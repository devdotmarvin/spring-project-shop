package dev.marvin.shop.cart;

import dev.marvin.shop.appuser.User;
import dev.marvin.shop.placedOrder.Order;
import dev.marvin.shop.placedOrder.OrderService;
import dev.marvin.shop.product.Product;
import dev.marvin.shop.product.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@AllArgsConstructor
public class CartController {

    private final ProductService productService;
    private final OrderService orderService;

    @GetMapping("/cart")
    public String cartPage(Model model) {
        model.addAttribute("cartCount", (long) Cart.cartItems.size());
        model.addAttribute("total", Cart.cartItems.stream().mapToDouble(Product::getPrice).sum());
        model.addAttribute("cart", Cart.cartItems);
        return "cart";
    }

    @GetMapping("/cart/{productId}")
    public String addToCart(@PathVariable("productId") Long id) {
        Product product = productService.getProductById(id).orElseThrow(() -> new RuntimeException("product not found"));
        Cart.cartItems.add(product);
        return "redirect:/shop";
    }


    @GetMapping("/cart/removeItem/{index}")
    public String remoteCartItem(@PathVariable("index") int index) {
        Cart.cartItems.remove(index);
        return "redirect:/cart";
    }

    @GetMapping("/checkout")
    public String checkout(Model model, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        model.addAttribute("user", user);
        model.addAttribute("order", new Order());
        model.addAttribute("cartCount", (long) Cart.cartItems.size());
        model.addAttribute("total", Cart.cartItems.stream().mapToDouble(Product::getPrice).sum());
        model.addAttribute("cart", Cart.cartItems);
        return "checkout";
    }

    @GetMapping("/placeOrder")
    public String placeOrderPage() {
        return "placedOrder";
    }

    @PostMapping("/placeOrder")
    public String placeOrder(@ModelAttribute("order") Order placedOrder) {
        orderService.saveOrder(placedOrder);
        return "redirect:/placeOrder";
    }
}
