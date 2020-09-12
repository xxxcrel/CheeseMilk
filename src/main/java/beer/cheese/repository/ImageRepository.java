package beer.cheese.repository;

import beer.cheese.model.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.util.Streamable;

public interface ImageRepository extends JpaRepository<Image, Long> {

    Streamable<Image> getAllById(Long id);

}
