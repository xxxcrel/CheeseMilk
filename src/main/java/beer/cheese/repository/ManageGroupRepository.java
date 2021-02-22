package beer.cheese.repository;

import beer.cheese.model.ManagerGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManageGroupRepository extends JpaRepository<ManagerGroup, Long> {
}
