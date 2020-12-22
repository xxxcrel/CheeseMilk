package com.cheese.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.*;
import java.util.Date;

public class UserVO {

    private Long id;

    @NotBlank(message = "用户名不能为空")
    @Size(min = 6, max = 16)
    private String username;

    @NotBlank
    private String nickname;

    @Email
    private String email;

    @NotNull
    @Pattern(regexp = "[0|1]", message = "性别格式只能为0或1")
    private Integer gender;

    @JsonProperty("avatar_url")
    private String avatarUrl;

    private String location = null;

    private String bio;

    private Date birth;

    @JsonProperty("student_attr")
    private String studentAttr;

    @JsonFormat(pattern = "yy:mm:dd HH:mm:ss", timezone = "GMT+8")
    @JsonProperty("created_at")
    private Date createdAt;

    @JsonFormat(pattern = "yy:mm:dd HH:mm:ss")
    @JsonProperty("updated_at")
    private Date updatedAt;

    private boolean locked;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getStudentAttr() {
        return studentAttr;
    }

    public void setStudentAttr(String studentAttr) {
        this.studentAttr = studentAttr;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

}
