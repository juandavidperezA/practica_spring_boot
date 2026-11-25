package cp.campus.todolist.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(
        name = "usuarios",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_usuarios_email",
                columnNames = "email"
        )
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String nombre;

    @Column(nullable = false,length = 254)
    private String email;

    @Column(nullable = false,length = 254)
    private String passwordHash;//aumentar la seguridad de las credenciales

    @Column(nullable = false,length = 50)
    @Enumerated(EnumType.STRING)
    private Rol rol;

    @Column(nullable = false)
    private Boolean vip;

    public Usuario(String nombre, String email, String passwordHash) {
        this.nombre = nombre;
        this.email = email;
        this.passwordHash = passwordHash;
        this.rol=Rol.CLIENTE;
        this.vip=false;
    }
}
