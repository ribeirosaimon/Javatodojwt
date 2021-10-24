package com.saimon.Javatodojwt.api;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.saimon.Javatodojwt.domain.AppUser;
import com.saimon.Javatodojwt.model.WorkToDo;
import com.saimon.Javatodojwt.repository.WorkToDoRepository;
import com.saimon.Javatodojwt.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.nio.charset.StandardCharsets;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RestController
@RequestMapping("/api/todo")
public class WorkToDoController {

    private final WorkToDoRepository workToDoRepository;
    private final UserService userService;

    public WorkToDoController(WorkToDoRepository workToDoRepository, UserService userService) {
        this.workToDoRepository = workToDoRepository;
        this.userService = userService;
    }

    @GetMapping("/works")
    public ResponseEntity<?> getWorks(HttpServletRequest request, HttpServletResponse response, WorkToDo work) throws Exception {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                String refresh_token = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes(StandardCharsets.UTF_8));
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refresh_token);
                String username = decodedJWT.getSubject();
                AppUser user = userService.getUser(username);
                return ResponseEntity.ok(user);
            } catch (Exception exception) {
                return ResponseEntity.ok(work);
            }
        }
        throw new Exception("Error");
    }
}
