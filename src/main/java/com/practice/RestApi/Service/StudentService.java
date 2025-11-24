package com.practice.RestApi.Service;

import com.practice.RestApi.Dto.StudentDto;
import com.practice.RestApi.Entity.Student;
import com.practice.RestApi.Repository.StudentRepository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public StudentDto saveStudent(StudentDto studentDto) {
        Student student = new Student();

        student.setName(studentDto.getName());
        student.setEmail(studentDto.getEmail());

        Student savedStudent = studentRepository.save(student);

        return new StudentDto(savedStudent.getId(), savedStudent.getName(), savedStudent.getEmail());

    }

    public List<StudentDto> getStudent() {

        List<Student> students = studentRepository.findAll();

        List<StudentDto> studentDtoList = new ArrayList<>();

        for (Student student : students) {
            StudentDto dto = new StudentDto(student.getId(), student.getName(), student.getEmail());
            studentDtoList.add(dto);
        }
        return studentDtoList;
    }

}