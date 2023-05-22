package dev.marvin.shop.product;

import dev.marvin.shop.category.Category;
import dev.marvin.shop.category.CategoryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@AllArgsConstructor
@Slf4j
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;


    @GetMapping("/products")
    public String manageProducts(Model model) {
        model.addAttribute("products", productService.getProducts());
        return "products";
    }

    @GetMapping("/products/create")
    public String createProductPage(Model model) {
        model.addAttribute("categories", categoryService.getCategories());
        model.addAttribute("productDTO", new ProductDTO());
        return "createProduct";
    }

    @PostMapping("/products/create")
    public String createProduct(@ModelAttribute("productDTO") ProductDTO productDTO) {
        Product product = new Product();
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        product.setProductStatus(productDTO.getProductStatus());
        product.setDescription(productDTO.getDescription());
        product.setCategory(categoryService.getCategoryById(productDTO.getCategoryId()));

        Category category = categoryService.getCategoryById(productDTO.getCategoryId());
        category.getProducts().add(product);

        productService.createProduct(product);
        log.info("product Created : {}", product);
        return "redirect:/products";
    }

    @RequestMapping(path = "/products/delete/{productId}", method = {RequestMethod.GET, RequestMethod.DELETE})
    public String deleteProductItem(@PathVariable("productId") Long id) {
        productService.removeProductById(id);
        return "redirect:/products";
    }

    @GetMapping("/products/update/{productId}")
    public String updateProductPage(@PathVariable("productId") Long id, Model model) {
        Optional<Product> product = productService.getProductById(id);
        product.ifPresent(value -> model.addAttribute("product", value));
        return "updateProduct";
    }

    @PostMapping("/products/update/{productId}")
    public String updateProduct(@ModelAttribute("product") Product productDTO, @PathVariable("productId") Long id) {
        Product product = productService.getProductById(id).orElseThrow(() -> new IllegalStateException("product not found"));

        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        product.setProductStatus(productDTO.getProductStatus());
        product.setDescription(productDTO.getDescription());

        productService.updateProduct(product);

        return "redirect:/products";
    }
}
