package com.saimon.Javatodojwt.api;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.saimon.Javatodojwt.Form.RoleToUserForm;
import com.saimon.Javatodojwt.domain.AppUser;
import com.saimon.Javatodojwt.domain.Roles;
import com.saimon.Javatodojwt.filter.CustomAuthenticationFilter;
import com.saimon.Javatodojwt.model.WorkToDo;
import com.saimon.Javatodojwt.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RestController
@RequestMapping("/api")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<AppUser>> getUsers() {
        return ResponseEntity.ok().body(userService.getUsers());
    }

    @PostMapping("/user/save")
    public ResponseEntity<AppUser> saveUser(@RequestBody AppUser user) {
        URI uri = URI.create(ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("api/user/save")
                .toString());
        return ResponseEntity.created(uri).body(userService.saveUser(user));
    }

    @PostMapping("/role/save")
    public ResponseEntity<Roles> saveRoles(@RequestBody Roles role) {
        URI uri = URI.create(ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("api/role/save")
                .toString());
        return ResponseEntity.created(uri).body(userService.saveRoles(role));
    }

    @PostMapping("/role/addtouser")
    public ResponseEntity<?> roleToUserForm(@RequestBody RoleToUserForm form) {
        userService.addRoleToUser(form.getUsername(), form.getRoleName());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        Logger log = LoggerFactory.getLogger(CustomAuthenticationFilter.class);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                String refresh_token = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes(StandardCharsets.UTF_8));
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refresh_token);
                String username = decodedJWT.getSubject();
                AppUser user = userService.getUser(username);
                String acess_token = JWT.create()
                        .withSubject(user.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 100))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles", user.getRoles().stream().map(Roles::getName).collect(Collectors.toList()))
                        .sign(algorithm);
                Map<String, String> tokens = new HashMap<>();
                tokens.put("acess_token", acess_token);
                tokens.put("refresh_token", refresh_token);

                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);
            } catch (Exception exception) {
                log.error("Error loggin in: {}", exception.getMessage());
                response.setHeader("error", exception.getMessage());
                response.setStatus(HttpStatus.FORBIDDEN.value());
                Map<String, String> error = new HashMap<>();
                error.put("error_message", exception.getMessage());
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }
        } else {
            throw new RuntimeException("Refresh token is missing");
        }
    }

    @GetMapping("/teste")
    public String testeapi() {
        userService.saveRoles(new Roles("ROLE_USER"));
        userService.saveRoles(new Roles("ROLE_MANAGER"));
        userService.saveRoles(new Roles("ROLE_ADMIN"));
        userService.saveRoles(new Roles("ROLE_SUPER_ADMIN"));
        userService.saveUser(new AppUser(
                        "nameadmin",
                        "admin",
                        "admin",
                        new ArrayList<>(),
                        new ArrayList<>()
                )
        );
        userService.saveUser(new AppUser(
                        "1nameadmin",
                        "admin1",
                        "admin1",
                        new ArrayList<>(),
                        new ArrayList<>()
                )
        );

        userService.addRoleToUser("admin", "ROLE_USER");
        userService.addRoleToUser("admin1", "ROLE_SUPER_ADMIN");

        WorkToDo work = new WorkToDo(new Date(), "Fort", false, userService.getUser("admin"));

        userService.addWorkToUser("admin", work);
        return "ok";
    }
//    https://www.youtube.com/watch?v=VVn9OG9nfH0&t=5624s

}
