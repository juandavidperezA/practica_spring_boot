package cp.campus.todolist.repository;

import cp.campus.todolist.entity.Productos;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductosRepository extends JpaRepository<Productos, Long> {


    @Override
    @EntityGraph(attributePaths = "categoria")
    List<Productos> findAll();

    @Override
    @EntityGraph(attributePaths = "categoria")
    Optional<Productos> findById(Long id);

    @EntityGraph(attributePaths = "categoria")
    List<Productos> findByCategoria_Id(Long CategoriaId);

    @EntityGraph(attributePaths = "categoria")
    Optional<Productos> findByNombre(String nombre);
}
