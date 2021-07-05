package cn.qisee.cheesemilk.service;

import cn.qisee.cheesemilk.constant.AppConstants;
import beer.cheese.entity.dto.CourseDTO;
import beer.cheese.entity.dto.UserRegisterDTO;
import cn.qisee.cheesemilk.entity.Course;
import cn.qisee.cheesemilk.entity.User;
import beer.cheese.repository.*;
import cn.qisee.cheesemilk.security.acl.AclDTO;
import cn.qisee.cheesemilk.security.acl.AclManager;
import cn.qisee.cheesemilk.util.JwtUtils;
import cn.qisee.cheesemilk.util.StringUtils;
import cn.qisee.cheesemilk.repository.CommentRepository;
import cn.qisee.cheesemilk.repository.CourseRepository;
import cn.qisee.cheesemilk.repository.PostRepository;
import cn.qisee.cheesemilk.repository.RoleRepository;
import cn.qisee.cheesemilk.repository.UserRepository;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheEvict;
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
import java.util.stream.Collectors;

@Service
//@CacheConfig(cacheNames = "userCache")
public class UserService {

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
    private CourseRepository courseRepository;

    @Autowired
    private AclManager aclManager;


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

    public String login(String username, String password) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("username: " + username + " not found"));
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("Bad Password");
        }
        return JwtUtils.generateToken(username);
    }

    @CacheEvict(cacheNames = "userCache", key = "#user.username")
    public void updateUserProfile(User user, String field, Object value) {
        Class<?> clazz = User.class;

        try {
            Field updateField = clazz.getDeclaredField(field);
            Class<?> fieldType = updateField.getType();
            boolean isInteger = fieldType.isAssignableFrom(Integer.class);
            if(isInteger) {
                value = Integer.parseInt((String) value);
            }
            updateField.setAccessible(true);
            updateField.set(user, value);
            userRepository.save(user);
        } catch (NoSuchFieldException | IllegalAccessException | ClassCastException e) {
            e.printStackTrace();
        }

    }



    @CacheEvict(cacheNames = "userCache", key = "#user.username")
    public void uploadAvatar(User user, MultipartFile avatar) {
        String avatarName = StringUtils.generateFileName(user.getUsername(), Objects.requireNonNull(avatar.getOriginalFilename()));
        String avatarUrl = AppConstants.STATIC_SERVER_PREFIX +
                AppConstants.USER_AVATAR_PATH + avatarName;
        try {
            fileService.uploadFile(AppConstants.USER_AVATAR_PATH, avatarName, avatar);
        } catch (UploadFailureException e) {
            e.printStackTrace();
        }
        user.setAvatarUrl(avatarUrl);
        userRepository.saveAndFlush(user);

    }

    public boolean presentByUsername(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    public boolean presentByEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    public void uploadTimetable(User user, List<CourseDTO> courses) {
        courseRepository.deleteAllByUser(user);

        List<Course> pos;
        pos = courses.stream().map(courseDTO -> {
            Course course = new Course();
            BeanUtils.copyProperties(courseDTO, course);
            course.setUser(user);
            return course;
        }).collect(Collectors.toList());
        courseRepository.saveAll(pos);
    }

    public List<CourseDTO> getTimetableByUser(User user) {
        return courseRepository.findAllByUser(user).stream().map(course -> {
            CourseDTO courseDTO = new CourseDTO();
            BeanUtils.copyProperties(course, courseDTO);
            return courseDTO;
        }).collect(Collectors.toList());
    }
}
