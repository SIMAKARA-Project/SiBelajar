package org.simakara.learning_management_system.controller;

import lombok.RequiredArgsConstructor;
import org.simakara.learning_management_system.dto.request.CreateCourseRequest;
import org.simakara.learning_management_system.dto.request.EnrollRequest;
import org.simakara.learning_management_system.dto.request.UpdateCourseRequest;
import org.simakara.learning_management_system.dto.response.CourseResponse;
import org.simakara.learning_management_system.dto.response.EnrollResponse;
import org.simakara.learning_management_system.dto.response.WebResponse;
import org.simakara.learning_management_system.service.CourseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(
        path = "/api/v2/course"
)
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @PostMapping(
            path = "/create",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<WebResponse<CourseResponse>> createCourse(
            @RequestBody CreateCourseRequest request
    ) {
        CourseResponse response = courseService.createCourse(request);

        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        new WebResponse<>(
                                response,
                                HttpStatus.OK.value(),
                                null,
                                null
                        )
                );
    }

    @GetMapping(
            path = "/{code}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<WebResponse<CourseResponse>> getCourseInfo(
            @PathVariable(value = "code") String code
    ) {
        CourseResponse response = courseService.getCourseInfo(code);

        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        new WebResponse<>(
                                response,
                                HttpStatus.OK.value(),
                                null,
                                null
                        )
                );
    }

    @GetMapping(
            path = "/search",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<WebResponse<List<CourseResponse>>> getCourses(
            @RequestParam(value = "name") String name,
            @RequestParam(value = "pg") int page,
            @RequestParam(value = "ctn") int content
    ) {
        List<CourseResponse> response = courseService.getCourses(name, page, content);

        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        new WebResponse<>(
                                response,
                                HttpStatus.OK.value(),
                                null,
                                null
                        )
                );
    }

    @PatchMapping(
            path = "/update/{code}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<WebResponse<CourseResponse>> updateCourse(
            @RequestBody UpdateCourseRequest request,
            @PathVariable(value = "code") String code
    ) {
        CourseResponse response = courseService.updateCourse(request, code);

        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        new WebResponse<>(
                                response,
                                HttpStatus.OK.value(),
                                null,
                                null
                        )
                );
    }

    @PostMapping(
            path = "/enroll",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<WebResponse<EnrollResponse>> enrollStudent(
            @RequestBody EnrollRequest request
    ) {
        EnrollResponse response = courseService.enrollStudentToCourse(request);

        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        new WebResponse<>(
                                response,
                                HttpStatus.OK.value(),
                                null,
                                null
                        )
                );
    }

    @PostMapping(
            path = "/enroll/remove",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<WebResponse<EnrollResponse>> removeStudent(
            @RequestBody EnrollRequest request
    ) {
        EnrollResponse response = courseService.removeStudentFromCourse(request);

        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        new WebResponse<>(
                                response,
                                HttpStatus.OK.value(),
                                null,
                                null
                        )
                );
    }

    @DeleteMapping(
            path = "/delete/{code}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<WebResponse<String>> deletedCourse(
            @PathVariable(value = "code") String code
    ) {
        courseService.deleteCourse(code);

        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        new WebResponse<>(
                                "Course "+code+" deleted.",
                                HttpStatus.OK.value(),
                                null,
                                null
                        )
                );
    }
}
