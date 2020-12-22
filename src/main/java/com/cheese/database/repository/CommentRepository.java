package com.cheese.database.repository;

import com.cheese.model.entity.User;
import com.cheese.model.entity.Comment;
import com.cheese.model.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Page<Comment> getAllByPost(Post post, Pageable pageable);

    Page<Comment> getAllByUser(User user, Pageable pageable);

    Page<Comment> getAllByPostAndParentIsNull(Post post, Pageable pageable);

    Optional<Comment> findCommentById(Long id);

    Page<Comment> getAllByParent(Comment parent, Pageable pageable);

    //返回一个帖子下的所有评论
    Page<Comment> getCommentsById(Long postId,Pageable pageable);

}
