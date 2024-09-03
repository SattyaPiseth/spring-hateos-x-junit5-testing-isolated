package co.istad.advanced_jpa.repository;

import co.istad.advanced_jpa.entity.Price;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Sattya
 * create at 8/27/2024 3:35 PM
 */
@Repository
public interface PriceRepository extends JpaRepository<Price,Long> {
}
