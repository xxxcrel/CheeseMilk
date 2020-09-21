package beer.cheese.repository;

import beer.cheese.model.entity.Star;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StarRepository extends JpaRepository<Star, Star.StarPK> {

}