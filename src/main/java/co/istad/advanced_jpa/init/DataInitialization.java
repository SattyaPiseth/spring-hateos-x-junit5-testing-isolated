package co.istad.advanced_jpa.init;

import co.istad.advanced_jpa.entity.Category;
import co.istad.advanced_jpa.entity.Price;
import co.istad.advanced_jpa.entity.Product;
import co.istad.advanced_jpa.repository.CategoryRepository;
import co.istad.advanced_jpa.repository.PriceRepository;
import co.istad.advanced_jpa.repository.ProductRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;


/**
 * @author Sattya
 * create at 8/27/2024 1:47 AM
 */
@Component
@RequiredArgsConstructor
public class DataInitialization {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final PriceRepository priceRepository;

    @PostConstruct
    void init(){
        System.out.println("Start initializing data ...");
        Category electronic = Category.builder().name("Electronic").build();
        Category smartphone = Category.builder().name("SmartPhone").build();
        Category sonic = Category.builder().name("Sonic").build();
        categoryRepository.saveAll(List.of(electronic,smartphone,sonic));

        Price defaultPrice = Price.builder()
                .priceIn(BigDecimal.valueOf(1000))
                .priceOut(BigDecimal.valueOf(1500))
                .build();

        Price specialPrice = Price.builder()
                .priceIn(BigDecimal.valueOf(1000))
                .priceOut(BigDecimal.valueOf(800))
                .build();
        priceRepository.saveAll(List.of(defaultPrice,specialPrice));

        Product product1 = Product.builder()
                .uuid(UUID.randomUUID().toString())
                .name("iPhone 13 Pro Max")
                .description("Apple Inc.")
                .category(smartphone)
                .prices(List.of(defaultPrice,specialPrice))
                .build();

        Product product2 = Product.builder()
                .uuid(UUID.randomUUID().toString())
                .name("macBook Pro M1")
                .description("Apple Inc.")
                .category(electronic)
                .prices(List.of(defaultPrice,specialPrice))
                .build();

        Product product3 = Product.builder()
                .uuid(UUID.randomUUID().toString())
                .name("Samsung Galaxy S21 Ultra")
                .description("Samsung Electronics")
                .category(smartphone)
                .prices(List.of(defaultPrice,specialPrice))
                .build();

        productRepository.saveAll(List.of(product1, product2, product3));

//        System.out.println(product1.getId());
//        System.out.println(product2.getId());
//        System.out.println(product3.getId());

        List<Product> products = productRepository.findAll();
        products.forEach(p-> System.out.println(p.getName()));
     }
}
