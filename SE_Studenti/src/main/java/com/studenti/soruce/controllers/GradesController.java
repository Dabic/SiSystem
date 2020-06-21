package com.studenti.soruce.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/students/grades")
public class GradesController {

    @RequestMapping("/get-all-grades")
    public ResponseEntity<String> getAllGrades() {
        return new ResponseEntity<>("get-all-grades", HttpStatus.OK);
    }
}
