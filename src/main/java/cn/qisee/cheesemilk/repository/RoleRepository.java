package cn.qisee.cheesemilk.repository;

import cn.qisee.cheesemilk.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByRoleName(String roleName);
}

