package cp.campus.todolist.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "detalles_pedido")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DetallePedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "producto_id", nullable = false)
    private Productos productos;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "pedido_id", nullable = false)
    private Pedido pedido;

    @Column(nullable = false)
    private Integer cantidad;

    @Column(nullable = false,precision = 19, scale = 2)
    private BigDecimal precioUnitario;

     DetallePedido(Productos productos, Pedido pedido, Integer cantidad) {
         if(pedido==null || productos == null){
             throw new IllegalArgumentException("El pedido y producto son obligatorios");
         }

         if(cantidad<=0){
             throw new IllegalArgumentException("La cantidad debe ser mayor a cero");
         }
         this.pedido=pedido;
         this.productos=productos;
         this.cantidad=cantidad;
         this.precioUnitario= productos.getPrecio();
    }

    public BigDecimal calcularImporte(){
         return precioUnitario.multiply(BigDecimal.valueOf(cantidad));
    }
}