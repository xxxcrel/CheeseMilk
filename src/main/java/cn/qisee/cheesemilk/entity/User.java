package cn.qisee.cheesemilk.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
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
@EntityListeners(AuditingEntityListener.class)
public class User extends BasicEntity implements Serializable {

    private static final long serialVersionUID = 749792921653839187L;

    //eg:studentId, teacherId, staffId...
    @Column(name = "cheese_id", unique = true)
    private String cheeseID;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "type")
    @Enumerated(EnumType.ORDINAL)
    private UserType type;

    @Column(name = "status")
    @Enumerated(EnumType.ORDINAL)
    private UserStatus status;

    @Column(name = "email", unique = true)
    private String email;

    @OneToOne
    @JoinColumn(name = "avatar_id")
    private Image avatar;

    //eg:0793-江西上饶、0731-湖南长沙
    @Column(name = "location")
    private String location = null;

    @Column(name = "bio")
    private String bio;

    @Column(name = "birth")
    private LocalDateTime birth;

    //0-male, 1-female
    @Column(name = "gender")
    private Integer gender;

    @Column(name = "created_at")
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Column(name = "last_login_at")
    private LocalDateTime lastLoginAt;

    public enum UserType{
        STUDENT, TEACHER, STAFF
    }
    public enum UserStatus{
        ENABLE, OFFLINE, ONLINE, DISABLED, ACCOUNT_LOCKED
    }
}
