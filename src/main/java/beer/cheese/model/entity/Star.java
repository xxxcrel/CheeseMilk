package beer.cheese.model.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity(name = "Star")
@Table(name = "star")
@IdClass(Star.StarPK.class)
public class Star implements Serializable {

    @Id
    @Column(name = "resource_id", nullable = false)
    private Long resourceId;

    @Id
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Id
    @Column(name = "resource_type")
    private int resourceType;

    public static class StarPK implements Serializable {

        Long resourceId;

        User user;

        int resourceType;

        public StarPK(){}

        public StarPK(Long resourceId, User user, int resourceType){
            this.resourceType = resourceType;
            this.resourceId = resourceId;
            this.user = user;
        }

        @Override
        public int hashCode() {
            return Objects.hash(resourceId, resourceId);
        }

        @Override
        public boolean equals(Object o) {
            if(this == o)
                return true;
            if(o == null || this.getClass() != o.getClass())
                return false;
            Star star = (Star)o;
            return user.equals(star.getUser()) && resourceId.equals(star.getResourceId())
                    && resourceType == star.getResourceType();
        }
    }

    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getResourceType() {
        return resourceType;
    }

    public void setResourceType(int resourceType) {
        this.resourceType = resourceType;
    }
}
