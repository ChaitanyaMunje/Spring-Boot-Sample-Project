package com.example.APIProject.service;

import com.example.APIProject.dto.AddStudentRequestDto;
import com.example.APIProject.dto.Studentdto;

import java.util.List;
import java.util.Map;
import java.util.Optional;


public interface StudentService {
    List<Studentdto> getAllStudents();

    Studentdto getStudentByID(Long id);

    Studentdto createNewStudent(AddStudentRequestDto addStudentRequestDto);

    void deleteStudentById(Long id);

    Studentdto updateStudentById(Long id, AddStudentRequestDto addStudentRequestDto);

    Studentdto updatePartialStudent(Long id, Map<String, Object> updatedStudent);
}
