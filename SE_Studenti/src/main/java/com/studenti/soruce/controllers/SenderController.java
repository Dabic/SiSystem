package com.studenti.soruce.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/students/sender")
public class SenderController {

    @RequestMapping("/send-to-students")
    public ResponseEntity<String> getAllGrades() {
        return new ResponseEntity<>("sent-results", HttpStatus.OK);
    }

}
