package com.website.surotec_academy.controller;

import com.website.surotec_academy.domain.dto.student.StudentDto;
import com.website.surotec_academy.domain.dto.student.StudentCreatedDto;
import com.website.surotec_academy.domain.dto.user.UserCreatedDto;
import com.website.surotec_academy.domain.dto.user.UserDto;
import com.website.surotec_academy.entity.UserEntity;
import com.website.surotec_academy.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @Operation(summary = "Obtener un usuarios ", description = "Devuelve los usuario.")
    @ApiResponse(responseCode = "200", description = "Usuario encontrado exitosamente", content = @Content(schema = @Schema(implementation = UserEntity.class)))
    @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    @GetMapping
    public List<StudentDto> getStudents() {
        return studentService.getAllStudents();
    }

    @Operation(summary = "Obtener un usuario por su ID", description = "Devuelve los detalles de un usuario específico basado en su identificador único.")
    @ApiResponse(responseCode = "200", description = "Usuario encontrado exitosamente", content = @Content(schema = @Schema(implementation = UserEntity.class)))
    @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    @GetMapping(value = "/{idStudent}", produces = MediaType.APPLICATION_JSON_VALUE)
    public StudentDto getStudentById(@PathVariable("idStudent") Long id) {
        return studentService.getStudentById(id);
    }

    @Operation(
            summary = "Obtener estudiante por estado",
            description = "Devuelve una lista de estudiante filtrados por su estado."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estudiante encontrados exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = StudentDto.class))),
            @ApiResponse(responseCode = "404", description = "No se encontraron estudiantes con ese estado", content = @Content)
    })
    @GetMapping("/status")
    public List<StudentDto> getStudentsByStatus(@RequestParam("status") String status) {
        return studentService.getStudentsByStatus(status);
    }

    @Operation(
            summary = "Crear un nuevo estudiante",
            description = "Crea un nuevo estudiante en el sistema a partir de los datos enviados en formato JSON."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Estudiante creado exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserCreatedDto.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos enviados", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StudentCreatedDto> addStudent(@RequestBody StudentDto studentDto) {
        StudentDto createdStudent = studentService.createStudent(studentDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(StudentCreatedDto.builder()
                        .idStudent(createdStudent.idStudent())
                        .idUser(createdStudent.userDto().idUser())
                        .build());
    }
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estudiante actualizado exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserCreatedDto.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos enviados", content = @Content),
            @ApiResponse(responseCode = "404", description = "Estudiante no encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @PutMapping(value = "/{idStudent}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StudentCreatedDto> updateStudent(@PathVariable("idStudent") Long id, @RequestBody StudentDto studentDto) {
        StudentDto updatedStudent = studentService.updateStudent(id, studentDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(StudentCreatedDto.builder()
                        .idStudent(updatedStudent.idStudent())
                        .build());
    }

    @Operation(
            summary = "Eliminar un estudiante por su ID",
            description = "Elimina un estudiante existente basado en su identificador único."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Estudiante eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Estudiante no encontrado", content = @Content)
    })
    @DeleteMapping("/{idStudent}")
    public ResponseEntity<Void> deleteStudent(@PathVariable("idStudent") Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }
}
