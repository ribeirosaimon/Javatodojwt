package com.saimon.Javatodojwt.service;

import com.saimon.Javatodojwt.domain.AppUser;
import com.saimon.Javatodojwt.domain.Roles;
import com.saimon.Javatodojwt.model.WorkToDo;
import com.saimon.Javatodojwt.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

public interface UserService {
    AppUser saveUser(AppUser user);
    Roles saveRoles(Roles role);
    void addRoleToUser(String username, String roleName);
    void addWorkToUser(String username, WorkToDo work);
    Optional<WorkToDo> makeWork(String username, WorkToDo work) throws Exception;
    Optional<AppUser> userLogin(HttpServletRequest request, HttpServletResponse response);
    List<WorkToDo> getAllWorks(String username);
    AppUser getUser(String usename);
    List<AppUser> getUsers();
}
