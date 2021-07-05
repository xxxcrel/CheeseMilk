package cn.qisee.cheesemilk.security.acl.model;

import javax.persistence.Column;
import javax.persistence.Id;


public class AclClass {

    @Id
    private Long id;

    @Column(name = "class", length = 100, unique = true)
    private String className;
}
