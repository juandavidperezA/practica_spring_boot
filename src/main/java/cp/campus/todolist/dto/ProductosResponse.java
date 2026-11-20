package cp.campus.todolist.dto;

import cp.campus.todolist.entity.Categoria;

import java.math.BigDecimal;

public record ProductosResponse(
        Long id,
        String nombre,
        BigDecimal precio,
        Long categoriaId,
        String nombreCategoria
) {
}
