package cp.campus.todolist.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration//la ejecuta para que trabaje desde el principio
@OpenAPIDefinition(
        info = @Info(
                title = "API DE PEDIDOS",
                version = "1.0.0",
                description = """
                        Gestion de categorias, productos y pedidos.
                        Los pedidos obtienen precios del catalogo y 
                        calculan sus importes finales.
                        """
        )
)//la informacion que quiero que salgo en swagger
public class OpenApiConfig {
}
