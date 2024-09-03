package co.istad.advanced_jpa.entity;

import co.istad.advanced_jpa.base.Auditable;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Sattya
 * create at 8/27/2024 2:30 AM
 */
@Entity
@Table(name = "prices")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Price extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal priceIn;
    private BigDecimal priceOut;
    @ManyToMany(mappedBy = "prices")
    private List<Product> products;
}
