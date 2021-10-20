package com.saimon.Javatodojwt.service;

import com.saimon.Javatodojwt.domain.AppUser;
import com.saimon.Javatodojwt.domain.Roles;
import com.saimon.Javatodojwt.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UserService {
    AppUser saveUser(AppUser user);
    Roles saveRoles(Roles role);
    void addRoleToUser(String username, String roleName);
    AppUser getUser(String usename);
    List<AppUser> getUsers();
}
