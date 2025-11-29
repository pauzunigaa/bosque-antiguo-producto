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
        return repo.save(p);
    }
    public Producto desactivar(Long id) {
        Producto p = obtener(id);
        p.setDisponible(false);
        return repo.save(p);
    }
    
    public Producto reducirStock(Long id, Integer cantidad) {
        Producto p = obtener(id);
        
        if (p.getStock() < cantidad) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, 
                "Stock insuficiente. Disponible: " + p.getStock() + ", Solicitado: " + cantidad
            );
        }
        
        p.setStock(p.getStock() - cantidad);
        
        // Si el stock queda en 0, marcamos como no disponible
        if (p.getStock() <= 0) {
            p.setDisponible(false);
        }
        
        return repo.save(p);
    }
    
    public void eliminar(Long id) {
        repo.deleteById(id);
    }
}
