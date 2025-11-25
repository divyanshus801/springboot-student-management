package com.practice.RestApi.Controller;

import com.practice.RestApi.Dto.StudentDto;
import com.practice.RestApi.Service.StudentService;

import jakarta.validation.Valid;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping("/studentList")
    public ResponseEntity<List<StudentDto>> getStudent() {
        List<StudentDto> studentList = studentService.getStudent();
        return new ResponseEntity<>(studentList, HttpStatus.OK);
    }

    @PostMapping("/addStudent")
    public ResponseEntity<StudentDto> createStudent(@RequestBody StudentDto studentDto) {
        StudentDto createdStudent = studentService.saveStudent(studentDto);
        return new ResponseEntity<>(createdStudent, HttpStatus.CREATED);
    }

    @PutMapping("updateStudent/{id}")
    public ResponseEntity<StudentDto> updateStudent(@PathVariable Long id, @Valid @RequestBody StudentDto studentDto) {
        StudentDto updatedStudent = studentService.updateStudent(id, studentDto);
        return new ResponseEntity<>(updatedStudent, HttpStatus.CREATED);
    }

    @DeleteMapping("deleteStudent/{id}")
    public ResponseEntity<Map<String, Object>> deleteStudent(@PathVariable Long id){
       Map<String, Object> response = studentService.deleteStudent(id);
       return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
