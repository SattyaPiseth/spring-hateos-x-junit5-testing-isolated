package co.istad.advanced_jpa.entity;

import co.istad.advanced_jpa.base.Auditable;
import jakarta.persistence.*;
import lombok.*;

/**
 * @author Sattya
 * create at 8/27/2024 3:02 PM
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "order_details")
public class OrderDetails extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String uuid;

    private Integer quantity;

    private String remark;
    @ManyToOne
    private Product product;

    @ManyToOne
    private Order order;

}
