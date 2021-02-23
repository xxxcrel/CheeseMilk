package beer.cheese.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tbl_permission")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Permission {

    @Id
    private Long id;

}
