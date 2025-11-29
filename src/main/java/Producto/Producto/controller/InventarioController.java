package Producto.Producto.controller;

import Producto.Producto.model.Inventario;
import Producto.Producto.repository.InventarioRepository;
import Producto.Producto.repository.ProductoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/v1/stock")
public class InventarioController {

    private final InventarioRepository inventarioRepo;
    private final ProductoRepository productoRepo;

    public InventarioController(InventarioRepository inventarioRepo,
                                ProductoRepository productoRepo) {
        this.inventarioRepo = inventarioRepo;
        this.productoRepo = productoRepo;
    }

    @GetMapping
    public List<Inventario> all() {
        return inventarioRepo.findAll();
    }

    // PUT /api/v1/stock/{productoId}?cantidad=10
    @PutMapping("/{productoId}")
    public Inventario update(@PathVariable Long productoId,
                             @RequestParam Integer cantidad) {

        var producto = productoRepo.findById(productoId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Producto no encontrado"
                ));

        var inventario = inventarioRepo.findByProductoId(productoId)
                .orElseGet(() -> {
                    Inventario inv = new Inventario();
                    inv.setProducto(producto);
                    inv.setCantidad(0);
                    return inv;
                });

        inventario.setCantidad(cantidad);
        return inventarioRepo.save(inventario);
    }
}
