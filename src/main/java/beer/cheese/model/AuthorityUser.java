package beer.cheese.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tbl_authority_user")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AuthorityUser {

    @Id
    private Long id;

    @OneToOne
    private User user;

    @OneToOne
    private Role role;

}
