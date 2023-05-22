package dev.marvin.shop.product;

import dev.marvin.shop.category.Category;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
public class Product {
    @Id
    @SequenceGenerator(name = "product_id_sequence", sequenceName = "product_id_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_id_sequence")
    @Column(updatable = false)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Double price;

    private String productStatus;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;


    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Category category;


    public Product(String name, String description, Double price, String productStatus, Category category) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.productStatus = productStatus;
        this.category = category;
    }
}
