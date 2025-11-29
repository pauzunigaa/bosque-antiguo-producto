package Producto.Producto.service;

import Producto.Producto.model.Producto;
import Producto.Producto.repository.ProductoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ProductoService {

    private final ProductoRepository repo;

    public ProductoService(ProductoRepository repo) {
        this.repo = repo;
    }

    public List<Producto> listar() {
        return repo.findAll();
    }
    public List<Producto> listarDisponibles() {
        return repo.findByDisponibleTrue();
    }
    public Producto obtener(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Producto no encontrado"
                ));
    }

    public Producto crear(Producto p) {
        return repo.save(p);
    }

    public Producto actualizar(Long id, Producto prodActualizado) {
        Producto p = obtener(id);

        p.setNombre(prodActualizado.getNombre());
        p.setDescripcion(prodActualizado.getDescripcion());
        p.setPrecio(prodActualizado.getPrecio());
        p.setImagenUrl(prodActualizado.getImagenUrl());
        p.setCategoria(prodActualizado.getCategoria());
        p.setDisponible(prodActualizado.getDisponible());
        p.setStock(prodActualizado.getStock());
        p.setStockCritico(prodActualizado.getStockCritico());
        return repo.save(p);
    }
    public Producto desactivar(Long id) {
        Producto p = obtener(id);
        p.setDisponible(false);
        return repo.save(p);
    }
    
    public Producto actualizarDisponibilidad(Long id, boolean disponible) {
        Producto p = obtener(id);
        p.setDisponible(disponible);
        return repo.save(p);
    }
    
    public Producto reducirStock(Long id, Integer cantidad) {
        System.out.println("=== REDUCIR STOCK LLAMADO ===");
        System.out.println("Producto ID: " + id + ", Cantidad a reducir: " + cantidad);
        
        Producto p = obtener(id);
        System.out.println("Producto encontrado: " + p.getNombre() + ", Stock actual: " + p.getStock());
        
        if (p.getStock() < cantidad) {
            String errorMsg = "Stock insuficiente para '" + p.getNombre() + "'. Disponible: " + p.getStock() + ", Solicitado: " + cantidad;
            System.err.println("ERROR: " + errorMsg);
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, 
                errorMsg
            );
        }
        
        int stockAnterior = p.getStock();
        p.setStock(p.getStock() - cantidad);
        
        // Si el stock queda en 0, marcamos como no disponible
        if (p.getStock() <= 0) {
            p.setDisponible(false);
        }
        
        System.out.println("Stock actualizado: " + stockAnterior + " -> " + p.getStock());
        Producto productoGuardado = repo.save(p);
        System.out.println("Producto guardado exitosamente");
        return productoGuardado;
    }
    
    public void eliminar(Long id) {
        repo.deleteById(id);
    }
}
