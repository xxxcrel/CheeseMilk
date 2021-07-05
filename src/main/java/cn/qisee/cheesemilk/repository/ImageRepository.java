package cn.qisee.cheesemilk.repository;

import cn.qisee.cheesemilk.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.util.Streamable;

public interface ImageRepository extends JpaRepository<Image, Long> {

    Streamable<Image> getAllById(Long id);

}
