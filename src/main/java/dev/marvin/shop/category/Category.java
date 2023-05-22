package dev.marvin.shop.category;

import dev.marvin.shop.product.Product;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categories")
@Data
@NoArgsConstructor
public class Category {
    @Id
    @SequenceGenerator(name = "category_id_sequence", sequenceName = "category_id_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "category_id_sequence")
    @Column(updatable = false)
    private Long id;

    @NotNull
    @Size(min = 3, message = "Name must be at least 3 characters long")
    private String name;

    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @OneToMany
    private List<Product> products = new ArrayList<>();

}
