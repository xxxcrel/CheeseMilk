package com.cheese.model.entity;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "Role")
@Table(name = "role")
public class Role {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "role_name", nullable = false)
    private String roleName;

    @Column
    private String description;


    @ManyToMany(mappedBy = "roles")
    private Set<User> users = new HashSet<>();

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
