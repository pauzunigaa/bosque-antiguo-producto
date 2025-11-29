package Producto.Producto.controller;

import Producto.Producto.model.Categoria;
import Producto.Producto.repository.CategoriaRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/v1/categories")
@Tag(name = "Categorías", description = "Gestión de categorías de productos")
public class CategoriaController {

    private final CategoriaRepository repo;

    public CategoriaController(CategoriaRepository repo) {
        this.repo = repo;
    }

    @Operation(summary = "Listar categorías", description = "Obtiene todas las categorías disponibles")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de categorías obtenida exitosamente",
            content = @Content(schema = @Schema(implementation = Categoria.class)))
    })
    @GetMapping
    public List<Categoria> all() {
        return repo.findAll();
    }

    @Operation(summary = "Crear categoría", description = "Crea una nueva categoría de productos")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Categoría creada exitosamente",
            content = @Content(schema = @Schema(implementation = Categoria.class)))
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Categoria create(@RequestBody Categoria c) {
        return repo.save(c);
    }

    @Operation(summary = "Obtener categoría por ID", description = "Obtiene una categoría específica por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Categoría encontrada",
            content = @Content(schema = @Schema(implementation = Categoria.class))),
        @ApiResponse(responseCode = "404", description = "Categoría no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Categoria> getById(@PathVariable Long id) {
        return repo.findById(id)
            .map(categoria -> ResponseEntity.ok(categoria))
            .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Actualizar categoría", description = "Actualiza una categoría existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Categoría actualizada exitosamente",
            content = @Content(schema = @Schema(implementation = Categoria.class))),
        @ApiResponse(responseCode = "404", description = "Categoría no encontrada")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Categoria> update(@PathVariable Long id, @RequestBody Categoria categoriaActualizada) {
        return repo.findById(id)
            .map(categoria -> {
                categoria.setNombre(categoriaActualizada.getNombre());
                return ResponseEntity.ok(repo.save(categoria));
            })
            .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Eliminar categoría", description = "Elimina una categoría por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Categoría eliminada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Categoría no encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
