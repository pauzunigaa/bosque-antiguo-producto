package Producto.Producto.repository;

import Producto.Producto.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

    // para búsqueda por nombre (opcional pero útil)
    List<Producto> findByNombreContainingIgnoreCase(String nombre);
    List<Producto> findByDisponibleTrue();
}
