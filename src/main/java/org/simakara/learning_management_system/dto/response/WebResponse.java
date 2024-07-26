package org.simakara.learning_management_system.dto.response;

public record WebResponse<T>(
        T data,
        Integer code,
        String message,
        String error
) {
}
