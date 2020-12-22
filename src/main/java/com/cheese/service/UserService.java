package com.cheese.service;

import com.cheese.model.dto.UserRegisterDTO;
import com.cheese.model.entity.Role;
import com.cheese.model.entity.User;
import com.cheese.model.vo.RoleVO;
import com.cheese.model.vo.UserVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

public interface UserService {

    void register(UserRegisterDTO registerDTO);

    String login(String username, String password);

    void updateUserProfile(User user, String field, Object value);

    void uploadAvatar(User user, MultipartFile avatar);

    boolean presentByUsername(String username);

    boolean presentByEmail(String email);

}
