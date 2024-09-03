package co.istad.advanced_jpa.assembler;

import co.istad.advanced_jpa.controller.ProductController;
import co.istad.advanced_jpa.dto.response.ProductDtoRS;
import co.istad.advanced_jpa.entity.Product;
import co.istad.advanced_jpa.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.stereotype.Component;


/**
 * @author Sattya
 * create at 8/28/2024 1:39 PM
 */
@Component
public class ProductModelAssembler extends RepresentationModelAssemblerSupport<Product, EntityModel<ProductDtoRS>> {

    private ProductMapper productMapper;

    @Autowired
    public void setProductMapper(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    @SuppressWarnings("unchecked")
    public ProductModelAssembler() {
        super(ProductController.class,(Class<EntityModel<ProductDtoRS>>) (Class<?>) EntityModel.class);
    }

    @Override
    public EntityModel<ProductDtoRS> toModel(Product entity) {
        ProductDtoRS productDtoRS = productMapper.toDto(entity);
        Link selflink = linkTo(methodOn(ProductController.class).findProductByUuid(entity.getUuid())).withSelfRel();
        Link collectionLink = linkTo(
                methodOn(ProductController.class).findProducts()
        ).withRel(IanaLinkRelations.COLLECTION);
        return EntityModel.of(productDtoRS,selflink,collectionLink);
    }

    @Override
    public CollectionModel<EntityModel<ProductDtoRS>> toCollectionModel(Iterable<? extends Product> entities) {
        return super.toCollectionModel(entities);
    }
}
