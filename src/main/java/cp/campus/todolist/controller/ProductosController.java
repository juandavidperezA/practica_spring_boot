package cp.campus.todolist.controller;

import cp.campus.todolist.dto.CategoriaResponse;
import cp.campus.todolist.dto.ProductosRequest;
import cp.campus.todolist.dto.ProductosResponse;
import cp.campus.todolist.service.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/API/Productos")
@Tag(name = "Producto",description = "Creacion y consulta de productos")
public class ProductosController {
    private final ProductoService productoService;

    public ProductosController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @PostMapping
    @Operation(summary = "Crear productos")//comentario en el endpoint para saber que hace
    public ResponseEntity<ProductosResponse> crear(@Valid @RequestBody ProductosRequest productosRequest){
        ProductosResponse resultado= productoService.crear(productosRequest);

        //Que es URI
        URI ubicacion= ServletUriComponentsBuilder
                .fromCurrentRequest()//toma la URL de inicio (de partida)
                .path("/{id}")//agrega "/{id}" a la ruta
                .buildAndExpand(resultado.id())// remplaza "/{id}" al id real
                .toUri();//Construye un objeto URI (que es un objeto URI)

        return  ResponseEntity.created(ubicacion).body(resultado);
    }

    @GetMapping
    @Operation(summary = "Listar productos")//comentario en el endpoint para saber que hace
    public ResponseEntity<List<ProductosResponse>> listar(){
        return ResponseEntity.ok(productoService.listar());
    }

    @GetMapping("/{id}")//le agrega esto a la ruta final y el PathVariable agarra el id ingresado y lo cambia al id de la url
    @Operation(summary = "Buscar productos por su id")//comentario en el endpoint para saber que hace
    public ResponseEntity<ProductosResponse> buscarPorId(@PathVariable @Positive Long id){
        return productoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(()->ResponseEntity.notFound().build());
    }
    @GetMapping("/categoria/{categoriaId}")
    @Operation(summary = "Listar por categoria del producto")//comentario en el endpoint para saber que hace
    public ResponseEntity<List<ProductosResponse>> listarPorCategoria(@Positive @PathVariable("categoriaId") Long categoriaId){
        return ResponseEntity.ok(productoService.listarPorCategoria(categoriaId));
    }
}
