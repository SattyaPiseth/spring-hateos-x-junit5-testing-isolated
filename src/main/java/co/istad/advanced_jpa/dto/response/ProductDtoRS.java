package co.istad.advanced_jpa.dto.response;


import lombok.Builder;
import org.springframework.hateoas.server.core.Relation;

import java.io.Serializable;

/**
 * DTO for {@link co.istad.advanced_jpa.entity.Product}
 */
@Builder
@Relation(collectionRelation = "products",itemRelation = "product")
public record ProductDtoRS(String uuid, String name, String description) implements Serializable {
}