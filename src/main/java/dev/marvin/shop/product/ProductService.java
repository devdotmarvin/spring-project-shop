package dev.marvin.shop.product;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public Iterable<Product> getProducts() {
        return productRepository.findAll();
    }

    public void createProduct(Product product) {
        productRepository.save(product);
    }

    public void removeProductById(Long id) {
        productRepository.deleteById(id);
    }

    public void updateProduct(Product product) {
        productRepository.save(product);
    }

    public List<Product> getProductsByCategory(Long id) {
        return productRepository.getProductsByCategoryId(id);
    }

    public List<Product> getProductByName(String name) {
        return productRepository.getProductsByName(name).stream().filter(x -> x.getName().equalsIgnoreCase(name)).collect(Collectors.toList());
    }
}
