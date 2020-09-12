package beer.cheese.controller.api;

import beer.cheese.constant.JwtConstants;
import beer.cheese.exception.AlreadyExistsException;
import beer.cheese.model.dto.PostDTO;
import beer.cheese.model.dto.UserRegisterDTO;
import beer.cheese.model.dto.UserUpdateDTO;
import beer.cheese.model.entity.User;
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
    private CommentService commentService;

    @Autowired
    @Qualifier("jdkFileService")
    private FileService fileService;

    @Autowired
    private MailService mailService;

    /************ current user controller **************/

    @GetMapping("/greeting")
    public String greeting(){
        return "hello world";
    }

    @PostMapping(value = "/check", consumes = MediaType.TEXT_PLAIN_VALUE)
    public void check(@RequestBody String s) throws IOException {
//        System.out.println(request.getInputStream().read());
        System.out.println("check");
        System.out.println(s);
    }

    @GetMapping(value = "/check", params = "username")
    @ResponseStatus(HttpStatus.OK)
    public void usernameCheck(@RequestParam("username")String username){
        if(userService.presentByUsername(username))
            throw new AlreadyExistsException("user:" + username + " already exists");
    }

    @GetMapping(value = "/code/email", params = "address")
    @ResponseStatus(HttpStatus.OK)
    public Result<String> verifyEmail(@RequestParam("address")@Email String email, Model model, HttpSession session){
        if(userService.presentByEmail(email))
            throw new AlreadyExistsException("email already registered");

        String verifyCode = VerifyCodeGenerator.generate(6);
        model.addAttribute("verifyDTO", new VerifyDTO(verifyCode, LocalDateTime.now()));
        mailService.sendMail(email, verifyCode);

        return Result.ok("verify email already send to: " + email);
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Result<String> register(@Valid UserRegisterDTO registerDTO,
                           @SessionAttribute(value = "verifyDTO")VerifyDTO verifyDTO,
                           SessionStatus sessionStatus) {
        if(!verifyDTO.check(registerDTO.getCode(), LocalDateTime.now()))
            return new Result<>("Time out, please resend email verification");
        sessionStatus.setComplete();
        logger.info("register dto: " + registerDTO);
        userService.register(registerDTO);
        return Result.ok("register successful");
    }

//    @PostMapping("/test")
//    public void test(@RequestPart String username){
//        logger.info("username " + username);
//    }

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
    public Result<String> updateNickname(@CurrentUser User currentUser, @ModelAttribute @Validated UserUpdateDTO updateDTO) {
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

    @GetMapping(value = "/user/posts", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<PostVO> getUserBubbleList(@CurrentUser User user, @PageableDefault Pageable pageable){
        return postService.listPostsByCurrentUser(user, pageable);
    }

    @GetMapping("/user")
    @ResponseStatus(HttpStatus.OK)
    public UserVO getCurrentUser(@CurrentUser User currentUser){
        UserVO result = new UserVO();
        BeanUtils.copyProperties(currentUser, result);
        return result;
    }

}
