package beer.cheese.model.dto;

import beer.cheese.validation.ValidFile;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.Duration;
import java.time.LocalDateTime;

public class UserRegisterDTO {

    @ValidFile
    private MultipartFile avatar;

    @NotEmpty
    private String username;

    @NotEmpty
    @Size(min = 6, max = 24)
    private String password;

    @Email
    private String email;

    @NotEmpty
    private String nickname;

    @Max(1)
    private Integer gender;

    @Size(min = 6, max = 6)
    private String code;//verify code

    public static class VerifyDTO{
        private String verifyCode;
        private LocalDateTime startTime;

        public VerifyDTO(String verifyCode, LocalDateTime startTime){
            this.verifyCode = verifyCode;
            this.startTime = startTime;
        }
        public String getVerifyCode() {
            return verifyCode;
        }

        public void setVerifyCode(String verifyCode) {
            this.verifyCode = verifyCode;
        }

        public LocalDateTime getStartTime() {
            return startTime;
        }

        public void setStartTime(LocalDateTime startTime) {
            this.startTime = startTime;
        }

        public boolean check(String verifyCode, LocalDateTime stopTime){
            boolean result = false;
            if(this.verifyCode.equals(verifyCode)){
                Duration duration = Duration.between(startTime, stopTime);
                if(duration.toMillis() / 1000 < 60)
                    result = true;
            }
            return result;
        }
    }
    public UserRegisterDTO(){

    }

    public MultipartFile getAvatar() {
        return avatar;
    }

    public void setAvatar(MultipartFile avatar) {
        this.avatar = avatar;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "username:" + username +
                "\n password: " + password +
                "\n nickname: " + nickname +
                "\n email: " + email;
    }
}
