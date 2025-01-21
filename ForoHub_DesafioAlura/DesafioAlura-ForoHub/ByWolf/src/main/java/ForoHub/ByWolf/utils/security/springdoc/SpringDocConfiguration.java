package ForoHub.ByWolf.utils.security.springdoc;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.servers.Server;
import lombok.Setter;


@OpenAPIDefinition(
        info = @Info(
                title = "Desafio-Foro_Hub",
                description = "Foro de conversaciones  Random"
        ),
        servers = @Server(
                        description = "Prod Server",
                        url = "http://localhost:8080"
        ),
        security = @SecurityRequirement(
                name = "Security Tokrn"
        )
)

public class SpringDocConfiguration {
}
