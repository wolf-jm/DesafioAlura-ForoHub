package ForoHub.ByWolf.models.dto;


import ForoHub.ByWolf.models.Course;
import ForoHub.ByWolf.models.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record TopicRegistryData(

        @NotBlank(message = "El titulo no puede estar vacio.")
        String title,

        @NotBlank(message = "El mensaje no puede estar vacio.")
        String message,

        @NotNull
        Status status,

        @NotBlank(message = "El autor no puede estar vacio.")
        String author,

        @NotNull
        Course course
) {
    public LocalDateTime creation_date() {
        return LocalDateTime.now();
    }
}
