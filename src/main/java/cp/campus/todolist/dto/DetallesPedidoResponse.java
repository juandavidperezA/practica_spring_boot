package cp.campus.todolist.dto;

import java.math.BigDecimal;

public record DetallesPedidoResponse(
        Long id,
        Long productoId,
        String productonombre,
        Integer cantidad,
        BigDecimal precioUnitario,
        BigDecimal importe
) {
}
