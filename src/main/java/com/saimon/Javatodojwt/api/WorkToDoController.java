package com.saimon.Javatodojwt.api;

import com.saimon.Javatodojwt.DTO.DTOConverter;
import com.saimon.Javatodojwt.DTO.WorkDTO;
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
import java.util.Date;

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
        var listWork = userService.getAllWorks(user.get().getUsername());
        var listWorkDto = dtoConverter.convertDTOList(listWork);
        return ResponseEntity.created(uri).body(listWorkDto.orElseThrow(() -> new Exception("User Error")));
    }

    @PostMapping("/work/checked")
    public ResponseEntity<WorkDTO> checkedWork(HttpServletRequest request, HttpServletResponse response, @RequestBody WorkToDo work) throws Exception {
        URI uri = URI.create(ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("api/todo/work/checked")
                .toString());
        var user = userService.userLogin(request, response);
        var newWork = userService.makeWork(user.get().getUsername(), work);
        var workDTO = dtoConverter.converterDTO(newWork.get());
        return ResponseEntity.created(uri).body(workDTO.orElseThrow(()-> new Exception("Error Work")));
    }

    @PostMapping("/work/save")
    public ResponseEntity<WorkDTO> saveWork(HttpServletRequest request, HttpServletResponse response, @RequestBody WorkToDo work) throws Exception {
        URI uri = URI.create(ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("api/todo/work/save")
                .toString());
        var user = userService.userLogin(request, response);
        var newWork = new WorkToDo(new Date(), work.getHomeWork(), false, user.get());
        userService.addWorkToUser(user.get().getUsername(), newWork);
        var newWorkDTO = dtoConverter.converterDTO(newWork);
        return ResponseEntity.created(uri).body(newWorkDTO.orElseThrow(() -> new Exception("Error save work")));
    }
}
