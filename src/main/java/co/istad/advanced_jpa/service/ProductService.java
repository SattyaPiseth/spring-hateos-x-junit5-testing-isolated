package co.istad.advanced_jpa.service;

import co.istad.advanced_jpa.dto.request.ProductDto;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;

/**
 * @author Sattya
 * create at 8/27/2024 4:27 PM
 */
public interface ProductService {
    CollectionModel<?> findProducts();

    EntityModel<?> findProductByUuid(String uuid);
    EntityModel<?> createProduct(ProductDto productDto);
    void updateProduct(String uuid, ProductDto productDto);
    void deleteProduct(String uuid);
}
