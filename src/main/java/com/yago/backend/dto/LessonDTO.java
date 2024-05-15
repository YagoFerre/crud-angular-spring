package com.yago.backend.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public record LessonDTO(
        Long id,
        @NotBlank @NotNull @Length(min = 5, max = 100) @Column(length = 100, nullable = false) String name,

        @NotBlank @NotNull @Length(min = 10, max = 11) @Column(length = 11, nullable = false) String youtubeUrl
) {
}
