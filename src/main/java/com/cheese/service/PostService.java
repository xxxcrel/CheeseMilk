package com.cheese.service;

import com.cheese.model.dto.PostDTO;
import com.cheese.model.entity.Post;
import com.cheese.model.entity.User;
import com.cheese.model.vo.PostVO;
import com.cheese.exception.NotFoundException;
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
