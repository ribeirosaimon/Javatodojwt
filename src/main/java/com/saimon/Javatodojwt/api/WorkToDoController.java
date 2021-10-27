package com.saimon.Javatodojwt.api;

import com.saimon.Javatodojwt.DTO.DTOConverter;
import com.saimon.Javatodojwt.model.WorkToDo;
import com.saimon.Javatodojwt.repository.WorkToDoRepository;
import com.saimon.Javatodojwt.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URI;

@RestController
@RequestMapping("/api/todo")
public class WorkToDoController {

    private final WorkToDoRepository workToDoRepository;
    private final UserService userService;
    private final DTOConverter dtoConverter;

    public WorkToDoController(WorkToDoRepository workToDoRepository, UserService userService, DTOConverter dtoConverter) {
        this.workToDoRepository = workToDoRepository;
        this.userService = userService;
        this.dtoConverter = dtoConverter;
    }

    @GetMapping("/works")
    public ResponseEntity<?> getWorks(HttpServletRequest request, HttpServletResponse response, WorkToDo work) throws Exception {
        URI uri = URI.create(ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("api/todo/works")
                .toString());
        var user = userService.userLogin(request, response);
        var listWork = user.get().getWorks();
        var listWorkDto = dtoConverter.convertDTOList(listWork);
        return ResponseEntity.created(uri).body(listWorkDto.orElseThrow(() -> new Exception("User Error")));
    }
}
