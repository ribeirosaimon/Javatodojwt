package com.saimon.Javatodojwt.Form;

import java.util.Objects;

public class RoleToUserForm {
    private String username;
    private String roleName;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RoleToUserForm)) return false;
        RoleToUserForm that = (RoleToUserForm) o;
        return Objects.equals(getUsername(), that.getUsername()) && Objects.equals(getRoleName(), that.getRoleName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUsername(), getRoleName());
    }
}
