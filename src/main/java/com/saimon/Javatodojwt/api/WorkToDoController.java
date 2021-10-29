package com.saimon.Javatodojwt.api;

import com.saimon.Javatodojwt.DTO.DTOConverter;
import com.saimon.Javatodojwt.filter.CustomAuthenticationFilter;
import com.saimon.Javatodojwt.model.WorkToDo;
import com.saimon.Javatodojwt.repository.WorkToDoRepository;
import com.saimon.Javatodojwt.service.UserService;
import com.saimon.Javatodojwt.service.WorkToDoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URI;

@RestController
@RequestMapping("/api/todo")
public class WorkToDoController {
    Logger log = LoggerFactory.getLogger(CustomAuthenticationFilter.class);
    private final WorkToDoRepository workToDoRepository;
    private final UserService userService;
    private final DTOConverter dtoConverter;
    private final WorkToDoService workService;

    public WorkToDoController(WorkToDoRepository workToDoRepository, UserService userService, DTOConverter dtoConverter, WorkToDoService workService) {
        this.workToDoRepository = workToDoRepository;
        this.userService = userService;
        this.dtoConverter = dtoConverter;
        this.workService = workService;
    }

    @GetMapping("/works")
    public ResponseEntity<?> getWorks(HttpServletRequest request, HttpServletResponse response, WorkToDo work) throws Exception {
        URI uri = URI.create(ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("api/todo/works")
                .toString());
        var user = userService.userLogin(request, response);
        var listWork = user.get().getWorks();
        for (WorkToDo w:listWork) {
            log.info(w.getHomeWork());

        }
        var listWorkDto = dtoConverter.convertDTOList(listWork);
        return ResponseEntity.created(uri).body(listWorkDto.orElseThrow(() -> new Exception("User Error")));
    }

    @PostMapping("/work/save")
    public ResponseEntity<WorkToDo> saveWork(HttpServletRequest request, HttpServletResponse response, @RequestBody WorkToDo work) throws Exception{
        URI uri = URI.create(ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("api/todo/work/save")
                .toString());
        var user = userService.userLogin(request, response);
        var newWork = workService.saveWork(user, work);
        var newWorkDTO = dtoConverter.converterDTO(newWork.get());
        return ResponseEntity.created(uri).body(newWork.orElseThrow(() -> new Exception("Error save work")));
    }
}
