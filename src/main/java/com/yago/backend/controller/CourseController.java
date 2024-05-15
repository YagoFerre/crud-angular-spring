package com.yago.backend.controller;

import com.yago.backend.dto.CourseDTO;
import com.yago.backend.service.CourseService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public ResponseEntity<List<CourseDTO>> list() {
        return ResponseEntity.status(HttpStatus.OK).body(courseService.list());
    }

    @GetMapping("/{id}")
    public CourseDTO findById(@PathVariable @NotNull @Positive Long id) {
        return courseService.findById(id);
    }

    @PostMapping
    public ResponseEntity<CourseDTO> create(@RequestBody @Valid CourseDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(courseService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CourseDTO> update(@PathVariable @NotNull @Positive Long id,
                                            @RequestBody @Valid CourseDTO dto) {
        return ResponseEntity.status(HttpStatus.OK).body(courseService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable @NotNull @Positive Long id) {
       courseService.delete(id);

       return ResponseEntity.noContent().<Void>build();
    }

}
