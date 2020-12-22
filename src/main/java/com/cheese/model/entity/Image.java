package com.cheese.model.entity;

import javax.persistence.*;

@Entity
@Table(name = "image")
public class Image {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "thumbnailUrl")
    private String thumbnailUrl;

    @JoinColumn(name = "post_id")
    @ManyToOne
    private Post post;

    public Image(){

    }

    public Image(String url){
        this.imageUrl = url;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
