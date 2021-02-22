package beer.cheese.model;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "Role")
@Table(name = "role")
@Data
@AllArgsConstructor
@NoArgsConstructor
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

}
