package ForoHub.ByWolf.models.dto;

import jakarta.validation.constraints.NotBlank;

public record DataAuthenticateUserService(

        @NotBlank(message = "Usuario requerdo!")
        String login,

        @NotBlank(message = "Password requerido!")
        String password
) {
}
