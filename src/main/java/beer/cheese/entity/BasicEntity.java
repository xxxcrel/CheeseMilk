package beer.cheese.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class BasicEntity {

    @Id
    @GeneratedValue
    private Long id;
}
