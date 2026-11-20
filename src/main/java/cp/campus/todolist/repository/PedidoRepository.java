package cp.campus.todolist.repository;

import cp.campus.todolist.entity.DetallePedido;
import cp.campus.todolist.entity.Pedido;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long>{
    @Override
    @EntityGraph(attributePaths = {"detalles","detalles.Productos"})
    Optional<Pedido> findById(Long id);

    @Override
    @EntityGraph(attributePaths = {"detalles","detalles.Productos"})
    List<Pedido> findAll();
}

