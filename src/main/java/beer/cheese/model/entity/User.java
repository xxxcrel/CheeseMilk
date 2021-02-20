package beer.cheese.model.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.transaction.annotation.Transactional;

import beer.cheese.model.builders.UserBuilder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "User")
@Table(name = "user")
@DynamicInsert
@DynamicUpdate
@Transactional(transactionManager = "transactionManager")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {

    private static final long serialVersionUID = 749792921653839187L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //eg:studentId, teacherId, staffId...
    @Column(unique = true, nullable = false, updatable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    private String nickname;

    private String email;

    @Column(name = "avatar_url")
    private String avatarUrl;

    //eg:0793-江西上饶、0731-湖南长沙
    private String location = null;

    private String bio;

    private Date birth;

    //0-male, 1-female
    private Integer gender;

    //如果type是student，采用.分隔（eg:0.17.12.2代表本科17级软件工程2班）
    @Column(name = "student_attr", nullable = true)
    private String studentAttr;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "user")
    private Set<Post> posts = new HashSet<>();

    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "user")
    private Set<Comment> comments = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = {@JoinColumn(name = "user_id")}, inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private Set<Role> roles = new HashSet<>();

    @ManyToMany(mappedBy = "members", fetch = FetchType.EAGER)
    private Set<ManagerGroup> managerGroups = new HashSet<>();

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "user")
    private Set<Course> courses = new HashSet<>();


    /***************************** spring security user details*************/

    public User(String avatarUrl, String username, String password, String nickname,
                String email, Integer gender, Date createdAt,
                boolean accountNonExpired, boolean credentialsNonExpired,
                boolean accountNonLocked, boolean enabled) {
        this.avatarUrl = avatarUrl;
        this.username = username;
        this.password = password;
        this.email = email;
        this.nickname = nickname;
        this.gender = gender;
        this.enabled = enabled;
    }

    private boolean enabled;

    public static UserBuilder builder() {
        return new UserBuilder();
    }

    @Override
    public boolean equals(Object o) {
        return id.equals(((User) o).getId()) && username.equals(((User) o).getUsername());
    }

}
