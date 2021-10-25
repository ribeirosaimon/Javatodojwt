package com.saimon.Javatodojwt.service;

import com.saimon.Javatodojwt.model.WorkToDo;
import com.saimon.Javatodojwt.repository.UserRepository;
import com.saimon.Javatodojwt.repository.WorkToDoRepository;

import java.util.List;

public class WorkToDoServiceImpl implements WorkToDoService{
    private final UserRepository userRepository;
    private final WorkToDoRepository workToDoRepository;

    public WorkToDoServiceImpl(UserRepository userRepository, WorkToDoRepository workToDoRepository) {
        this.userRepository = userRepository;
        this.workToDoRepository = workToDoRepository;
    }

    @Override
    public WorkToDo saveWork(WorkToDo work) {
        return null;
    }

    @Override
    public List<WorkToDo> getWorks() {
        return null;
    }
}
