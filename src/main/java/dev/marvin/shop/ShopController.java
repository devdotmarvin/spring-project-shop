package dev.marvin.shop;

import dev.marvin.shop.cart.Cart;
import dev.marvin.shop.category.CategoryService;
import dev.marvin.shop.product.Product;
import dev.marvin.shop.product.ProductService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@AllArgsConstructor
public class ShopController {

    private final CategoryService categoryService;
    private final ProductService productService;

    @GetMapping(path = {"/", "/shop"})
    public String shoppingPage(Model model) {
        model.addAttribute("categories", categoryService.getCategories());
        model.addAttribute("products", productService.getProducts());
        model.addAttribute("cartCount", Cart.cartItems.size());
        return "index";
    }

    @GetMapping("/manage")
    public String managePage() {
        return "manage";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/shop/category/{categoryId}")
    public String shopByCategory(@PathVariable("categoryId") Long id, Model model) {
        model.addAttribute("categories", categoryService.getCategories());
        model.addAttribute("products", productService.getProductsByCategory(id));
        return "index";
    }

    @GetMapping("/shop/products/search")
    public String searchProduct(@RequestParam("name") String productName, Model model) {
        model.addAttribute("categories", categoryService.getCategories());
        model.addAttribute("products", productService.getProductByName(productName));
        return "index";
    }

    @GetMapping("/shop/view/product/{id}")
    public String viewProductPage(@PathVariable("id") Long id, Model model) {
        Product product = productService.getProductById(id).orElseThrow(() -> new RuntimeException("product not found"));
        model.addAttribute("product", product);
        return "viewProduct";
    }
}