package beer.cheese.view.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class CommentVO {

    private Long id;

    private String nickname;

    private String username;

    @JsonProperty("avatar_url")
    private String avatarUrl;

    private String content;

    @JsonProperty("parent_id")
    private Long parentId;

    @JsonFormat(pattern = "yy:mm:dd HH:mm:SS")
    private Date createdAt;

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

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public int getSubCommentCount() {
        return subCommentCount;
    }

    public void setSubCommentCount(int subCommentCount) {
        this.subCommentCount = subCommentCount;
    }

}
