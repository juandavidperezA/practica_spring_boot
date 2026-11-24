package cp.campus.todolist.service;

import cp.campus.todolist.dto.ProductosRequest;
import cp.campus.todolist.dto.ProductosResponse;
import cp.campus.todolist.entity.Categoria;
import cp.campus.todolist.entity.Productos;
import cp.campus.todolist.repository.CategoriaRepository;
import cp.campus.todolist.repository.ProductosRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

@Service
@Validated
@Transactional(readOnly = true)
public class ProductoService {

    private final ProductosRepository productosRepository;
    private final CategoriaRepository categoriaRepository;

    public ProductoService(ProductosRepository productosRepository, CategoriaRepository categoriaRepository) {
        this.productosRepository = productosRepository;
        this.categoriaRepository = categoriaRepository;
    }

    @Transactional
    public ProductosResponse crear(@NotNull @Valid ProductosRequest productRequest){
        Categoria categoria= categoriaRepository
                .findById(productRequest.categoriaId())
                .orElseThrow(()->new IllegalArgumentException("No existe la categoria con el ID= "
                        + productRequest.categoriaId()));

        BigDecimal precio= productRequest.precio().setScale(2, RoundingMode.UNNECESSARY);

        Productos productos= new Productos(productRequest.nombre().strip(),precio,categoria);

        Productos guardar= productosRepository.save(productos);

        return toResponse(guardar);
    }

    public List<ProductosResponse> listar(){
        return productosRepository.findAll().stream().map(this::toResponse).toList();

    }

    public Optional<ProductosResponse> buscarPorId(@NotNull @Positive Long id){
        return productosRepository.findById(id).map(this::toResponse);
    }

    public List<ProductosResponse> listarPorCategoria(@NotNull @Positive Long categoriaId){
        return productosRepository.findByCategoria_Id(categoriaId).stream().map(this::toResponse).toList();
    }

    private ProductosResponse toResponse(Productos productos){
        return new ProductosResponse(productos.getId(),productos.getNombre(),productos.getPrecio(),productos.getCategoria().getId(),productos.getCategoria().getNombre());
    }
}
