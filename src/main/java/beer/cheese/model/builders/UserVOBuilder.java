package beer.cheese.model.builders;

import beer.cheese.model.entity.User;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class UserVOBuilder {

    private Long uid;

    private String username;

    private String nickname;

    private String email;

    private Integer gender;

    private String avatarUrl;

    private String location = null;

    private String bio;

    private Date birth;

    private String studentAttr;

    @JsonFormat(pattern = "yy:mm:dd HH:mm:ss", timezone = "GMT+8")
    private Date createdAt;

    @JsonFormat(pattern = "yy:mm:dd HH:mm:ss")
    private Date updatedAt;

    private boolean locked;
    public UserVOBuilder(){}

    public UserVOBuilder withUser(User user){
        return username(user.getUsername())
                .email(user.getEmail())
                .location(user.getLocation())
                .bio(user.getBio())
                .avatarUrl(user.getAvatarUrl())
                .birth(user.getBirth())
                .gender(user.getGender())
                .nickname(user.getNickname())
                .uid(user.getId())
                .createdAt(user.getCreatedAt());
    }

    public UserVOBuilder createdAt(Date createdAt){
        this.createdAt = createdAt;
        return this;
    }
    public UserVOBuilder uid(Long uid){
        this.uid = uid;
        return this;
    }
    public UserVOBuilder username(String username){
        this.username = username;
        return this;
    }

    public UserVOBuilder nickname(String nickname){
        this.nickname = nickname;
        return this;
    }

    public UserVOBuilder email(String email) {
        this.email = email;
        return this;
    }

    public UserVOBuilder gender(Integer gender){
        this.gender = gender;
        return this;
    }

    public UserVOBuilder avatarUrl(String avatarUrl){
        this.avatarUrl = avatarUrl;

        return this;
    }

    public UserVOBuilder location(String location){
        this.location = location;
        return this;
    }

    public UserVOBuilder bio(String bio){
        this.bio = bio;
        return this;
    }

    public UserVOBuilder birth(Date birth){
        this.birth = birth;
        return this;
    }

}
