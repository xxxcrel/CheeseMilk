package beer.cheese.security.acl.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


public class AclClass {

    @Id
    private Long id;

    @Column(name = "class", length = 100, unique = true)
    private String className;
}
