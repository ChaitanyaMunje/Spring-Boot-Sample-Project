package com.example.APIProject.service.impl;

import com.example.APIProject.dto.AddStudentRequestDto;
import com.example.APIProject.dto.Studentdto;
import com.example.APIProject.entity.Student;
import com.example.APIProject.repository.StudentRepository;
import com.example.APIProject.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class StudentServiceImplementation implements StudentService {


    private final StudentRepository studentRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public StudentServiceImplementation(StudentRepository studentRepository, ModelMapper modelMapper) {
        this.studentRepository = studentRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<Studentdto> getAllStudents() {
        List<Student> students = studentRepository.findAll();
        return students
                .stream()
                .map(student -> new Studentdto(student.getId(), student.getName(), student.getEmail()))
                .toList();

    }

    @Override
    public Studentdto getStudentByID(Long id) {
        Student student = studentRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("Student with id " + id + " not found"));
       return modelMapper.map(student, Studentdto.class);
    }

    @Override
    public Studentdto createNewStudent(AddStudentRequestDto addStudentRequestDto) {
        Student newstudent = modelMapper.map(addStudentRequestDto, Student.class);
        Student student = studentRepository.save(newstudent);
        return modelMapper.map(student, Studentdto.class);
    }

    @Override
    public void deleteStudentById(Long id) {
        if(!studentRepository.existsById(id)){
            throw new IllegalArgumentException("Student with id " + id + " not found");
        }else{
            studentRepository.deleteById(id);
        }
    }

    @Override
    public Studentdto updateStudentById(Long id, AddStudentRequestDto addStudentRequestDto) {
        Student student = studentRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("Student with id " + id + " not found"));
        modelMapper.map(addStudentRequestDto, student);
        studentRepository.save(student);
        return modelMapper.map(student, Studentdto.class);
    }

    @Override
    public Studentdto updatePartialStudent(Long id, Map<String, Object> updatedStudent) {

        Student student = studentRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("Student with id " + id + " not found"));

            updatedStudent.forEach((fieldName, fieldValue) -> {
                switch (fieldName) {
                    case "name":
                        student.setName((String) fieldValue);
                        break;
                    case "email":
                        student.setEmail((String) fieldValue);
                        break;
                    default:
                        throw new IllegalArgumentException("Field is not supported");
                }
            });

            Student savedStudent = studentRepository.save(student);
            return modelMapper.map(savedStudent, Studentdto.class);

    }


}
