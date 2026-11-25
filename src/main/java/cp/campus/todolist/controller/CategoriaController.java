package cp.campus.todolist.controller;

import cp.campus.todolist.dto.CategoriaRequest;
import cp.campus.todolist.dto.CategoriaResponse;
import cp.campus.todolist.service.CategoriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController//convierte los dto en objetos JSON y que le llegaran las peticiones HTTP (GET,POST,PUT,DELETE,PATCH)
@RequestMapping("/api/categorias")//es el pedazo de la ruta que nunca va a cambiar ejemplo= akosto.com y siempre para buena practica se usa (/api)
@Tag(name = "categorias",description = "Creacion y consulta de categorias")
public class CategoriaController {
    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    //crea una comparacion de requestBody con @requestparam
    //para despligar con un aparche tomcat y los archivos .JAR
    @PostMapping
    @Operation(summary = "crear categoria")//comentario en el endpoint para saber que hace
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Recurso creado",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CategoriaResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos invalidos",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class
                            )
                    )
            )
    })
    public ResponseEntity<CategoriaResponse> crear(@Valid @RequestBody CategoriaRequest request){
        CategoriaResponse resultado= categoriaService.crear(request);

        //Que es URI
        URI ubicacion= ServletUriComponentsBuilder
                .fromCurrentRequest()//toma la URL de inicio (de partida)
                .path("/{id}")//agrega "/{id}" a la ruta
                .buildAndExpand(resultado.id())// remplaza "/{id}" al id real
                .toUri();//Construye un objeto URI (que es un objeto URI)

        return  ResponseEntity.created(ubicacion).body(resultado);
    }

    @GetMapping
    @Operation(summary = "Listar categoria")//comentario en el endpoint para saber que hace
    public ResponseEntity<List<CategoriaResponse> > listar(){
        return ResponseEntity.ok(categoriaService.listar());
    }

    @GetMapping("/{id}")//le agrega esto a la ruta final y el PathVariable agarra el id ingresado y lo cambia al id de la url
    @Operation(summary = "Buscar categoria por su id")//comentario en el endpoint para saber que hace
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Recurso encontrado",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CategoriaResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "ID invalidos",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Recurso no encontrado",
                    content = @Content
            )
    })
    public ResponseEntity<CategoriaResponse> buscarPorId(@PathVariable @Positive Long id){
        return categoriaService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(()->ResponseEntity.notFound().build());
    }
}
//el swagger es la documentacion que se le da al frontend para que sepa que hacer