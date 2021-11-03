package com.saimon.Javatodojwt.service;

import com.saimon.Javatodojwt.domain.AppUser;
import com.saimon.Javatodojwt.model.WorkToDo;
import com.saimon.Javatodojwt.repository.UserRepository;
import com.saimon.Javatodojwt.repository.WorkToDoRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WorkToDoServiceImpl implements WorkToDoService {
    private final UserRepository userRepository;
    private final WorkToDoRepository workToDoRepository;
    private final UserServiceImpl userService;

    public WorkToDoServiceImpl(UserRepository userRepository, WorkToDoRepository workToDoRepository, UserServiceImpl userService) {
        this.userRepository = userRepository;
        this.workToDoRepository = workToDoRepository;
        this.userService = userService;
    }


    @Override
    public Optional<WorkToDo> findWorkByid(Optional<AppUser> user, Long id) throws Exception {
        for (WorkToDo work : user.get().getWorks()) {
            if (work.getId() == id) {
                return Optional.of(work);
            }
        }
        return Optional.empty();
    }
}
