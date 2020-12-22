package com.cheese.service;

import com.cheese.model.dto.CommentDTO;
import com.cheese.model.entity.Comment;
import com.cheese.model.entity.User;
import com.cheese.model.vo.CommentVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface CommentService {

    Page<CommentVO> listCommentByCurrentUser(Pageable pageable);

    Page<CommentVO> listCommentsByPost(Long pid, Pageable pageable);

    Page<CommentVO> listCommentsByUser(String username, Pageable pageable);

    Page<CommentVO> listCommentsByParent(long parentId, Pageable pageable);

    void addComment(User user, Long postId, CommentDTO commentDTO);

}
