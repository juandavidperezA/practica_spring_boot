package cp.campus.todolist.service;

import cp.campus.todolist.dto.CategoriaRequest;
import cp.campus.todolist.dto.CategoriaResponse;
import cp.campus.todolist.entity.Categoria;
import cp.campus.todolist.repository.CategoriaRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@Service
@Validated//hace validaciones por medio del proxy de Spring --> verifica que se cumpla los parametros de los dto de los atributos
@Transactional(readOnly = true)//permite establecer una operacion de escritura por eso el readOnly=true
public class CategoriaService {
    //si la entidad tiene mas de un constructor se usa el @AutoWired mientras se hacer la inyeccion de dependencias
    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }//esto es inyeccion de dependecias con el private y el constructor

    @Transactional//es como un rollback que si algo falla devuelve todo y no se ejecuta solo se usa para mandar datos a la base de datos
    public CategoriaResponse crear(@NotNull @Valid CategoriaRequest Request){//el tipo de dato retornado debe ser igual al declarado en este caso el categoriaResponse
        Categoria categoria=new Categoria(Request.nombre().strip());

        Categoria guardado= categoriaRepository.save(categoria);

        return toResponse(guardado);
    }

    public List<CategoriaResponse> listar(){
        return categoriaRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public Optional<CategoriaResponse> buscarPorId(@NotNull @Positive Long id){//Optional da la opcion de que si el elemento recibido existe o no existe
        return categoriaRepository.findById(id).map(this::toResponse);
    }

    private CategoriaResponse toResponse(Categoria categoria){
        return new CategoriaResponse(categoria.getId(),categoria.getNombre());
    }
}