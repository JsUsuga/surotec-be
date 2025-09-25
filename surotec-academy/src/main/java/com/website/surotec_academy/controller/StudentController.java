package com.website.surotec_academy.controller;

import com.website.surotec_academy.domain.dto.request.request.StudentDto;
import com.website.surotec_academy.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @Operation(summary = "Obtener un usuario por su ID", description = "Devuelve los detalles de un usuario específico basado en su identificador único.")
    @ApiResponse(responseCode = "200", description = "Usuario encontrado exitosamente", content = @Content(schema = @Schema(implementation = StudentDto.class)))
    @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    @GetMapping
    public List<StudentDto> getStudents() {
        return studentService.getAllStudents();
    }


    @PostMapping
    public Void addStudent() {
        return null;
    }

    @PutMapping
    Void updateStudent() {
        return null;
    }

    @DeleteMapping
    Void deleteStudent() {
        return null;
    }

   // @GetMapping("/status")
   // List<StudentDto> getStudentByStatus();
}
