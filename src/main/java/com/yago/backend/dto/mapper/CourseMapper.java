package com.yago.backend.dto.mapper;

import com.yago.backend.dto.CourseDTO;
import com.yago.backend.dto.LessonDTO;
import com.yago.backend.enums.Category;
import com.yago.backend.enums.Status;
import com.yago.backend.model.Course;
import com.yago.backend.model.Lesson;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CourseMapper {

    public CourseDTO toDTO(Course course) {
        if (course == null) {
            return null;
        }

        List<LessonDTO> lessonsDTO = course.getLessons()
                .stream()
                .map(lesson -> new LessonDTO(lesson.getId(), lesson.getName(), lesson.getYoutubeUrl()))
                .collect(Collectors.toList());

        return new CourseDTO(
                course.getId(),
                course.getName(),
                course.getCategory().getValue(),
                lessonsDTO
        );
    }

    public Course toEntity(CourseDTO dto) {
        if (dto == null) {
            return null;
        }

        Course course = new Course();
        if (dto.id() != null) {
            course.setId(dto.id());
        }

        List<Lesson> lessons = dto.lessons().stream()
                        .map(lessonDTO -> new Lesson(lessonDTO.id(), lessonDTO.name(), lessonDTO.youtubeUrl(), course))
                                .collect(Collectors.toList());

        course.setName(dto.name());
        course.setCategory(convertCategoryValue(dto.category()));
        course.setStatus(Status.ACTIVE);
        course.setLessons(lessons);
        return course;
    }

    public Category convertCategoryValue(String value) {
        if (value == null) {
            return null;
        }

        return switch (value) {
            case "Front-end" -> Category.FRONT_END;
            case "Back-end" -> Category.BACK_END;
            default -> throw new IllegalArgumentException("Categoria inválida: " + value);
        };
    }

}
