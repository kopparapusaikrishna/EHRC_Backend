package com.iiitb.tcp_backend.clientmodels;

public class DepartmentStats {

    private String department_name;
    private int count;

    public DepartmentStats(String department_name, int count) {
        this.department_name = department_name;
        this.count = count;
    }

    public String getDepartment_name() {
        return department_name;
    }

    public void setDepartment_name(String department_name) {
        this.department_name = department_name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
