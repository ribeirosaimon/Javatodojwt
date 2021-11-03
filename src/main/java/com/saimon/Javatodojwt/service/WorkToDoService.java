package com.saimon.Javatodojwt.service;

import com.saimon.Javatodojwt.domain.AppUser;
import com.saimon.Javatodojwt.model.WorkToDo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

public interface WorkToDoService {
    Optional<WorkToDo> findWorkByid(Optional<AppUser> user, Long id) throws Exception;
}
