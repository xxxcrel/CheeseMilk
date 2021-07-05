package cn.qisee.cheesemilk.repository;

import cn.qisee.cheesemilk.entity.Course;
import cn.qisee.cheesemilk.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {

    List<Course> findAllByUser(User user);

    @Modifying
    void deleteAllByUser(User user);

}
