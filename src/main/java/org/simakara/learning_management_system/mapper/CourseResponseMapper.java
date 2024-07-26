package org.simakara.learning_management_system.mapper;

import org.simakara.learning_management_system.dto.response.CourseResponse;
import org.simakara.learning_management_system.model.Course;

public class CourseResponseMapper {

    public static CourseResponse toCourseResponse(Course course){
        return new CourseResponse(
                course.getName(),
                course.getDescription(),
                course.getCode()
        );
    }
}
