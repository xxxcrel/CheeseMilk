package cn.qisee.cheesemilk.entity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "Post")
@Table(name = "tbl_post")
@DynamicUpdate
@DynamicInsert
@Getter
@Setter
@NoArgsConstructor
@NamedEntityGraph(
        name = "Post.details",
        attributeNodes = {
                @NamedAttributeNode("images"),
        }
)
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String content;

    /**
     * 标签，使用逗号分开
     */
    private String tags;

    @ManyToOne
    private Category category;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "star_count")
    private Integer starCount = 0;

    @Column(name = "comment_count")
    private Integer commentCount = 0;

    private boolean publicState;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "tbl_post_image")
    private Set<Image> images = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "tbl_post_comment")
    private Set<Comment> comments = new HashSet<>();
}