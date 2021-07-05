package cn.qisee.cheesemilk.security.acl.model;


import javax.persistence.*;


public class AclObjectIdentity {
    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "object_id_class")
    private AclClass aclClass;

    @ManyToOne
    @JoinColumn(name = "owner_sid")
    private AclSid owner;

    @OneToOne
    @JoinColumn(name = "parent_object")
    private AclObjectIdentity parent;

    @Column(name = "object_id_identity")
    private String objectIdIdentity;

    @Column(name = "entries_inheriting")
    private String entriesInheriting;
}
