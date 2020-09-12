package beer.cheese.repository;

import beer.cheese.model.entity.ManagerGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManageGroupRepository extends JpaRepository<ManagerGroup, Long> {
}
