package com.mariworld.imageboard.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class SampleController {

    @GetMapping("/sample")
    public String sample1(){
        log.info("sample.....");
        return "blank";
    }
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/role")
    public String sample2(){
        log.info("role.....");
        return "blank";
    }
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/role_user")
    public String sample3(){
        log.info("role_user.....");
        return "blank";
    }

}
