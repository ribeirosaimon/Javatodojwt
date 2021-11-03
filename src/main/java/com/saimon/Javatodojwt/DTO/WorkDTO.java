package com.saimon.Javatodojwt.DTO;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class WorkDTO implements Serializable {
    private Long id;
    private Date datetime;
    private String work;
    private boolean checked;

    public WorkDTO() {

    }

    public WorkDTO(Long id, Date datetime, String work, boolean checked) {
        this.id = id;
        this.datetime = datetime;
        this.work = work;
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

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
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
        if (!(o instanceof WorkDTO)) return false;
        WorkDTO workDTO = (WorkDTO) o;
        return isChecked() == workDTO.isChecked() && Objects.equals(getId(), workDTO.getId()) && Objects.equals(getDatetime(), workDTO.getDatetime()) && Objects.equals(getWork(), workDTO.getWork());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getDatetime(), getWork(), isChecked());
    }
}
