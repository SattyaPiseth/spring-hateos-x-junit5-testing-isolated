package co.istad.advanced_jpa.entity;

import co.istad.advanced_jpa.base.Auditable;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

/**
 * @author Sattya
 * create at 8/27/2024 2:20 AM
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "categories")
public class Category extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "category")
    private List<Product> products;
}
