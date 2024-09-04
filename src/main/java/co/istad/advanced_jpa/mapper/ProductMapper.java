package co.istad.advanced_jpa.mapper;

import co.istad.advanced_jpa.dto.request.ProductDto;
import co.istad.advanced_jpa.dto.response.ProductDtoRS;
import co.istad.advanced_jpa.entity.Product;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductDtoRS toDto(Product product);
    @Mappings(
            @Mapping(source = "categoryId" ,target = "category.id")
    )
    Product fromDto(ProductDto productDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void partialUpdate(ProductDto productDto, @MappingTarget Product product);

}