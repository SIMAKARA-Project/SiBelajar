package org.simakara.learning_management_system.dto.request;

import java.util.List;

public record EnrollRequest(
        String courseCode,
        List<String> usernames
) {
}
