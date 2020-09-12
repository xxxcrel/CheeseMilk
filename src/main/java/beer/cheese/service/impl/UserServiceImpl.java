package beer.cheese.service.impl;

import beer.cheese.constant.AppConstants;
import beer.cheese.exception.UploadFailureException;
import beer.cheese.model.dto.UserRegisterDTO;
import beer.cheese.model.entity.User;
import beer.cheese.repository.CommentRepository;
import beer.cheese.repository.PostRepository;
import beer.cheese.repository.RoleRepository;
import beer.cheese.repository.UserRepository;
import beer.cheese.security.acl.AclDTO;
import beer.cheese.security.acl.AclManager;
import beer.cheese.service.FileService;
import beer.cheese.service.UserService;
import beer.cheese.util.JwtUtils;
import beer.cheese.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    @Qualifier("jdkFileService")
    private FileService fileService;

    @Autowired
    private AclManager aclManager;


    @Override
    @Transactional
    public void register(UserRegisterDTO registerDTO) {
        MultipartFile avatar = registerDTO.getAvatar();
        String avatarName = StringUtils.generateFileName(registerDTO.getUsername(), Objects.requireNonNull(avatar.getOriginalFilename()));
        String avatarUrl = AppConstants.STATIC_SERVER_PREFIX +
                AppConstants.USER_AVATAR_PATH + avatarName;

        User po = User.builder().username(registerDTO.getUsername())
                .avatarUrl(avatarUrl)
                .password(passwordEncoder.encode(registerDTO.getPassword()))
                .email(registerDTO.getEmail())
                .nickname(registerDTO.getNickname())
                .accountLocked(false)
                .accountExpired(false)
                .disabled(false)
                .createdAt(new Date())
                .build();
        userRepository.save(po);

        fileService.uploadFile(AppConstants.USER_AVATAR_PATH, avatarName, registerDTO.getAvatar());

        //为每一个新注册的用户在acl_object_identity增加一条记录，拥有者为自己，由于JdbcMutableService需要从SecurityContextHolder中获取owner信息
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(po.getUsername(), po.getPassword(), grantedAuthorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        //增加管理员的管理权限
        aclManager.addPermission(AclDTO.builder().securedObject(po).role("SUPER_ADMIN").permission(BasePermission.ADMINISTRATION).build());
    }

    @Override
    public String login(String username, String password) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("username: " + username + " not found"));
        if (!passwordEncoder.matches(password, user.getPassword()))
            throw new BadCredentialsException("Bad Password");
        return JwtUtils.generateToken(username);
    }

    @Override
    public void updateUserProfile(User currentUser, String field, Object value) {
        Class<?> clazz = User.class;
        try {
            Field updateField = clazz.getDeclaredField(field);
            updateField.setAccessible(true);
            updateField.set(currentUser, value);
            userRepository.save(currentUser);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void uploadAvatar(User user, MultipartFile avatar) {
        String avatarName = StringUtils.generateFileName(user.getUsername(), Objects.requireNonNull(avatar.getOriginalFilename()));
        String avatarUrl = AppConstants.STATIC_SERVER_PREFIX +
                AppConstants.USER_AVATAR_PATH + avatarName;
        try {
            fileService.uploadFile(AppConstants.USER_AVATAR_PATH, avatarName, avatar);
        }catch (UploadFailureException e){
            e.printStackTrace();
        }
        user.setAvatarUrl(avatarUrl);
        userRepository.saveAndFlush(user);
    }

    @Override
    public boolean presentByUsername(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    @Override
    public boolean presentByEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

}
