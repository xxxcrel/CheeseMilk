package com.cheese.security.acl.model;


import javax.persistence.*;

@Entity
@Table(name = "acl_entry")
public class AclEntry {

    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "acl_object_identity")
    private AclObjectIdentity objectIdentity;

    @Column(name = "ace_order")
    private int aceOrder;

    @ManyToOne
    @JoinColumn(name = "sid")
    private AclSid sid;

    private Integer mask;

    private boolean granting;

    @Column(name = "audit_success")
    private boolean auditSuccess;

    @Column(name = "audit_failure")
    private boolean auditFailure;
}
