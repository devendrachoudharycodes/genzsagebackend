package com.genzsage.genzsage.globalconfigurations;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data // Generates getters, setters, toString, etc.
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL) // Hides null fields in the JSON response
public class GlobalResponseDTO<T> {

    private boolean success;
    private int statusCode;
    private String message;
    private LocalDateTime timestamp; // Good for debugging

    // The successful payload
    private T data;

    // The list of errors (only populated on failure)
    private List<String> errors;

    // --- Static Factory Methods for cleaner usage ---

    public static <T> GlobalResponseDTO<T> success(T data, String message) {
        return GlobalResponseDTO.<T>builder()
                .success(true)
                .statusCode(200)
                .message(message)
                .data(data)
                .timestamp(LocalDateTime.now())
                .build();
    }

    public static <T> GlobalResponseDTO<T> failure(int statusCode, String message, List<String> errors) {
        return GlobalResponseDTO.<T>builder()
                .success(false)
                .statusCode(statusCode)
                .message(message)
                .errors(errors)
                .timestamp(LocalDateTime.now())
                .build();
    }
}