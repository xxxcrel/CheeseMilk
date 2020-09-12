package beer.cheese.repository;

import beer.cheese.model.entity.Comment;
import beer.cheese.model.entity.Post;
import beer.cheese.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

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
