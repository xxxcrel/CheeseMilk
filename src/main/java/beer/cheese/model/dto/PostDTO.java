package beer.cheese.model.dto;

import javax.validation.constraints.NotEmpty;

public class PostDTO {

    @NotEmpty
    private String category;

    @NotEmpty
    private String content;

    private String tags;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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
}
