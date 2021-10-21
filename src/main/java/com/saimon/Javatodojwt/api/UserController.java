package com.saimon.Javatodojwt.api;

import com.saimon.Javatodojwt.Form.RoleToUserForm;
import com.saimon.Javatodojwt.domain.AppUser;
import com.saimon.Javatodojwt.domain.Roles;
import com.saimon.Javatodojwt.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

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
    public ResponseEntity<AppUser> saveUser(@RequestBody AppUser user){
        URI uri = URI.create(ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("api/user/save")
                .toString());
        return ResponseEntity.created(uri).body(userService.saveUser(user));
    }

    @PostMapping("/role/save")
    public ResponseEntity<Roles> saveRoles(@RequestBody Roles role){
        URI uri = URI.create(ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("api/role/save")
                .toString());
        return ResponseEntity.created(uri).body(userService.saveRoles(role));
    }

    @PostMapping("/role/addtouser")
    public ResponseEntity<?> roleToUserForm(@RequestBody RoleToUserForm form){
        userService.addRoleToUser(form.getUsername(), form.getRoleName());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/teste")
    public String testeapi(){
        userService.saveRoles(new Roles("ROLE_USER"));
        userService.saveRoles(new Roles("ROLE_MANAGER"));
        userService.saveRoles(new Roles("ROLE_ADMIN"));
        userService.saveRoles(new Roles("ROLE_SUPER_ADMIN"));
        userService.saveUser(new AppUser(
                        "nameadmin",
                        "admin",
                        "admin",
                        new ArrayList<>()
                )
        );
        userService.saveUser(new AppUser(
                        "1nameadmin",
                        "admin1",
                        "admin1",
                        new ArrayList<>()
                )
        );
        userService.addRoleToUser("admin", "ROLE_USER");
        userService.addRoleToUser("admin1", "ROLE_SUPER_ADMIN");
        return "ok";
    }
//    https://www.youtube.com/watch?v=VVn9OG9nfH0&t=5624s

}
