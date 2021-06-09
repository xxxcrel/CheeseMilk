package beer.cheese.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedEntityGraphs;
import javax.persistence.NamedSubgraph;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "User")
@Table(name = "tbl_user")
@DynamicInsert
@DynamicUpdate
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@NamedEntityGraphs(
        value = {
                @NamedEntityGraph(
                        name = "User.postsFetchImages",
                        attributeNodes = @NamedAttributeNode(value = "posts", subgraph = "User.postsFetchImages.images"),
                        subgraphs = @NamedSubgraph(name = "User.postsFetchImages.images", attributeNodes = @NamedAttributeNode("images"))
                ),
                @NamedEntityGraph(
                        name = "User.postsFetchAll",
                        attributeNodes = @NamedAttributeNode(value = "posts", subgraph = "User.postsFetchAll.all"),
                        subgraphs = @NamedSubgraph(name = "User.postsFetchAll.all", attributeNodes = {@NamedAttributeNode("images"), @NamedAttributeNode("comments")})
                )
        }
)
@EntityListeners(AuditingEntityListener.class)
public class User implements Serializable {

    private static final long serialVersionUID = 749792921653839187L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //eg:studentId, teacherId, staffId...
    @Column(name = "cheese_id", unique = true)
    private String cheeseID;

    @Column(unique = true)
    private String phone;

    private String password;

    private String nickname;

    @Enumerated(EnumType.ORDINAL)
    private UserType type;

    @Enumerated(EnumType.ORDINAL)
    private UserStatus status;

    @Column(unique = true)
    private String email;

    @OneToOne
    @JoinColumn(name = "avatar_id")
    private Image avatar;

    //eg:0793-江西上饶、0731-湖南长沙
    private String location = null;

    private String bio;

    private LocalDateTime birth;

    //0-male, 1-female
    private Integer gender;

    @Column(name = "created_at")
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "tbl_user_post")
    private Set<Post> posts = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "tbl_user_comment")
    private Set<Comment> comments = new HashSet<>();

    public enum UserType{
        STUDENT, TEACHER, STAFF
    }
    public enum UserStatus{
        ENABLE, OFFLINE, ONLINE, DISABLED, ACCOUNT_LOCKED
    }
}
