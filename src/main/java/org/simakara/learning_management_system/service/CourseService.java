package org.simakara.learning_management_system.service;

import org.simakara.learning_management_system.dto.request.CreateCourseRequest;
import org.simakara.learning_management_system.dto.request.EnrollRequest;
import org.simakara.learning_management_system.dto.request.UpdateCourseRequest;
import org.simakara.learning_management_system.dto.response.CourseResponse;
import org.simakara.learning_management_system.dto.response.EnrollResponse;

import java.util.List;

public interface CourseService {

    CourseResponse createCourse(CreateCourseRequest request);

    CourseResponse getCourseInfo(String code);

    List<CourseResponse> getCourses(String name, int page, int content);

    CourseResponse updateCourse(UpdateCourseRequest request, String code);

    EnrollResponse enrollStudentToCourse(EnrollRequest request);

    EnrollResponse removeStudentFromCourse(EnrollRequest request);

    void deleteCourse(String code);
}
