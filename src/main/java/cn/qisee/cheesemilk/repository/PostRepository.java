package cn.qisee.cheesemilk.repository;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import cn.qisee.cheesemilk.entity.Category;
import cn.qisee.cheesemilk.entity.Post;
import cn.qisee.cheesemilk.entity.User;

public interface PostRepository extends JpaRepository<Post, Long> {

    Page<Post> getAllByUserAndCreatedAtAfterAndCreatedAtBefore(User user, LocalDateTime start, LocalDateTime end, Pageable pageable);

    Page<Post> getAllByCategory(Category category, Pageable pageable);

    Page<Post> getAllByCategoryAndCreatedAtAfterAndCreatedAtBefore(Category category, LocalDateTime start, LocalDateTime end, Pageable pageable);

    @Modifying
    @Query("update Post p set p.commentCount = p.commentCount + :increment where p.id = :pid")
    void updateComments(@Param("pid") Long pid, @Param("increment") int increment);

    @Modifying
    @Query("update Post p set p.starCount = p.starCount + :increment where p.id = :pid")
    void updateStars(@Param("pid") Long pid, @Param("increment") int increment);

    @EntityGraph(attributePaths = {"posts"}, type = EntityGraph.EntityGraphType.FETCH)
    User findByNickname(String nickname);

    @EntityGraph("User.postsFetchAll")
    @Query("select u from User u where u.nickname = :nickname")
    User fetchAllByNickname(@Param("nickname") String nickname);

    @EntityGraph("User.postsFetchImages")
    User getByNickname(String nickname);
}
