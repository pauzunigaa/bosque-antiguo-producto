package Producto.Producto.assemblers;

import org.springframework.stereotype.Component;

@Component
public class ProductoModelAssembler implements RepresentationModelAssembler<Producto, EntityModel<Producto>> {

    /**
     * Transforma un objeto Producto en un EntityModel que incluye enlaces HATEOAS.
     *
     * @param producto el objeto Producto que se va a transformar
     * @return un EntityModel con los datos del producto y enlaces útiles
     */
    @Override
    public EntityModel<Producto> toModel(Producto producto) {
        return EntityModel.of(producto,
                // Enlace al recurso actual (GET /productos/{id})
                linkTo(methodOn(ProductoController.class).obtenerProductoPorId(producto.getId())).withSelfRel(),

                // Enlace a la lista completa de productos (GET /productos)
                linkTo(methodOn(ProductoController.class).listarProductos()).withRel("productos"),

                // Enlace para eliminar este producto (DELETE /productos/{id})
                linkTo(methodOn(ProductoController.class).eliminarProducto(producto.getId())).withRel("eliminar"));


    }
}
