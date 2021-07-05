package cn.qisee.cheesemilk.security.acl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.jdbc.JdbcMutableAclService;
import org.springframework.security.acls.model.*;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class AclManager{

    public final Log logger = LogFactory.getLog(getClass());
    @Autowired
    private JdbcMutableAclService aclService;

    public void createObjectIdentity(Object securedObject){
        ObjectIdentity oi = new ObjectIdentityImpl(securedObject);
        readOrCreateAcl(oi);
    }

    public void addPermission(AclDTO aclDTO) {
        ObjectIdentity oi = new ObjectIdentityImpl(aclDTO.getSecuredObject());
        MutableAcl acl = readOrCreateAcl(oi);
        isPermissionGranted(acl, aclDTO.getSid(), aclDTO.getPermission());
        aclService.updateAcl(acl);
    }

    public void removePermission(AclDTO aclDTO) {
        ObjectIdentity oi = new ObjectIdentityImpl(aclDTO.getSecuredObject());
        MutableAcl acl = readOrCreateAcl(oi);

        AccessControlEntry[] aces = acl.getEntries().toArray(new AccessControlEntry[acl.getEntries().size()]);
        for (int i = 0; i < acl.getEntries().size(); ++i) {
            if (aces[i].getSid().equals(aclDTO.getSid()) && aces[i].getPermission().equals(aclDTO.getPermission())) {
                //found permission with given sid;
                acl.deleteAce(i);
            }
        }
    }
    public boolean isPermissionGranted(Object securedObject, List<Sid> sids, List<Permission> permissions) {
        ObjectIdentity identity = new ObjectIdentityImpl(securedObject);
        MutableAcl acl = (MutableAcl) aclService.readAclById(identity);
        boolean isGranted = false;

        try {
            isGranted = acl.isGranted(permissions, sids, false);
        } catch (NotFoundException e) {
            logger.info("Unable to find an ACE for the given object", e);
        } catch (UnloadedSidException e) {
            logger.error("Unloaded Sid", e);
        }

        return isGranted;
    }

    private MutableAcl readOrCreateAcl(ObjectIdentity oi) {
        MutableAcl acl = null;
        try {
            acl = (MutableAcl) aclService.readAclById(oi);
        } catch (NotFoundException e) {
            acl = aclService.createAcl(oi);
        }
        return acl;
    }

    private void isPermissionGranted(MutableAcl acl, Sid sid, Permission permission) {
        try {
            acl.isGranted(Arrays.asList(permission), Arrays.asList(sid), false);
        } catch (NotFoundException e) {
            acl.insertAce(acl.getEntries().size(), permission, sid, false);
        }
    }

    public void removeAclFeature() {
        //todo:remove acl feature;
    }
}
