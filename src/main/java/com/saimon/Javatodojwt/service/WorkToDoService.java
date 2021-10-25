package com.saimon.Javatodojwt.service;

import com.saimon.Javatodojwt.domain.AppUser;
import com.saimon.Javatodojwt.domain.Roles;
import com.saimon.Javatodojwt.model.WorkToDo;

import java.util.List;

public interface WorkToDoService {
    WorkToDo saveWork(WorkToDo work);
    List<WorkToDo> getWorks();
}
