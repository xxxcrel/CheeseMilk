package beer.cheese.repository;

import beer.cheese.model.entity.Course;
import beer.cheese.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {

    List<Course> findAllByUser(User user);

    @Modifying
    void deleteAllByUser(User user);

}
