package com.yago.backend.service;

import com.yago.backend.dto.CourseDTO;
import com.yago.backend.dto.mapper.CourseMapper;
import com.yago.backend.exception.RecordNotFoundException;
import com.yago.backend.model.Course;
import com.yago.backend.repository.CourseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;

    public CourseService(CourseRepository courseRepository, CourseMapper courseMapper) {
        this.courseRepository = courseRepository;
        this.courseMapper = courseMapper;
    }

    public List<CourseDTO> list() {
        return courseRepository.findAll()
                .stream()
                .map(courseMapper::toDTO)
                .collect(Collectors.toList());
    }

    public CourseDTO findById(Long id) {
        return courseRepository.findById(id)
                .map(courseMapper::toDTO)
                .orElseThrow(() -> new RecordNotFoundException(id));
    }

    public CourseDTO create(CourseDTO dto) {
        return courseMapper.toDTO(courseRepository.save(courseMapper.toEntity(dto)));
    }

    public CourseDTO update(Long id, CourseDTO dto) {
        return courseRepository.findById(id)
                .map(courseFound -> {
                    Course course = courseMapper.toEntity(dto);

                    courseFound.setName(dto.name());
                    courseFound.setCategory(courseMapper.convertCategoryValue(dto.category()));
//                    courseFound.setLessons(course.getLessons());

                    courseFound.getLessons().clear();
                    course.getLessons().forEach(lesson -> courseFound.getLessons().add(lesson));

                    return courseRepository.save(courseFound);
                })
                .map(courseMapper::toDTO)
                .orElseThrow(() -> new RecordNotFoundException(id));
    }

    public void delete(Long id) {
        Course courseToBeRemoved = courseRepository.findById(id)
                        .orElseThrow(() -> new RecordNotFoundException(id));

        courseRepository.delete(courseToBeRemoved);
    }
}
