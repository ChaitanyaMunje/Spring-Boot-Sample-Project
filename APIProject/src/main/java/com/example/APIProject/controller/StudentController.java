package com.example.APIProject.controller;

import com.example.APIProject.dto.AddStudentRequestDto;
import com.example.APIProject.dto.Studentdto;
import com.example.APIProject.service.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService ;

    @GetMapping("/students")
    public ResponseEntity<List<Studentdto>> getAllStudents(){
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    @GetMapping("/students/{id}")
    public ResponseEntity<Studentdto> getStudentById(@PathVariable Long id){
        return ResponseEntity.ok(studentService.getStudentByID(id));
    }

    @PostMapping("/students")
    public ResponseEntity<Studentdto> createStudent(@RequestBody @Valid AddStudentRequestDto addStudentRequestDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(studentService.createNewStudent(addStudentRequestDto));

    }

    @DeleteMapping("/students/{id}")
    public ResponseEntity<Studentdto> deleteStudentById(@PathVariable Long id){
        studentService.deleteStudentById(id);
        return ResponseEntity.noContent().build();

    }

    // PUT method is used to completely update the json object
    @PutMapping("/students/{id}")
    public ResponseEntity<Studentdto> updateStudent(@PathVariable Long id,@RequestBody AddStudentRequestDto addStudentRequestDto){
        return ResponseEntity.ok(studentService.updateStudentById(id,addStudentRequestDto));
    }

    // PATCH method is used to do partial updates. It is used to do partial updates in patch method.
    @PatchMapping("/students/{id}")
    public ResponseEntity<Studentdto> updatePartialStudent(@PathVariable Long id,@RequestBody Map<String,Object> updatedStudent){
        return ResponseEntity.ok(studentService.updatePartialStudent(id,updatedStudent));
    }
}
