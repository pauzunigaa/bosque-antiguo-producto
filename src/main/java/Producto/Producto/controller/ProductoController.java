package Producto.Producto.controller;

import Producto.Producto.model.Producto;
import Producto.Producto.service.ProductoService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173") // front Vite
@RestController
@RequestMapping("/api/v1/products")
public class ProductoController {

    private final ProductoService service;

    public ProductoController(ProductoService service) {
        this.service = service;
    }

    @GetMapping
    public List<Producto> listar(
            @RequestParam(required = false) Boolean disponibles
    ) {
        if (Boolean.TRUE.equals(disponibles)) {
            return service.listarDisponibles();
        }
        return service.listar(); // todos
    }

    @GetMapping("/{id}")
    public Producto byId(@PathVariable Long id) {
        return service.obtener(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Producto create(@RequestBody Producto p) {
        return service.crear(p);
    }

    @PutMapping("/{id}")
    public Producto update(@PathVariable Long id, @RequestBody Producto p) {
        return service.actualizar(id, p);
    }
    @DeleteMapping("/{id}")
    public Producto desactivar(@PathVariable Long id) {
        return service.desactivar(id); // ← esto pone disponible=false
    }
    
    @PutMapping("/{id}/reducir-stock")
    public Producto reducirStock(@PathVariable Long id, @RequestParam Integer cantidad) {
        return service.reducirStock(id, cantidad);
    }

}
