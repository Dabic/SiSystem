package com.studenti.soruce.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/students/computer")
public class ComputerController {

    @RequestMapping("/sum-grades")
    public ResponseEntity<String> sumGrades() {
        return new ResponseEntity<>("sum-grades", HttpStatus.OK);

    }

    @RequestMapping("/avg-grades")
    public ResponseEntity<String> avgGrades() {
        return new ResponseEntity<>("avg-grades", HttpStatus.OK);
    }
}

