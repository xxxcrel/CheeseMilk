package beer.cheese.service;

import beer.cheese.model.dto.CourseDTO;
import beer.cheese.model.dto.UserRegisterDTO;
import beer.cheese.model.entity.Course;
import beer.cheese.model.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {

    void register(UserRegisterDTO registerDTO);

    String login(String username, String password);

    void updateUserProfile(User user, String field, Object value);

    void uploadAvatar(User user, MultipartFile avatar);

    boolean presentByUsername(String username);

    boolean presentByEmail(String email);

    void uploadTimetable(User user, List<CourseDTO> courses);

    List<CourseDTO> getTimetableByUser(User user);
}
