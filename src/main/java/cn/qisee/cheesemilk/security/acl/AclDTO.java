package cn.qisee.cheesemilk.security.acl;

import org.springframework.security.acls.domain.GrantedAuthoritySid;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.model.Permission;
import org.springframework.security.acls.model.Sid;
import org.springframework.util.Assert;

public class AclDTO {

    private Object securedObject;
    private Permission permission;
    private Sid sid;

    private AclDTO() {

    }

    public Object getSecuredObject() {
        return securedObject;
    }

    public void setSecuredObject(Object securedObject) {
        this.securedObject = securedObject;
    }

    public Permission getPermission() {
        return permission;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
    }

    public Sid getSid() {
        return sid;
    }

    public void setSid(Sid sid) {
        this.sid = sid;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Object object;

        private Permission permission;

        private String sidName;

        private boolean principal;


        public Builder securedObject(Object object) {
            this.object = object;
            return this;
        }

        public Builder principal(String name) {
            if (this.sidName != null && this.sidName.length() != 0)
                return this;
            this.sidName = name;
            this.principal = true;
            return this;
        }

        public Builder role(String roleName) {
            Assert.hasLength(roleName, "role name can not be empty");
            if (this.sidName != null && this.sidName.length() != 0)
                return this;
            this.sidName = roleName;
            this.principal = false;
            return this;
        }

        public Builder permission(Permission permission) {
            Assert.hasLength(String.valueOf(permission), "permission can not be empty");
            this.permission = permission;
            return this;
        }

        public AclDTO build() {
            AclDTO aclDTO = new AclDTO();
            aclDTO.setSecuredObject(this.object);
            Sid sid = null;
            sid = principal ? new PrincipalSid(sidName) : new GrantedAuthoritySid(sidName);
            aclDTO.setSid(sid);
            aclDTO.setPermission(permission);
            return aclDTO;
        }
    }
}
