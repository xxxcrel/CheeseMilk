package beer.cheese.model.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity(name = "Star")
@Table(name = "star")
public class Star implements Serializable {

    @EmbeddedId
    private StarPK starPK;

    public StarPK getStarPK() {
        return starPK;
    }

    public void setStarPK(StarPK starPK) {
        this.starPK = starPK;
    }

    @Embeddable
    public static class StarPK implements Serializable {

        @ManyToOne
        @JoinColumn(name = "user_id")
        private User user;

        @Column(name = "resource_id")
        Long resourceId;

        @Column(name = "resource_type")
        int resourceType;

        public StarPK() {}

        public StarPK(User user, Long resourceId, int resourceType) {
            this.user = user;
            this.resourceType = resourceType;
            this.resourceId = resourceId;
        }

        public Long getResourceId() {
            return resourceId;
        }

        public void setResourceId(Long resourceId) {
            this.resourceId = resourceId;
        }

        public int getResourceType() {
            return resourceType;
        }

        public void setResourceType(int resourceType) {
            this.resourceType = resourceType;
        }

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }

        @Override
        public int hashCode() {
            return Objects.hash(resourceId, resourceId);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (o == null || this.getClass() != o.getClass())
                return false;
            StarPK star = (StarPK) o;
            return user.equals(((StarPK) o).getUser()) && resourceId.equals(star.getResourceId())
                    && resourceType == star.getResourceType();
        }
    }

    public enum ResourceType {
        POST,
        COMMENT
    }


}
