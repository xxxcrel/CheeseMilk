package beer.cheese.controller.api;

import beer.cheese.constant.JwtConstants;
import beer.cheese.exception.AlreadyExistsException;
import beer.cheese.model.dto.CourseDTO;
import beer.cheese.model.dto.PostDTO;
import beer.cheese.model.dto.UserRegisterDTO;
import beer.cheese.model.dto.UserUpdateDTO;
import beer.cheese.model.entity.Star;
import beer.cheese.model.entity.User;
import beer.cheese.repository.StarRepository;
import beer.cheese.security.CurrentUser;
import beer.cheese.service.*;
import beer.cheese.util.VerifyCodeGenerator;
import beer.cheese.view.Result;
import beer.cheese.view.vo.PostVO;
import beer.cheese.view.vo.UserVO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import static beer.cheese.model.dto.UserRegisterDTO.VerifyDTO;

@RestController
@RequestMapping
@Validated
@SessionAttributes(value = "verifyDTO")
public class UserController {
    public final Log logger = LogFactory.getLog(getClass());

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    @Autowired
    private StarRepository starRepository;

    @Autowired
    @Qualifier("jdkFileService")
    private FileService fileService;

    @Autowired
    private MailService mailService;

    @PostMapping(value = "/login", params = {"username", "password"}, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Result<String> login(@RequestParam("username")@NotEmpty String username,
                                @RequestParam("password")@NotEmpty String password, HttpServletResponse response){
        String jwtToken = userService.login(username, password);
        response.setHeader(HttpHeaders.AUTHORIZATION, JwtConstants.TOKEN_SCHEMA + jwtToken);
        return Result.ok("login successful");
    }

    @PostMapping(path = "/user/profiles", params = "avatar", consumes = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE})
    public Result<String> uploadAvatar(@CurrentUser User currentUser , @RequestParam("avatar")@Validated MultipartFile avatar){
        userService.uploadAvatar(currentUser, avatar);
        return Result.ok("update avatar successful!");
    }

    @PostMapping(path = "/user/profiles", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Result<String> updateNickname(@CurrentUser User currentUser, @RequestPart(name = "meta-data") @Validated UserUpdateDTO updateDTO) {
        userService.updateUserProfile(currentUser, updateDTO.getUpdatedField(), updateDTO.getUpdatedValue());
        return Result.ok("update field: " + updateDTO.getUpdatedField() + " successful");
    }

    /// 把发帖放在user下是为了显示发帖这一动作只属于用户
    @PostMapping("/user/posts")
    @ResponseStatus(HttpStatus.OK)
    public Result<String> postBubble(@CurrentUser User user, @RequestPart("meta-data") PostDTO postDTO, @RequestPart(value = "images[]", required = false) List<MultipartFile> images) {

        postService.postFlexBubble(user, postDTO, images);
        return Result.ok("post successful");
    }

    @GetMapping("/user/profiles")
    @ResponseStatus(HttpStatus.OK)
    public UserVO getCurrentUser(@CurrentUser User currentUser){
        UserVO result = new UserVO();
        BeanUtils.copyProperties(currentUser, result);
        return result;
    }

    @PostMapping("/user/timetable")
    @ResponseStatus(HttpStatus.OK)
    public void uploadTimetable(@CurrentUser User user,
                                @RequestPart(name = "timetable")List<CourseDTO> courses){
        userService.uploadTimetable(user, courses);
    }

    @GetMapping("/user/timetable")
    @ResponseStatus(HttpStatus.OK)
    public List<CourseDTO> getTimetable(@CurrentUser User user){
        return userService.getTimetableByUser(user);
    }
}
