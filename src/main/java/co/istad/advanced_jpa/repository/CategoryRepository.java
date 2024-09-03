package co.istad.advanced_jpa.repository;

import co.istad.advanced_jpa.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Sattya
 * create at 8/27/2024 3:29 PM
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByName(String name);
    List<Category> findByIdOrName(Long id,String name);

    List<Category> findByNameContainsIgnoreCase(String name);


}
