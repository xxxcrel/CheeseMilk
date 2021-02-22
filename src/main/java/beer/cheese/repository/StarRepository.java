package beer.cheese.repository;

import beer.cheese.model.Star;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StarRepository extends JpaRepository<Star, Star.StarPK> {

}
