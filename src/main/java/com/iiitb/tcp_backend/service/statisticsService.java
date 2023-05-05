package com.iiitb.tcp_backend.service;

import com.iiitb.tcp_backend.clientmodels.DepartmentStats;
import com.iiitb.tcp_backend.repository.DoctorDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;

@Service
@Transactional
public class statisticsService {
    @Autowired
    private DoctorDetailsRepository repo;

    public List<String> getDepartmentName(String start_date, String end_Date){
        System.out.println("Hi");
        List<String> ans = repo.getDepartmentStats(start_date,end_Date);
        System.out.println("Hi");
        return ans;
    }

    public int getDepartmentCount(String department_name,String start_date,String end_date){
        return repo.getDepartmentCount(department_name,start_date,end_date);
    }

    public List<String> getDepartmentNames(){
        return repo.getDepartments();
    }

    public int getDoctorCount(String department_name){
        return repo.getDoctorCount(department_name);
    }

}
