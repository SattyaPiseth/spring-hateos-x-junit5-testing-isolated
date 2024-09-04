package co.istad.advanced_jpa.service.impl;

import co.istad.advanced_jpa.assembler.ProductModelAssembler;
import co.istad.advanced_jpa.constant.MessageConstant;
import co.istad.advanced_jpa.controller.ProductController;
import co.istad.advanced_jpa.dto.request.ProductDto;
import co.istad.advanced_jpa.dto.response.ProductDtoRS;
import co.istad.advanced_jpa.entity.Product;
import co.istad.advanced_jpa.mapper.ProductMapper;
import co.istad.advanced_jpa.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {
    @Mock
    private ProductRepository productRepository;
    @Mock
    private ProductMapper productMapper;
    @Mock
    private ProductModelAssembler productModelAssembler;
    @InjectMocks
    private ProductServiceImpl productServiceImpl;


    @Test
    void shouldSuccessFindProducts() {
        // Given or Arrange
        Product product1 = Product.builder()
                .uuid(UUID.randomUUID().toString())
                .name("product1")
                .build();
        Product product2 = Product.builder()
                .uuid(UUID.randomUUID().toString())
                .name("product2")
                .build();

        List<Product> productList = Arrays.asList(product1,product2);
        // Create DTOs for comparison purposes
        ProductDtoRS dtoRS1 = ProductDtoRS.builder()
                .uuid(product1.getUuid())
                .name(product1.getName())
                .build();
        ProductDtoRS dtoRS2 = ProductDtoRS.builder()
                .uuid(product2.getUuid())
                .name(product2.getName())
                .build();

        // Mocks the calls
        when(productRepository.findAll()).thenReturn(productList);
        when(productModelAssembler.toCollectionModel(productList)).thenReturn(
                CollectionModel.of(
                        Arrays.asList(
                                EntityModel.of(dtoRS1),
                                EntityModel.of(dtoRS2)
                        )
                )
        );

        // When or Action is called
        CollectionModel<EntityModel<ProductDtoRS>> result = (CollectionModel<EntityModel<ProductDtoRS>>) productServiceImpl.findProducts();

        // Then or Assert
        assertNotNull(result,"result should not be null");
        assertEquals(2,result.getContent().size(),"Result size should match number of products");

        // Verify interactions with mocks
        verify(productRepository, times(1)).findAll();
        verify(productModelAssembler, times(1)).toCollectionModel(productList);
        verifyNoMoreInteractions(productRepository);
        verifyNoMoreInteractions(productModelAssembler);

        // Validate that the returned collection has the correct contents
        List<ProductDtoRS> dtos = result.getContent().stream()
               .map(EntityModel::getContent).collect(Collectors.toList());
        assertEquals(Arrays.asList(dtoRS1,dtoRS2), dtos,"DTOs should match the products");

    }

    @Test
    void shouldSuccessFindProductByUuid() {
        // Given or Arrange
        String uuid = UUID.randomUUID().toString();
        Product product = Product.builder().uuid(uuid).build();
        ProductDtoRS productDtoRS = ProductDtoRS.builder()
                .uuid(uuid)
                .build();

        EntityModel<ProductDtoRS> entityModel = EntityModel.of(productDtoRS);
        entityModel.add(linkTo(methodOn(ProductController.class).findProductByUuid(uuid)).withSelfRel());
        entityModel.add(linkTo(methodOn(ProductController.class).findProducts()).withRel(IanaLinkRelations.COLLECTION));

        // Mocks the calls
        when(productRepository.findByUuid(uuid)).thenReturn(Optional.of(product));
        when(productModelAssembler.toModel(product)).thenReturn(entityModel);

        // When or Action is called
        EntityModel<?> result = productServiceImpl.findProductByUuid(uuid);

        // Then or Assert
        assertNotNull(result, "Result should not be null");
        assertEquals(entityModel, result, "The result should match the expected entity model");

        // Verify interactions with mocks
        verify(productRepository, times(1)).findByUuid(uuid);
        verify(productModelAssembler, times(1)).toModel(product);
        verifyNoMoreInteractions(productRepository);
        verifyNoMoreInteractions(productModelAssembler);
    }

    @Test
    void shouldFailedFindProductByUuid_ProductNotFound() {
        String uuid = UUID.randomUUID().toString();
        when(productRepository.findByUuid(uuid)).thenReturn(Optional.empty());

        // When or Action is called
        assertThrows(ResponseStatusException.class, () -> productServiceImpl.findProductByUuid(uuid));

        // Verify interactions with mocks
        verify(productRepository, times(1)).findByUuid(uuid);
        verifyNoMoreInteractions(productRepository);
        verifyNoMoreInteractions(productModelAssembler);
    }

    @Test
    void shouldSuccessCreateProduct() {
        // Given or Arrange
        ProductDto productDto = ProductDto.builder()
                .name("test")
                .description("test")
                .price(BigDecimal.valueOf(5))
                .build();

        Product product = Product.builder()
                .uuid(UUID.randomUUID().toString())
                .name(productDto.name())
                .description(productDto.description())
                .price(productDto.price())
                .build();

        ProductDtoRS productDtoRS = ProductDtoRS.builder()
                .uuid(product.getUuid())
                .name(product.getName())
                .description(product.getDescription())
                .build();
        EntityModel<ProductDtoRS> entityModel = EntityModel.of(productDtoRS);
        entityModel.add(linkTo(methodOn(ProductController.class).findProductByUuid(product.getUuid())).withSelfRel());
        entityModel.add(linkTo(methodOn(ProductController.class).findProducts()).withRel(IanaLinkRelations.COLLECTION));

        // Mocks the calls
        when(productRepository.existsByName(productDto.name())).thenReturn(false);
        when(productMapper.fromDto(productDto)).thenReturn(product);  // Add this line
        when(productRepository.save(product)).thenReturn(product);
        when(productModelAssembler.toModel(product)).thenReturn(entityModel);

        // When or Action is called
        EntityModel<?> result = productServiceImpl.createProduct(productDto);

        // Then or Assert
        assertNotNull(result, "Result should not be null");
        assertEquals(entityModel, result, "The result should match the expected entity model");

        // Verify interactions with mocks
        verify(productRepository, times(1)).existsByName(productDto.name());
        verify(productMapper, times(1)).fromDto(productDto);  // Add this line
        verify(productRepository, times(1)).save(product);
        verify(productModelAssembler, times(1)).toModel(product);
        verifyNoMoreInteractions(productRepository);
        verifyNoMoreInteractions(productModelAssembler);
        verifyNoMoreInteractions(productMapper);
    }

    @Test
    void shouldFailedToCreateProduct_ProductAlreadyExists() {
        // Given
        ProductDto productDto = ProductDto.builder().name("Existing Product").build();

        // Mock the repository behavior
        when(productRepository.existsByName(productDto.name())).thenReturn(true);

        // When and Then
        ResponseStatusException thrown = assertThrows(ResponseStatusException.class, () -> {
            productServiceImpl.createProduct(productDto);
        });

        assertEquals(HttpStatus.CONFLICT, thrown.getStatusCode());
        assertEquals(String.format(MessageConstant.Product.ALREADY_EXISTS, productDto.name()), thrown.getReason());
    }

    @Test
    void shouldSuccessUpdateProduct() {
        // Given or Arrange
        String uuid = UUID.randomUUID().toString();
        ProductDto productDto = ProductDto.builder().name("updated product").build();
        Product product = Product.builder().uuid(uuid).name(productDto.name()).build();

        // Mocks the calls behaviours
        when(productRepository.findByUuid(uuid)).thenReturn(Optional.ofNullable(product));
        when(productRepository.existsByName(productDto.name())).thenReturn(false);

        // When or Action is called
        productServiceImpl.updateProduct(uuid, productDto);

        // Then or Assert
        verify(productMapper).partialUpdate(productDto, product);
        assert product != null;
        verify(productRepository).save(product);


    }

    @Test
    void shouldThrowNotFoundExceptionWhenProductDoesNotExist() {
        // Arrange
        String uuid = UUID.randomUUID().toString();
        ProductDto productDto = ProductDto.builder().name("update product").build();

        // Mocks the calls
        when(productRepository.findByUuid(uuid)).thenReturn(Optional.empty());

        // Act & Assert
        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> productServiceImpl.updateProduct(uuid, productDto));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());

        // Verify interactions with mocks
        verify(productRepository, times(1)).findByUuid(uuid);
        verifyNoMoreInteractions(productMapper);
        verifyNoMoreInteractions(productModelAssembler);
    }

    @Test
    void shouldThrowConflictExceptionWhenProductNameAlreadyExists() {
        // Arrange
        String uuid = UUID.randomUUID().toString();
        ProductDto productDto = ProductDto.builder().name("existing product").build();
        Product product = Product.builder().uuid(uuid).name(productDto.name()).build();

        // Mocks the calls
        when(productRepository.findByUuid(uuid)).thenReturn(Optional.of(product));
        when(productRepository.existsByName(productDto.name())).thenReturn(true);

        // Act & Assert
        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> productServiceImpl.updateProduct(uuid, productDto));
        assertEquals(HttpStatus.CONFLICT, exception.getStatusCode());

        // Verify interactions with mocks
        verify(productRepository, times(1)).findByUuid(uuid);
        verify(productRepository, times(1)).existsByName(productDto.name());
        verify(productMapper, never()).partialUpdate(any(), any());
        verify(productRepository, never()).save(any());
        verify(productModelAssembler, never()).toModel(any());
        verifyNoMoreInteractions(productRepository);
        verifyNoMoreInteractions(productMapper);
        verifyNoMoreInteractions(productModelAssembler);
    }
}