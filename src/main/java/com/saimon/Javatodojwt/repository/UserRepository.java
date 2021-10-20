package com.saimon.Javatodojwt.repository;

import com.saimon.Javatodojwt.domain.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface UserRepository extends JpaRepository<AppUser, Long> {
    AppUser findByUsername(String username);
}
