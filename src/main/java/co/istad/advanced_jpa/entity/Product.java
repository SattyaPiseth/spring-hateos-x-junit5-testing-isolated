package co.istad.advanced_jpa.entity;

import co.istad.advanced_jpa.base.Auditable;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Sattya
 * create at 8/27/2024 1:11 AM
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "products")
public class Product extends Auditable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true,nullable = false)
    private String uuid;
    @Column(name = "pro_name",nullable = false)
    private String name;

    @Column(columnDefinition = "text")
    private String description;

    private BigDecimal price;
    @ManyToOne
    @JoinColumn(name = "cate_id")
    private Category category;
    @ManyToMany
    @JoinTable(name = "product_prices",joinColumns = @JoinColumn(name = "product_id",referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name = "price_id",referencedColumnName = "id"))
    private List<Price> prices;

    @OneToMany(mappedBy = "product")
    private List<OrderDetails> orderDetails;
}
