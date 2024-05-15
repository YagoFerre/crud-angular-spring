package com.yago.backend.model;

import com.yago.backend.enums.Category;
import com.yago.backend.enums.Status;
import com.yago.backend.enums.converters.CategoryConverter;
import com.yago.backend.enums.converters.StatusConverter;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.validator.constraints.Length;

import java.util.ArrayList;
import java.util.List;

@Entity
@SQLDelete(sql = "UPDATE course SET status =  'Inativo' WHERE id = ?")
@SQLRestriction("status <> 'Inativo'")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank
    @Length(min = 5, max = 100)
    @Column(length = 100, nullable = false)
    private String name;

    @NotNull
    @Column(length = 10, nullable = false)
    @Convert(converter = CategoryConverter.class)
    private Category category;

    @NotNull
    @Column(length = 10, nullable = false)
    @Convert(converter = StatusConverter.class)
    private Status status = Status.ACTIVE;

    @NotNull
    @NotEmpty
    @Valid
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "course")
//    @JoinColumn(name = "course_id")
    private List<Lesson> lessons = new ArrayList<>();

    public Course() {
    }

    public Course(Long id, String name, Category category, Status status, List<Lesson> lessons) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.status = status;
        this.lessons = lessons;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<Lesson> getLessons() {
        return lessons;
    }

    public void setLessons(List<Lesson> lessons) {
        this.lessons = lessons;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", category=" + category +
                ", status=" + status +
                ", lessons=" + lessons +
                '}';
    }
}
