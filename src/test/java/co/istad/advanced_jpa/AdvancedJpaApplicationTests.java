package co.istad.advanced_jpa;

import co.istad.advanced_jpa.dto.request.ProductDto;
import co.istad.advanced_jpa.entity.Category;
import co.istad.advanced_jpa.entity.Product;
import co.istad.advanced_jpa.mapper.ProductMapper;
import co.istad.advanced_jpa.repository.CategoryRepository;
import co.istad.advanced_jpa.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class AdvancedJpaApplicationTests {
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private ProductMapper productMapper;
	@Autowired
	private WebApplicationContext webApplicationContext;

    @BeforeEach
	void setUp(){
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}
	// Test cases for Category Repository
	@Test
	void whenFindCategoryByName_thenReturnCategoryList() {
		// Arrange
		String categoryName = "SmartPhone";

		// Act
		List<Category> categories = categoryRepository.findByName(categoryName);

		// Assert
		assertFalse(categories.isEmpty());
        categories.forEach(category -> System.out.println(category.getName()));
	}
	@Test
	void whenFindCategoryByIdOrName_thenReturnCategoryList(){
		// Arrange
		Long categoryId = 3L;
		String categoryName = "SmartPhone";

		// Act
		List<Category> categories = categoryRepository.findByIdOrName(categoryId,categoryName);

		// Assert
		assertFalse(categories.isEmpty(), "Category list should not be empty");
		categories.forEach(category -> System.out.println(category.getName()));
	}
	@Test
	void whenFindCategoryByNameContains_thenReturnCategoryList() {
		// Arrange
		String categoryPart = "Nic";

		// Act
		List<Category> categories = categoryRepository.findByNameContainsIgnoreCase(categoryPart);

		// Assert
		assertFalse(categories.isEmpty(), "Category list should not be empty");
		categories.forEach(category -> System.out.println(category.getName()));
	}
	@Test
	void whenFindAllProducts_thenReturnProductList(){
		// Act
		List<Product> products = productRepository.report();

		// Assert
		assertFalse(products.isEmpty(),"Product list should not be empty");
		products.forEach(product -> System.out.println(product.getName()));
	}
	@Test
	void whenUpdateProductName_thenProductShouldBeUpdated(){
		// Arrange
		Long productId = 2L;
		String updatedName = "Updated MacBook Pro M1";

		// Act
		productRepository.updateNameById(updatedName,productId);

		// Assert
		Product updatedProduct = productRepository.findById(productId).orElseThrow();
		assertEquals(updatedName,updatedProduct.getName(),"Product name should be updated");
	}
	@Test
	void createProduct(){
		// Arrange : Prepare the neccessary data for the test
		Long categoryId = 3L;
		ProductDto productDto = ProductDto.builder()
				.name("vietnam-coffee")
				.description("your daily beat <3")
				.price(BigDecimal.valueOf(2.19))
				.categoryId(categoryId)
				.build();
		System.out.println(productDto);
		// Act : Convert Dto to Entity and save it
		Product product = productMapper.fromDto(productDto);
		product.setUuid(UUID.randomUUID().toString());
		Product savedProduct = productRepository.save(product); // Save the product to the database
		System.out.println(savedProduct.getUuid());

		// Assert : Verify that the product was saved as expected
		assertNotNull(savedProduct.getId(),"Product ID should not be null after saving.");
		assertEquals(productDto.name(),savedProduct.getName(),"Product name should be match the DTO name.");
		assertEquals(productDto.description(), savedProduct.getDescription(),"Product description should match the DTO description");
		assertEquals(productDto.price(),savedProduct.getPrice(),"Product price should match the DTO price");

		// Optionally check if the UUID was assigned and saved correctly
		assertNotNull(savedProduct.getUuid(), "Product UUID should not be null");
	}
}
