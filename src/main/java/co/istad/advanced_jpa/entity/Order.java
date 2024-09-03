package co.istad.advanced_jpa.entity;

import co.istad.advanced_jpa.base.Auditable;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Sattya
 * create at 8/27/2024 2:57 PM
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "orders")
public class Order extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String uuid;
    private LocalDateTime orderedDate;
    @OneToMany(mappedBy = "order")
    private List<OrderDetails> orderDetails;
}
