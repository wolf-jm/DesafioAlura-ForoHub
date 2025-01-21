package ForoHub.ByWolf.error;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.bind.validation.ValidationErrors;

import java.util.List;

@Getter
@Setter
public class ErrorResponse {

    private String message;
    private List<ValidationError> errors;

    public ErrorResponse(String message, List<ValidationError> errors) {
        this.message = message;
        this.errors = errors;
    }

    @Getter
    @Setter
    public static class ValidationError {

        private String field;
        private String error;

        public ValidationError(String field, String error) {
            this.field = field;
            this.error = error;
        }
    }
}
