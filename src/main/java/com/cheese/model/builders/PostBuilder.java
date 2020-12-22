package com.cheese.model.builders;

import com.cheese.model.dto.PostDTO;
import com.cheese.model.entity.Post;

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
        post.setCreatedAt(new Date());
        return post;
    }
}
