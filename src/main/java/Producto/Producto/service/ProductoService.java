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

    public Producto obtener(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Producto no encontrado"
                ));
    }

    public Producto crear(Producto p) {
        return repo.save(p);
    }

    public Producto actualizar(Long id, Producto in) {
        Producto p = obtener(id);

        p.setNombre(in.getNombre());
        p.setDescripcion(in.getDescripcion());
        p.setPrecio(in.getPrecio());
        p.setImagenUrl(in.getImagenUrl());
        p.setCategoria(in.getCategoria());

        return repo.save(p);
    }

    public void eliminar(Long id) {
        repo.deleteById(id);
    }
}
