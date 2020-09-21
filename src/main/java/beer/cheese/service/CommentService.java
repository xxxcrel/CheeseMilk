package beer.cheese.service;

import static beer.cheese.controller.api.MultiDataQueryController.DateTuple;

import beer.cheese.model.dto.CommentDTO;
import beer.cheese.model.entity.User;
import beer.cheese.view.vo.CommentVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.time.Period;


public interface CommentService {

    Page<CommentVO> listCommentByCurrentUser(Pageable pageable);

    Page<CommentVO> listCommentsByPost(Long pid, DateTuple queryPeriod, Pageable pageable);

    Page<CommentVO> listCommentsByUser(String username, Pageable pageable);

    Page<CommentVO> listCommentsByParent(long parentId, DateTuple queryPeriod, Pageable pageable);

    void addComment(User user, Long postId, CommentDTO commentDTO);
    void giveAStar(User user, Long commentId);
}
