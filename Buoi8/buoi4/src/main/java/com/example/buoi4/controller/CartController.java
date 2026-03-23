package com.example.buoi4.controller;

import com.example.buoi4.model.CartItem;
import com.example.buoi4.model.Product;
import com.example.buoi4.service.ProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private ProductService productService;

    // Lấy giỏ hàng từ session
    private List<CartItem> getCartFromSession(HttpSession session) {
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart == null) {
            cart = new ArrayList<>();
            session.setAttribute("cart", cart);
        }
        return cart;
    }

    // Thêm sản phẩm vào giỏ hàng
    @PostMapping("/add")
    public String addToCart(@RequestParam Long productId,
                           @RequestParam(defaultValue = "1") Integer quantity,
                           HttpSession session) {
        Product product = productService.getProductById(productId);
        if (product != null) {
            List<CartItem> cart = getCartFromSession(session);

            // Kiểm tra xem sản phẩm đã có trong giỏ chưa
            boolean found = false;
            for (CartItem item : cart) {
                if (item.getProductId().equals(productId)) {
                    item.setQuantity(item.getQuantity() + quantity);
                    found = true;
                    break;
                }
            }

            // Nếu chưa có thì thêm mới
            if (!found) {
                CartItem newItem = new CartItem();
                newItem.setProductId(product.getId());
                newItem.setProductName(product.getName());
                newItem.setPrice(product.getPrice());
                newItem.setImage(product.getImage());
                newItem.setQuantity(quantity);
                cart.add(newItem);
            }

            session.setAttribute("cart", cart);
        }
        return "redirect:/products";
    }

    // Hiển thị giỏ hàng
    @GetMapping
    public String viewCart(HttpSession session, Model model) {
        List<CartItem> cart = getCartFromSession(session);

        long totalAmount = 0;
        for (CartItem item : cart) {
            totalAmount += item.getTotalPrice();
        }

        model.addAttribute("cartItems", cart);
        model.addAttribute("totalAmount", totalAmount);
        return "cart/cart";
    }

    // Cập nhật số lượng
    @PostMapping("/update")
    public String updateCart(@RequestParam Long productId,
                            @RequestParam Integer quantity,
                            HttpSession session) {
        List<CartItem> cart = getCartFromSession(session);

        for (CartItem item : cart) {
            if (item.getProductId().equals(productId)) {
                if (quantity > 0) {
                    item.setQuantity(quantity);
                } else {
                    cart.remove(item);
                }
                break;
            }
        }

        session.setAttribute("cart", cart);
        return "redirect:/cart";
    }

    // Xóa sản phẩm khỏi giỏ hàng
    @PostMapping("/remove")
    public String removeFromCart(@RequestParam Long productId, HttpSession session) {
        List<CartItem> cart = getCartFromSession(session);
        cart.removeIf(item -> item.getProductId().equals(productId));
        session.setAttribute("cart", cart);
        return "redirect:/cart";
    }

    // Xóa toàn bộ giỏ hàng
    @PostMapping("/clear")
    public String clearCart(HttpSession session) {
        session.removeAttribute("cart");
        return "redirect:/cart";
    }
}
