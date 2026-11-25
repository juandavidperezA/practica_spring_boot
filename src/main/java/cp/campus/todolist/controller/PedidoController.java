package cp.campus.todolist.controller;

import cp.campus.todolist.dto.PedidoRequest;
import cp.campus.todolist.dto.PedidoResponse;
import cp.campus.todolist.dto.ProductosRequest;
import cp.campus.todolist.dto.ProductosResponse;
import cp.campus.todolist.service.PedidoService;
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
@RequestMapping("/API/Pedidos")
@Tag(name = "Pedidos",description = "Creacion y consulta de pedidos")
public class PedidoController {
    private final PedidoService pedidoService;


    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping
    @Operation(summary = "Crear pedidos")//comentario en el endpoint para saber que hace
    public ResponseEntity<PedidoResponse> crear(@Valid @RequestBody PedidoRequest request){
        PedidoResponse resultado= pedidoService.crear(request);

        //Que es URI
        URI ubicacion= ServletUriComponentsBuilder
                .fromCurrentRequest()//toma la URL de inicio (de partida)
                .path("/{id}")//agrega "/{id}" a la ruta
                .buildAndExpand(resultado.id())// remplaza "/{id}" al id real
                .toUri();//Construye un objeto URI (que es un objeto URI)

        return  ResponseEntity.created(ubicacion).body(resultado);
    }


    @GetMapping
    @Operation(summary = "Listar Pedidos")//comentario en el endpoint para saber que hace
    public ResponseEntity<List<PedidoResponse>> listar(){
        return ResponseEntity.ok(pedidoService.listar());
    }

    @GetMapping("/{id}")//le agrega esto a la ruta final y el PathVariable agarra el id ingresado y lo cambia al id de la url
    @Operation(summary = "Buscar pedidos por su id")//comentario en el endpoint para saber que hace
    public ResponseEntity<PedidoResponse> buscarPorId(@PathVariable @Positive Long id){
        return pedidoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(()->ResponseEntity.notFound().build());
    }

}
