package com.cheese.service.impl;

import com.cheese.database.repository.CommentRepository;
import com.cheese.database.repository.PostRepository;
import com.cheese.database.repository.UserRepository;
import com.cheese.exception.NotFoundException;
import com.cheese.model.dto.CommentDTO;
import com.cheese.model.entity.Comment;
import com.cheese.model.entity.Post;
import com.cheese.model.entity.User;
import com.cheese.model.vo.CommentVO;
import com.cheese.security.acl.AclDTO;
import com.cheese.security.acl.AclManager;
import com.cheese.service.CommentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;


@Service
public class CommentServiceImpl implements CommentService {

    private static final String[] COMMENTVO_TO_COMMENT_IGNORE = {"id", "postId",};
    private static final String[] COMMENT_TO_COMMENTVO_IGNORE = {"user", "post"};
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AclManager aclManager;

    @Override
    public Page<CommentVO> listCommentByCurrentUser(Pageable pageable) {
        return null;
    }

    static class CustomCommentCopy{
        static CommentVO copy(Comment comment){
            CommentVO vo = new CommentVO();
            BeanUtils.copyProperties(comment, vo, COMMENT_TO_COMMENTVO_IGNORE);
            User user = comment.getUser();
            vo.setAvatarUrl(user.getAvatarUrl());
            vo.setNickname(user.getNickname());
            vo.setUsername(user.getUsername());
            return vo;
        }
    }

    @Override
    @Transactional
    public Page<CommentVO> listCommentsByPost(Long pid, Pageable pageable) {
        Post post = postRepository.findById(pid).orElseThrow(() -> new NotFoundException("post_id:" + pid + " not found"));
        return commentRepository.getAllByPostAndParentIsNull(post, pageable).map(CustomCommentCopy::copy);
    }

    @Override
    public Page<CommentVO> listCommentsByUser(String username, Pageable pageable) {
        User user = userRepository.findByUsername(username).get();
        return commentRepository.getAllByUser(user, pageable).map(CustomCommentCopy::copy);
    }


    @Override
    public Page<CommentVO> listCommentsByParent(long parentId, Pageable pageable) {
        Comment parent = commentRepository.getOne(parentId);
        return commentRepository.getAllByParent(parent, pageable).map(CustomCommentCopy::copy);
    }

    @Override
    public void addComment(User user, Long postId, CommentDTO commentDTO) {

        Post post = postRepository.findById(postId).orElseThrow(() -> new NotFoundException("post_id:" + postId + " not found"));
        Comment parent;
        Comment comment = new Comment();
        comment.setContent(commentDTO.getContent());
        Long parentId = commentDTO.getParentId();
        if (parentId != null && !parentId.equals(0L)) {
            parent = commentRepository.getOne(parentId);
            comment.setParent(parent);
        }
        comment.setUser(user);
        comment.setPost(post);
        comment.setCreatedAt(new Date());
        commentRepository.save(comment);

        aclManager.addPermission(AclDTO.builder()
                .securedObject(comment)
                .role(post.getCategory().getCategoryName())
                .permission(BasePermission.DELETE).build());
    }
}
