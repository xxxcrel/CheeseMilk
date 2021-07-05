package cn.qisee.cheesemilk.service;


import beer.cheese.entity.dto.CommentDTO;
import cn.qisee.cheesemilk.entity.Comment;
import cn.qisee.cheesemilk.entity.Post;
import cn.qisee.cheesemilk.entity.User;
import cn.qisee.cheesemilk.repository.CommentRepository;
import cn.qisee.cheesemilk.repository.PostRepository;
import cn.qisee.cheesemilk.repository.StarRepository;
import cn.qisee.cheesemilk.repository.UserRepository;
import cn.qisee.cheesemilk.security.acl.AclDTO;
import cn.qisee.cheesemilk.security.acl.AclManager;
import beer.cheese.view.vo.CommentVO;
import cn.qisee.cheesemilk.web.api.MultiDataQueryController;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;


@Service
public class CommentService{

    private static final String[] COMMENTVO_TO_COMMENT_IGNORE = {"id", "postId",};
    private static final String[] COMMENT_TO_COMMENTVO_IGNORE = {"user", "post"};
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StarRepository starRepository;

    @Autowired
    private AclManager aclManager;

    public Page<CommentVO> listCommentByCurrentUser(Pageable pageable) {
        return null;
    }

    static class CustomCommentCopy {
        static CommentVO copy(Comment comment) {
            CommentVO vo = new CommentVO();
            BeanUtils.copyProperties(comment, vo, COMMENT_TO_COMMENTVO_IGNORE);
            User user = comment.getUser();
            vo.setAvatarUrl(user.getAvatarUrl());
            vo.setNickname(user.getNickname());
            vo.setUsername(user.getUsername());
            Optional.ofNullable(comment.getParent()).ifPresent((parent) -> {
                vo.setParentNickname(parent.getUser().getNickname());
            });
            return vo;
        }
    }

    @Transactional
    public Page<CommentVO> listCommentsByPost(Long pid, MultiDataQueryController.DateTuple queryPeriod, Pageable pageable) {
        Post post = postRepository.findById(pid).orElseThrow(() -> new NotFoundException("post_id:" + pid + " not found"));
        return commentRepository.getAllByPostAndCreatedAtBetweenAndParentIsNull(post, queryPeriod.start, queryPeriod.end, pageable).map(CustomCommentCopy::copy);
    }

    public Page<CommentVO> listCommentsByUser(String username, Pageable pageable) {
        User user = userRepository.findByUsername(username).get();
        return commentRepository.getAllByUser(user, pageable).map(CustomCommentCopy::copy);
    }


    @Transactional
    public Page<CommentVO> listCommentsByParent(long parentId, MultiDataQueryController.DateTuple queryPeriod, Pageable pageable) {
        Comment parent = commentRepository.getOne(parentId);
        return commentRepository.getAllByParentAndCreatedAtBetween(parent, queryPeriod.start, queryPeriod.end, pageable).map(CustomCommentCopy::copy);
    }

    @Transactional
    public void addComment(User user, Long postId, CommentDTO commentDTO) {

        Post post = postRepository.findById(postId).orElseThrow(() -> new NotFoundException("post_id:" + postId + " not found"));
        Comment parent;
        Comment comment = new Comment();
        comment.setContent(commentDTO.getContent());
        Long parentId = commentDTO.getParentId();
        comment.setUser(user);
        comment.setPost(post);
        comment.setCreatedAt(LocalDateTime.now());
        commentRepository.save(comment);
        postRepository.updateComments(postId, 1);
        if (parentId != null && !parentId.equals(0L)) {
            parent = commentRepository.findById(parentId).orElseThrow(() -> new NotFoundException("comment_id: " + parentId + " not found"));
            comment.setParent(parent);
            commentRepository.updateSubCommentCount(parentId, 1);
        }

        aclManager.addPermission(AclDTO.builder()
                .securedObject(comment)
                .role(post.getCategory().getCategoryName())
                .permission(BasePermission.DELETE).build());
    }

    public void giveAStar(User user, Long commentId) {
        Star star = new Star();
//        star.setUser(user);
        star.setStarPK(new Star.StarPK(user, commentId, (Star.ResourceType.COMMENT.ordinal())));
        starRepository.save(star);
        commentRepository.updateStars(commentId, 1);
    }
}
