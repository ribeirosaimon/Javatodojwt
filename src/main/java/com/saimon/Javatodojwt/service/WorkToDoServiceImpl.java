package com.saimon.Javatodojwt.service;

import com.saimon.Javatodojwt.domain.AppUser;
import com.saimon.Javatodojwt.model.WorkToDo;
import com.saimon.Javatodojwt.repository.UserRepository;
import com.saimon.Javatodojwt.repository.WorkToDoRepository;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
    public Optional<WorkToDo> saveWork(Optional<AppUser> user, WorkToDo work) throws Exception {
        if (work != null) {
            WorkToDo newWork = new WorkToDo(new Date(), work.getHomeWork(), false, user.get());
            user.get().getWorks().add(work);
            userRepository.save(user.get());
            return Optional.of(newWork);
        }
        throw new Exception("Work is null");
    }

    @Override
    public Optional<WorkToDo> makeWork(HttpServletRequest request, HttpServletResponse response, Long id) throws Exception {
        var user = userService.userLogin(request, response);
        try {
            Optional<WorkToDo> foundWork = findWorkByid(user, id);
            foundWork.get().setChecked(true);
            workToDoRepository.save(foundWork.get());
            return foundWork;
        } catch (Exception e) {
            throw new Exception("Not Work");
        }
    }


    @Override
    public Optional<List<WorkToDo>> getWorks(HttpServletRequest request, HttpServletResponse response) {
        var user = userService.userLogin(request, response);
        ArrayList<WorkToDo> allWorks = new ArrayList<WorkToDo>();
        for (WorkToDo work : user.get().getWorks()) {
            allWorks.add(work);
        }
        return Optional.of(allWorks);
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
