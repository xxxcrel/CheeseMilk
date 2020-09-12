package beer.cheese.service;

import beer.cheese.exception.NotFoundException;
import beer.cheese.model.dto.PostDTO;
import beer.cheese.model.entity.User;

import beer.cheese.view.vo.PostVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PostService {

    Page<PostVO> listPostsByCurrentUser(User currentUser, Pageable pageable);

    Page<PostVO> listPostsByUsername(String username, Pageable pageable);

    Page<PostVO> listPostsByCategory(String category, Pageable pageable);

    PostVO getPostByPid(Long pid) throws NotFoundException;

    void postPlainBubble(User currentUser, PostDTO postDTO);

    void postFlexBubble(User currentUser, PostDTO postDTO, List<MultipartFile> images);

    void removeBubble(User operateUser, Long postID);
}
