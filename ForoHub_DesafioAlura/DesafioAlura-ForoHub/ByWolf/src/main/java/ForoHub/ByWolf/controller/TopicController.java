package ForoHub.ByWolf.controller;

import ForoHub.ByWolf.models.dto.TopicDataList;
import ForoHub.ByWolf.models.dto.TopicRegistryData;
import ForoHub.ByWolf.models.dto.TopicUpdateData;
import ForoHub.ByWolf.repository.TopicRepository;
import ForoHub.ByWolf.service.TopicService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/topics")
//@RequiredArgsConstructor
@Tag(name = "Temas", description = "Operaciones relacionadas con la gestión de temas.")

public class TopicController {

    private final TopicRepository topicRepository;

    private final TopicService topicService;

    public TopicController(TopicRepository topicRepository, TopicService topicService) {
        this.topicRepository = topicRepository;
        this.topicService = topicService;
    }



    @PostMapping
    @Operation(summary = "Crear un nuevo tema",
            description = "Este endpoint crea un nuevo tema basado en los datos proporcionados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Recurso creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
            @ApiResponse(responseCode = "409", description = "Conflicto: el tópico ya existe"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<String> createTopic(@RequestBody @Valid TopicRegistryData topicRegistryData) {
        return topicService.createTopic(topicRegistryData);
    }

//###############

    @GetMapping("/{id}")
    @Operation(summary = "Leer un tema",
            description = "Este endpoint recupera un tema específico utilizando su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tema encontrado"),
            @ApiResponse(responseCode = "404", description = "Tema no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<Object> readTopic(@Parameter(description = "ID del tema a recuperar", required = true)
                                            @PathVariable Long id) {
        return topicService.readTopic(id);
    }

//###############

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un tema",
            description = "Este endpoint actualiza un tema específico utilizando su ID y los datos proporcionados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tema actualizado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "404", description = "Tema no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<String> updateTopic(@Parameter(description = "ID del tema a actualizar", required = true)
                                              @PathVariable Long id,
                                              @Parameter(description = "Datos del tema que se van a actualizar", required = true)
                                              @Valid @RequestBody TopicUpdateData topicUpdateData) {
        return topicService.updateTopic(id, topicUpdateData);
    }
//##############

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un tema",
            description = "Este endpoint elimina un tema específico utilizando su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Tema eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Tema no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<Void> deleteTopic(@Parameter(description = "ID del tema a eliminar", required = true)
                                            @PathVariable Long id) {
        return topicService.deleteTopic(id);
    }

//###############

    @GetMapping
    @Operation(summary = "Listar temas",
            description = "Este endpoint devuelve una lista de temas con paginación.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de temas recuperada exitosamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<PagedModel<EntityModel<TopicDataList>>> listTopics(
            @Parameter(description = "Parámetros de paginación, como el número de página y el tamaño de la página.")
            @PageableDefault(size = 5) Pageable pageable) {
        return topicService.listTopics(pageable);
    }
}