package com.saimon.Javatodojwt.repository;

import com.saimon.Javatodojwt.domain.Roles;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RolesRepository extends JpaRepository<Roles, Long> {
    Roles findByName(String username);
}
