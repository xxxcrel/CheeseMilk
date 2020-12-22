package com.cheese.database.repository;

import com.cheese.model.entity.ManagerGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManageGroupRepository extends JpaRepository<ManagerGroup, Long> {
}
