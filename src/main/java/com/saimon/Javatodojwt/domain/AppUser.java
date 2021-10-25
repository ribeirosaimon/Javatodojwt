package com.saimon.Javatodojwt.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.saimon.Javatodojwt.model.WorkToDo;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

@Entity
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String username;
    private String password;
    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Roles> roles = new ArrayList<>();
    @JsonIgnoreProperties("appUser")
    @OneToMany(cascade=CascadeType.ALL)
    private Collection<WorkToDo> works;

    public AppUser(){

    }

    public AppUser(String name, String username, String password, Collection<Roles> roles, ArrayList<WorkToDo> works) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.works = works;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Collection<Roles> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Roles> roles) {
        this.roles = roles;
    }

    public Collection<WorkToDo> getWorks() {
        return works;
    }

    public void setWorks(ArrayList<WorkToDo> works) {
        this.works = works;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AppUser)) return false;
        AppUser appUser = (AppUser) o;
        return Objects.equals(getId(), appUser.getId()) && Objects.equals(getName(), appUser.getName()) && Objects.equals(getUsername(), appUser.getUsername()) && Objects.equals(getPassword(), appUser.getPassword()) && Objects.equals(getRoles(), appUser.getRoles()) && Objects.equals(getWorks(), appUser.getWorks());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getUsername(), getPassword(), getRoles(), getWorks());
    }
}
