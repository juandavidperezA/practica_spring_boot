package cp.campus.todolist.service;

import cp.campus.todolist.dto.DetallesPedidoResponse;
import cp.campus.todolist.dto.DetallespedidoRequest;
import cp.campus.todolist.dto.PedidoRequest;
import cp.campus.todolist.dto.PedidoResponse;
import cp.campus.todolist.entity.DetallePedido;
import cp.campus.todolist.entity.Pedido;
import cp.campus.todolist.entity.Productos;
import cp.campus.todolist.repository.PedidoRepository;
import cp.campus.todolist.repository.ProductosRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Validated
@Transactional(readOnly = true)
public class PedidoService {
    private static final BigDecimal TASA_DESCUENTO_VIP = new BigDecimal("0.10");

    private final PedidoRepository pedidoRepository;
    private final ProductosRepository productosRepository;


    public PedidoService(PedidoRepository pedidoRepository, ProductosRepository productosRepository) {
        this.pedidoRepository = pedidoRepository;
        this.productosRepository = productosRepository;
    }

    @Transactional
    public PedidoResponse crear(@NotNull @Valid PedidoRequest request) {
        Pedido pedido = new Pedido(request.cliente().strip());

        Set<Long> productosIncluidos = new HashSet<>();

        for (DetallespedidoRequest detalle : request.detalles()) {
            if (!productosIncluidos.add(detalle.productoId())) {
                throw new IllegalArgumentException("El producto esta repetido" + detalle.productoId());
            }

            Productos productos = productosRepository.findById(detalle.productoId())
                    .orElseThrow(() -> new IllegalArgumentException("No existe el producto con el ID=" + detalle.productoId()));

            pedido.agregarDetalle(productos, detalle.cantidad());
        }
            BigDecimal tasa= request.clienteVip() ? TASA_DESCUENTO_VIP : BigDecimal.ZERO;

            pedido.calcularTotales(tasa);

            Pedido guardar= pedidoRepository.save(pedido);

            return toResponse(guardar);

    }
    private PedidoResponse toResponse(Pedido pedido){
        List<DetallesPedidoResponse> detalles= pedido.getDetalles().stream().map(this::toDetalleResponse).toList();

        return new PedidoResponse(pedido.getId(),pedido.getCliente(),pedido.getSubtotal(),pedido.getDescuento(),pedido.getTotal(),detalles);
    }

    private DetallesPedidoResponse toDetalleResponse(DetallePedido detallePedido){
        return new DetallesPedidoResponse(detallePedido.getId(),detallePedido.getProductos().getId()
                ,detallePedido.getProductos().getNombre()
                ,detallePedido.getCantidad(),detallePedido.getPrecioUnitario(),detallePedido.calcularImporte());
    }
}