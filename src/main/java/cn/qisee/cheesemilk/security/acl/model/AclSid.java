package cn.qisee.cheesemilk.security.acl.model;

import javax.persistence.*;

public class AclSid {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private boolean principal;

    @Column(name = "sid", length = 100, unique = true)
    private String sid;
}
