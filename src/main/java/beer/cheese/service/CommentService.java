package beer.cheese.service;

import beer.cheese.model.dto.CommentDTO;
import beer.cheese.model.entity.User;
import beer.cheese.view.vo.CommentVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface CommentService {

    Page<CommentVO> listCommentByCurrentUser(Pageable pageable);

    Page<CommentVO> listCommentsByPost(Long pid, Pageable pageable);

    Page<CommentVO> listCommentsByUser(String username, Pageable pageable);

    Page<CommentVO> listCommentsByParent(long parentId, Pageable pageable);

    void addComment(User user, Long postId, CommentDTO commentDTO);

}
