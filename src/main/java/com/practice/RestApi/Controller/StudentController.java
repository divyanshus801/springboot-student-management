package com.practice.RestApi.Controller;

import com.practice.RestApi.Dto.StudentDto;
import com.practice.RestApi.Service.StudentService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
public class StudentController {
    
    @Autowired
    private StudentService studentService;

    @GetMapping("/studentList")
    public ResponseEntity<List<StudentDto>>getStudent(){
        List<StudentDto> studentList = studentService.getStudent();
        return new ResponseEntity<>(studentList, HttpStatus.OK);
    }

    @PostMapping("/addStudent")
    public ResponseEntity<StudentDto>createStudent(@RequestBody StudentDto studentDto) {
        StudentDto createdStudent = studentService.saveStudent(studentDto);
        return new ResponseEntity<>(createdStudent, HttpStatus.CREATED);
    }
    
}
