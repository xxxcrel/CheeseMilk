package beer.cheese.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tbl_manager_group")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ManagerGroup {

    @Id
    private Long id;

    @OneToOne
    private User owner;

    @OneToMany
    private Set<AuthorityUser> members = new HashSet<>();

}
