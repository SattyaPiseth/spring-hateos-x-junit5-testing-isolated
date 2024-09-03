package co.istad.advanced_jpa.mapper;

import co.istad.advanced_jpa.dto.request.ProductDto;
import co.istad.advanced_jpa.dto.response.ProductDtoRS;
import co.istad.advanced_jpa.entity.Product;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductMapper {


    ProductDtoRS toDto(Product product);
    @Mappings(
            @Mapping(source = "categoryId",target = "category.id")
    )
    Product fromDto(ProductDto productDto);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Product partialUpdate(ProductDto productDto, @MappingTarget Product product);

}