package ForoHub.ByWolf.models.dto;

import ForoHub.ByWolf.models.Course;
import ForoHub.ByWolf.models.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jdk.jshell.Snippet;

public record TopicUpdateData(

        @NotBlank
        String title,

        @NotBlank
        String message,

        @NotNull
        Status status,

        @NotBlank
        String author,

        @NotNull
        Course course
) {
}
