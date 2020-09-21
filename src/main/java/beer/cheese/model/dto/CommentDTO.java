package beer.cheese.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CommentDTO {

    @JsonProperty("parent_id")
    private Long parentId;

    private String content;


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
}
