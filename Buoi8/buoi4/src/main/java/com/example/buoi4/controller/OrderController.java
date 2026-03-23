package com.example.buoi4.controller;

import com.example.buoi4.model.CartItem;
import com.example.buoi4.model.Order;
import com.example.buoi4.service.OrderService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // Hiển thị trang checkout
    @GetMapping("/checkout")
    public String showCheckoutForm(HttpSession session, Model model) {
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");

        if (cart == null || cart.isEmpty()) {
            return "redirect:/cart";
        }

        long totalAmount = 0;
        for (CartItem item : cart) {
            totalAmount += item.getTotalPrice();
        }

        model.addAttribute("cartItems", cart);
        model.addAttribute("totalAmount", totalAmount);
        return "order/checkout";
    }

    // Xử lý đặt hàng
    @PostMapping("/place")
    public String placeOrder(@RequestParam String customerName,
                            @RequestParam String shippingAddress,
                            HttpSession session,
                            Model model) {

        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");

        if (cart == null || cart.isEmpty()) {
            return "redirect:/cart";
        }

        // Tạo order
        Order order = orderService.createOrder(cart, customerName, shippingAddress);

        // Xóa giỏ hàng sau khi đặt hàng thành công
        session.removeAttribute("cart");

        // Chuyển đến trang xác nhận
        model.addAttribute("order", order);
        return "order/success";
    }

    // Hiển thị chi tiết đơn hàng
    @GetMapping("/{id}")
    public String viewOrder(@PathVariable Long id, Model model) {
        Order order = orderService.getOrderById(id);
        model.addAttribute("order", order);
        return "order/detail";
    }

    // Danh sách đơn hàng
    @GetMapping
    public String listOrders(Model model) {
        List<Order> orders = orderService.getAllOrders();
        model.addAttribute("orders", orders);
        return "order/list";
    }
}
