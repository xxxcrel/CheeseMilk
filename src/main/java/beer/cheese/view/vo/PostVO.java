package beer.cheese.view.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class PostVO {

    private Long id;

    private String nickname;

    private String avatarUrl;

    private LocalDateTime createdAt;

    private String content;

    /**
     * 标签，使用逗号分开
     */
    private String tags;

    //0:moments
    private String categoryName;

    private Integer starCount;


    private Integer commentCount;

    private List<String> imageUrls;

    private String commentUrl;

    private boolean starred;

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

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Integer getStarCount() {
        return starCount;
    }

    public void setStarCount(Integer startCount) {
        this.starCount = startCount;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public String getCommentUrl() {
        return commentUrl;
    }

    public boolean isStarred() {
        return starred;
    }

    public void setStarred(boolean starred) {
        this.starred = starred;
    }

    public void setCommentUrl(String commentUrl) {
        this.commentUrl = commentUrl;
    }
}
