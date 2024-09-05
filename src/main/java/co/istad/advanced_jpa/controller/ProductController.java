package co.istad.advanced_jpa.controller;

import co.istad.advanced_jpa.dto.request.ProductDto;
import co.istad.advanced_jpa.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * @author Sattya
 * create at 8/27/2024 3:45 PM
 */
@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@CrossOrigin
public class ProductController {
    private final ProductService productService;
    @GetMapping
    public CollectionModel<?> findProducts(){
        return productService.findProducts();
    }
    @GetMapping("/{uuid}")
    public EntityModel<?> findProductByUuid(@PathVariable String uuid){
        return productService.findProductByUuid(uuid);
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EntityModel<?> createProduct(@RequestBody @Valid ProductDto productDto){
        return productService.createProduct(productDto);
    }
    @PatchMapping("/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    public void updateProduct(@PathVariable String uuid, @RequestBody @Valid ProductDto productDto){
        productService.updateProduct(uuid, productDto);
    }
    @DeleteMapping("/{uuid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable String uuid){
        productService.deleteProduct(uuid);
    }
}
