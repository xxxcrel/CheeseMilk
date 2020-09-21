package beer.cheese.repository;

import beer.cheese.model.entity.Category;
import beer.cheese.model.entity.Post;
import beer.cheese.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Date;

public interface PostRepository extends JpaRepository<Post, Long> {

    Page<Post> getAllByUserAndCreatedAtBetween(User user, LocalDateTime start, LocalDateTime end, Pageable pageable);

    Page<Post> getAllByCategory(Category category, Pageable pageable);

    Page<Post> getAllByCategoryAndCreatedAtBetween(Category category, LocalDateTime start, LocalDateTime end, Pageable pageable);

    @Modifying
    @Query("update Post p set p.commentCount = p.commentCount + :increment where p.id = :pid")
    void updateComments(@Param("pid")Long pid, @Param("increment")int increment);

    @Modifying
    @Query("update Post p set p.starCount = p.starCount + :increment where p.id = :pid")
    void updateStars(@Param("pid")Long pid, @Param("increment")int increment);

}
