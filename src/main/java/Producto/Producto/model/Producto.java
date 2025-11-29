package Producto.Producto.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "producto")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, length=150)
    private String nombre;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @Column(nullable=false, precision=10, scale=2)
    private BigDecimal precio;

    @Column(length=255)
    private String imagenUrl;
    @Column(nullable = false,columnDefinition = "TINYINT(1)")
    private boolean disponible;

    @Column(nullable = false)
    private Integer stock;

    @Column(nullable = false)
    private Integer stockCritico;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

}
