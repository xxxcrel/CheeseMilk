package beer.cheese.model.builders;

import beer.cheese.model.entity.User;
import org.springframework.util.Assert;

import java.util.Date;

public class UserBuilder {
    private String avatarUrl;
    private String username;
    private String password;
    private String nickname;
    private String email;
    private Integer gender;
    private Date createdAt;

    private boolean accountExpired;
    private boolean accountLocked;
    private boolean credentialsExpired;
    private boolean disabled;

    public UserBuilder() {

    }

    public UserBuilder avatarUrl(String avatarUrl){
        Assert.notNull(avatarUrl, "avatarUrl can not be null");
        this.avatarUrl = avatarUrl;
        return this;
    }
    public UserBuilder username(String username) {
        Assert.notNull(username, "username cannot be null");
        this.username = username;
        return this;
    }

    public UserBuilder password(String password) {
        Assert.notNull(password, "password can not be null");
        this.password = password;
        return this;
    }

    public UserBuilder nickname(String nickname) {
        Assert.notNull(nickname, "nickname can not be null");
        this.nickname = nickname;
        return this;
    }

    public UserBuilder email(String email) {
        Assert.notNull(email, "email cant not be null");
        this.email = email;
        return this;
    }


    public UserBuilder gender(Integer gender) {
        if (!gender.toString().matches("[0|1]"))
            return this;
        this.gender = gender;
        return this;
    }

    public UserBuilder createdAt(Date now){
        Assert.notNull(now, "created time can not be null");
        this.createdAt = now;
        return this;
    }

    public UserBuilder accountExpired(boolean accountExpired) {
        this.accountExpired = accountExpired;
        return this;
    }
    public UserBuilder accountLocked(boolean accountLocked) {
        this.accountLocked = accountLocked;
        return this;
    }

    public UserBuilder credentialsExpired(boolean credentialsExpired) {
        this.credentialsExpired = credentialsExpired;
        return this;
    }

    public UserBuilder disabled(boolean disabled) {
        this.disabled = disabled;
        return this;
    }


    public User build(){
        return new User(avatarUrl, username, password, nickname,
                        email, gender, createdAt,
                        !accountExpired, !credentialsExpired,
                        !accountLocked, !disabled);
    }

}