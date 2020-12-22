package com.cheese.database.repository;

import com.cheese.model.entity.Category;
import com.cheese.model.entity.User;
import com.cheese.model.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    Page<Post> getAllByUser(User user, Pageable pageable);

    Page<Post> getAllByCategory(Category category, Pageable pageable);

    @Query("update Post p set p.commentCount = p.commentCount + :increment where p.id = :pid")
    void updateComments(@Param("pid")Long pid, @Param("increment")int increment);

    @Modifying
    @Query("update Post p set p.starCount = p.commentCount + :increment where p.id = :pid")
    void updateLikes(@Param("pid")Long pid, @Param("increment")int increment);

}
