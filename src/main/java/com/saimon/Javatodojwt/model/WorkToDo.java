package com.saimon.Javatodojwt.model;

import com.saimon.Javatodojwt.domain.AppUser;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
public class WorkToDo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date datetime;
    private String homeWork;
    private boolean checked;

    @ManyToOne
    @JoinColumn(name = "app_user_id")
    private AppUser appUser;

    public WorkToDo(){

    }

    public WorkToDo(Date datetime, String homeWork, boolean checked, AppUser appUser) {
        this.datetime = datetime;
        this.homeWork = homeWork;
        this.checked = checked;
        this.appUser = appUser;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    public String getHomeWork() {
        return homeWork;
    }

    public void setHomeWork(String homeWork) {
        this.homeWork = homeWork;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public AppUser getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WorkToDo)) return false;
        WorkToDo workToDo = (WorkToDo) o;
        return isChecked() == workToDo.isChecked() && Objects.equals(getId(), workToDo.getId()) && Objects.equals(getDatetime(), workToDo.getDatetime()) && Objects.equals(getHomeWork(), workToDo.getHomeWork()) && Objects.equals(getAppUser(), workToDo.getAppUser());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getDatetime(), getHomeWork(), isChecked(), getAppUser());
    }
}
