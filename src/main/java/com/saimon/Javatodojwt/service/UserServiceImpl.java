package com.saimon.Javatodojwt.service;

import com.saimon.Javatodojwt.domain.AppUser;
import com.saimon.Javatodojwt.domain.Roles;
import com.saimon.Javatodojwt.repository.RolesRepository;
import com.saimon.Javatodojwt.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final RolesRepository rolesRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("USer not found in the database");
        }
        Collection<SimpleGrantedAuthority> authorites = new ArrayList<>();
        user.getRoles().forEach(roles -> authorites.add(new SimpleGrantedAuthority(roles.getName())));
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                authorites);
    }

    public UserServiceImpl(UserRepository userRepository, RolesRepository rolesRepository) {
        this.userRepository = userRepository;
        this.rolesRepository = rolesRepository;
    }


    @Override
    public AppUser saveUser(AppUser user) {
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
    public AppUser getUser(String usename) {
        return userRepository.findByUsername(usename);
    }

    @Override
    public List<AppUser> getUsers() {
        return userRepository.findAll();
    }


}
