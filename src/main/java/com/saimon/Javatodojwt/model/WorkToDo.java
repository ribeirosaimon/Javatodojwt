package com.saimon.Javatodojwt.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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

    public WorkToDo(){

    }
    public WorkToDo(Date datetime, String homeWork, boolean checked) {
        this.datetime = datetime;
        this.homeWork = homeWork;
        this.checked = checked;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WorkToDo)) return false;
        WorkToDo workToDo = (WorkToDo) o;
        return isChecked() == workToDo.isChecked() && Objects.equals(getId(), workToDo.getId()) && Objects.equals(getDatetime(), workToDo.getDatetime()) && Objects.equals(getHomeWork(), workToDo.getHomeWork());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getDatetime(), getHomeWork(), isChecked());
    }
}
