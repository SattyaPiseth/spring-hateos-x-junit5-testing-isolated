package co.istad.advanced_jpa.service.impl;

import co.istad.advanced_jpa.assembler.ProductModelAssembler;
import co.istad.advanced_jpa.constant.MessageConstant;
import co.istad.advanced_jpa.dto.request.ProductDto;
import co.istad.advanced_jpa.entity.Product;
import co.istad.advanced_jpa.mapper.ProductMapper;
import co.istad.advanced_jpa.repository.ProductRepository;
import co.istad.advanced_jpa.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

/**
 * @author Sattya
 * create at 8/27/2024 4:28 PM
 */
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final ProductModelAssembler productModelAssembler;
    @Override
    public CollectionModel<?> findProducts() {
        List<Product> products = productRepository.findAll();
        return productModelAssembler.toCollectionModel(products);
    }

    @Override
    public EntityModel<?> findProductByUuid(String uuid) {
        Product product = productRepository.findByUuid(uuid).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,String.format(MessageConstant.Product.NOT_FOUND,uuid))
        );
        return productModelAssembler.toModel(product);
    }
    @Transactional
    @Override
    public EntityModel<?> createProduct(ProductDto productDto) {
        // Check name of product in the database
        if (productRepository.existsByName(productDto.name())){
            throw new ResponseStatusException(HttpStatus.CONFLICT, String.format(MessageConstant.Product.ALREADY_EXISTS,productDto.name()));
        }
        // let create the product
        Product product = productMapper.fromDto(productDto);
        product.setUuid(UUID.randomUUID().toString());
        productRepository.save(product);
        return productModelAssembler.toModel(product);
    }
    @Transactional
    @Override
    public void updateProduct(String uuid, ProductDto productDto) {
        // Fetch the product by UUID
        Product product = productRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format(MessageConstant.Product.NOT_FOUND, uuid)));

        // Check if the product with the new name already exists
        boolean isNameExists = productRepository.existsByName(productDto.name());
        if (isNameExists) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, String.format(MessageConstant.Product.ALREADY_EXISTS, productDto.name()));
        }

        // Update the product properties
        productMapper.partialUpdate(productDto, product);

        // Save the updated product
        productRepository.save(product);

    }
    @Transactional
    @Override
    public void deleteProduct(String uuid) {
        // Check if the product exists
        if (!productRepository.existsByUuid(uuid)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format(MessageConstant.Product.NOT_FOUND, uuid));
        }

        // Delete the product
        productRepository.deleteByUuid(uuid);
    }
}
