package beer.cheese.service;

import static  beer.cheese.controller.api.MultiDataQueryController.DateTuple;
import beer.cheese.exception.NotFoundException;
import beer.cheese.model.dto.PostDTO;
import beer.cheese.model.entity.User;

import beer.cheese.view.vo.PostVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface PostService {

    Page<PostVO> listPostsByUser(User currentUser, DateTuple queryPeriod, Pageable pageable);

    Page<PostVO> listPostsByUsername(String username, DateTuple queryPeriod, Pageable pageable);

    Page<PostVO> listPostsByCategory(User user, String category, DateTuple queryPeriod, Pageable pageable);

    PostVO getPostByPid(Long pid) throws NotFoundException;

    void postPlainBubble(User currentUser, PostDTO postDTO);

    void postFlexBubble(User currentUser, PostDTO postDTO, List<MultipartFile> images);

    void removeBubble(User operateUser, Long postID);

    void giveAStar(User currentUser, Long postId);
}
