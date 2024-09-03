package co.istad.advanced_jpa.repository;

import co.istad.advanced_jpa.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


/**
 * @author Sattya
 * create at 8/27/2024 1:42 AM
 */
@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    @Query("select p from Product p where p.uuid = ?1")
    Optional<Product> findByUuid(String uuid);

    @Query("select p from Product p order by p.id DESC")
    List<Product> report();

    @Transactional
    @Modifying
    @Query("update Product p set p.name = ?1 where p.id = ?2")
    void updateNameById(String name, Long id);

    @Query("select (count(p) > 0) from Product p where p.name = ?1")
    boolean existsByName(String name);

    boolean existsByUuid(String uuid);

    void deleteByUuid(String uuid);
}
