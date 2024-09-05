package co.istad.advanced_jpa.assembler;

import co.istad.advanced_jpa.controller.CategoryController;
import co.istad.advanced_jpa.dto.response.CategoryDtoRS;
import co.istad.advanced_jpa.entity.Category;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

/**
 * @author Sattya
 * create at 9/5/2024 2:45 AM
 */
@Component
public class CategoryModelAssembler extends RepresentationModelAssemblerSupport<Category, EntityModel<CategoryDtoRS>> {

    @SuppressWarnings("unchecked")
    public CategoryModelAssembler() {
        super(CategoryController.class,(Class<EntityModel<CategoryDtoRS>>) (Class<?>) EntityModel.class);
    }

    @Override
    public EntityModel<CategoryDtoRS> toModel(Category entity) {
        return null;
    }
}
