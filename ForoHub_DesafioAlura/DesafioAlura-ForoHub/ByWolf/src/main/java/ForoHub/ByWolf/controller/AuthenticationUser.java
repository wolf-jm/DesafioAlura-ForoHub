package ForoHub.ByWolf.controller;

import ForoHub.ByWolf.models.UserService;
import ForoHub.ByWolf.models.dto.DataAuthenticationUserService;


import ForoHub.ByWolf.utils.security.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/v1/login")
//@RequiredArgsConstructor
@Tag(name = "Autenticación", description = "Controlador de autenticación")
public class AuthenticationUser {

    private final AuthenticationManager authenticationManager;

    private final TokenService tokenService;

    @Autowired  // Inyección explícita a través del constructor
    public AuthenticationUser(AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }


    @PostMapping
    @Operation(summary = "Registrar un usuario",
            description = "Este endpoint registra un nuevo usuario y genera un token JWT.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Registro exitoso, token generado"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "401", description = "Credenciales inválidas"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<Map<String, String>> register(
            @Parameter(description = "Datos de autenticación del usuario que se va a registrar", required = true)
            @RequestBody @Valid DataAuthenticationUserService dataAuthenticationUserService) {
        Authentication authToken = new org.springframework.security.authentication.UsernamePasswordAuthenticationToken(dataAuthenticationUserService.login(),
                dataAuthenticationUserService.password());
        var authenticatedUser = authenticationManager.authenticate(authToken);
        var JWTtoken = tokenService.generateToken((UserService) authenticatedUser.getPrincipal());
        // Crear un mapa para la respuesta que solo contenga el token
        Map<String, String> response = new HashMap<>();
        response.put("token", JWTtoken);

        return ResponseEntity.ok(response);
    }




}
