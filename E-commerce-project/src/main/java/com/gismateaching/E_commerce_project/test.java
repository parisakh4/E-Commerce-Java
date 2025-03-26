package com.gismateaching.E_commerce_project;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class test {
    @PostMapping ("/add")
    public ResponseEntity<String> testAdd(){
        return ResponseEntity.ok("test success");
    }
}
