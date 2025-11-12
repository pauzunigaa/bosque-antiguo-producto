package Producto.Producto.repository;

import Producto.Producto.model.Inventario;
import org.springframework.data.jpa.repository.JpaRepository;
public interface InventarioRepository extends JpaRepository<Inventario,Long> {

}