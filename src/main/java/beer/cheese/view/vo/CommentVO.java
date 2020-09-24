package beer.cheese.view.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class CommentVO {

    private Long id;

    private String nickname;

    private String username;

    @JsonProperty("avatar_url")
    private String avatarUrl;

    private String content;

    @JsonProperty("parent_nickname")
    private String parentNickname;

//    @JsonFormat(pattern = "yy:mm:dd HH:mm:SS")
    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    @JsonProperty("sub_comment_count")
    private int subCommentCount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getParentNickname() {
        return parentNickname;
    }

    public void setParentNickname(String parentNickname) {
        this.parentNickname = parentNickname;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public int getSubCommentCount() {
        return subCommentCount;
    }

    public void setSubCommentCount(int subCommentCount) {
        this.subCommentCount = subCommentCount;
    }

}
