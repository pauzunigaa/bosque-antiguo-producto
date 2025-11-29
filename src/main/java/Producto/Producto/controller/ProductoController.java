package Producto.Producto.controller;

import Producto.Producto.model.Producto;
import Producto.Producto.service.ProductoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173") // front Vite
@RestController
@RequestMapping("/api/v1/products")
@Tag(name = "Productos", description = "Gestión de productos y catálogo")
public class ProductoController {

    private final ProductoService service;

    public ProductoController(ProductoService service) {
        this.service = service;
    }

    @Operation(summary = "Listar productos", description = "Obtiene todos los productos o solo los disponibles")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de productos obtenida exitosamente",
            content = @Content(schema = @Schema(implementation = Producto.class)))
    })
    @GetMapping
    public List<Producto> listar(
            @RequestParam(required = false) Boolean disponibles
    ) {
        if (Boolean.TRUE.equals(disponibles)) {
            return service.listarDisponibles();
        }
        return service.listar(); // todos
    }

    @Operation(summary = "Obtener producto por ID", description = "Obtiene un producto específico por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Producto encontrado",
            content = @Content(schema = @Schema(implementation = Producto.class))),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    @GetMapping("/{id}")
    public Producto byId(@PathVariable Long id) {
        return service.obtener(id);
    }

    @Operation(summary = "Crear producto", description = "Crea un nuevo producto en el catálogo")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Producto creado exitosamente",
            content = @Content(schema = @Schema(implementation = Producto.class)))
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Producto create(@RequestBody Producto p) {
        return service.crear(p);
    }

    @Operation(summary = "Actualizar producto", description = "Actualiza un producto existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Producto actualizado exitosamente",
            content = @Content(schema = @Schema(implementation = Producto.class))),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    @PutMapping("/{id}")
    public Producto update(@PathVariable Long id, @RequestBody Producto p) {
        return service.actualizar(id, p);
    }
    @Operation(summary = "Desactivar producto", description = "Desactiva un producto (disponible=false)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Producto desactivado exitosamente",
            content = @Content(schema = @Schema(implementation = Producto.class))),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    @DeleteMapping("/{id}")
    public Producto desactivar(@PathVariable Long id) {
        return service.desactivar(id); // ← esto pone disponible=false
    }
    
    @Operation(summary = "Reducir stock", description = "Reduce el stock de un producto en la cantidad especificada")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Stock reducido exitosamente",
            content = @Content(schema = @Schema(implementation = Producto.class))),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado"),
        @ApiResponse(responseCode = "400", description = "Stock insuficiente")
    })
    @PutMapping("/{id}/reducir-stock")
    public Producto reducirStock(@PathVariable Long id, @RequestParam Integer cantidad) {
        return service.reducirStock(id, cantidad);
    }

}
