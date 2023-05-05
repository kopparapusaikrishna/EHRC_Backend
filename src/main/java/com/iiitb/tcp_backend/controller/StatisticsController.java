package com.iiitb.tcp_backend.controller;

import com.iiitb.tcp_backend.clientmodels.DepartmentStats;
import com.iiitb.tcp_backend.service.statisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class StatisticsController {

    @Autowired
    statisticsService statService;

    @GetMapping("/departmentStatistics")
    public ResponseEntity<HashMap<String,Integer>> getDepartmentDetails(@RequestParam String start_date, @RequestParam String end_date) {
        try {
            System.out.println(start_date);
            System.out.println(end_date);
            HashMap<String, Integer> ans = new HashMap<String, Integer>();
            List<String> stats = statService.getDepartmentName(start_date,end_date);
            System.out.println("Hello");
            for(String i: stats){
                System.out.println(i);
                ans.put(i,statService.getDepartmentCount(i,start_date,end_date));
            }
            System.out.println("Hi");
            return new ResponseEntity<>(ans, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/doctorDepartmentStatistics")
    public ResponseEntity<HashMap<String,Integer>> getDepartmentDetails() {
        try {

            HashMap<String, Integer> ans = new HashMap<String, Integer>();
            List<String> stats = statService.getDepartmentNames();
            System.out.println("Hello");
            for(String i: stats){
                System.out.println(i);
                ans.put(i,statService.getDoctorCount(i));
            }
            System.out.println("Hi");
            return new ResponseEntity<>(ans, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
