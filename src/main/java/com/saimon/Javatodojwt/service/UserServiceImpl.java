package com.saimon.Javatodojwt.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.saimon.Javatodojwt.domain.AppUser;
import com.saimon.Javatodojwt.domain.Roles;
import com.saimon.Javatodojwt.filter.CustomAuthenticationFilter;
import com.saimon.Javatodojwt.model.WorkToDo;
import com.saimon.Javatodojwt.repository.RolesRepository;
import com.saimon.Javatodojwt.repository.UserRepository;
import com.saimon.Javatodojwt.repository.WorkToDoRepository;
import org.hibernate.jdbc.Work;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    Logger log = LoggerFactory.getLogger(CustomAuthenticationFilter.class);
    private final UserRepository userRepository;
    private final RolesRepository rolesRepository;
    private final PasswordEncoder passwordEncoder;
    private final WorkToDoRepository workToDoRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found in the database");
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                authorities);
    }

    public UserServiceImpl(UserRepository userRepository, RolesRepository rolesRepository, PasswordEncoder passwordEncoder, WorkToDoRepository workToDoRepository) {
        this.userRepository = userRepository;
        this.rolesRepository = rolesRepository;
        this.passwordEncoder = passwordEncoder;
        this.workToDoRepository = workToDoRepository;
    }


    @Override
    public AppUser saveUser(AppUser user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public Roles saveRoles(Roles role) {
        return rolesRepository.save(role);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        AppUser user = userRepository.findByUsername(username);
        Roles role = rolesRepository.findByName(roleName);
        user.getRoles().add(role);
        userRepository.save(user);
    }

    @Override
    public void addWorkToUser(String username, WorkToDo work) {
        AppUser user = userRepository.findByUsername(username);
        user.getWorks().add(work);
        userRepository.save(user);

    }

    @Override
    public Optional<WorkToDo> makeWork(String username, WorkToDo work) throws Exception {
        AppUser user = userRepository.findByUsername(username);
        for (WorkToDo works:workToDoRepository.findAll()) {
            if (work.getHomeWork().toString().equals(works.getHomeWork().toString())){
                var foundWork = workToDoRepository.findById(works.getId());
                foundWork.get().setChecked(true);
                workToDoRepository.save(foundWork.get());
                return foundWork;
            }

        }
        WorkToDo foundWork = workToDoRepository.findById(work.getId()).get();
        log.info(foundWork.toString());
        foundWork.setChecked(true);
        workToDoRepository.save(foundWork);
        return Optional.of(foundWork);
    }

    @Override
    public Optional<AppUser> userLogin(HttpServletRequest request, HttpServletResponse response) {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String refresh_token = authorizationHeader.substring("Bearer ".length());
            Algorithm algorithm = Algorithm.HMAC256("secret".getBytes(StandardCharsets.UTF_8));
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT decodedJWT = verifier.verify(refresh_token);
            String username = decodedJWT.getSubject();
            AppUser user = userRepository.findByUsername(username);
            return Optional.of(user);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public List<WorkToDo> getAllWorks(String username) {
        List<WorkToDo> listWorks = new ArrayList<>();
        AppUser user = userRepository.findByUsername(username);
        for (WorkToDo work:user.getWorks()) {
            if (!work.isChecked()){
                listWorks.add(work);
            }
        }
        return listWorks;
    }

    @Override
    public AppUser getUser(String usename) {
        return userRepository.findByUsername(usename);
    }

    @Override
    public List<AppUser> getUsers() {
        return userRepository.findAll();
    }

}
