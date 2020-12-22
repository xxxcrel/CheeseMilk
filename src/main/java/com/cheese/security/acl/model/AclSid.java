package com.cheese.security.acl.model;

import javax.persistence.*;

@Entity
@Table(name = "acl_sid")
public class AclSid {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private boolean principal;

    @Column(name = "sid", length = 100, unique = true)
    private String sid;
}
