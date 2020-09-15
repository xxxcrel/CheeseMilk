package beer.cheese.model.builders;

import beer.cheese.model.dto.PostDTO;
import beer.cheese.model.entity.Post;

import java.time.LocalDateTime;
import java.util.Date;

public class PostBuilder {

    public PostBuilder() {

    }

    public Post fromDTO(PostDTO dto){
        Post post = new Post();
        post.setContent(dto.getContent());
        post.setTags(dto.getTags());
        post.setCommentCount(0);
        post.setStarCount(0);
        post.setCreatedAt(LocalDateTime.now());
        return post;
    }
}
