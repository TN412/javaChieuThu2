package com.example.buoi4.service;

import com.example.buoi4.model.CartItem;
import com.example.buoi4.model.Order;
import com.example.buoi4.model.OrderDetail;
import com.example.buoi4.model.Product;
import com.example.buoi4.repository.OrderRepository;
import com.example.buoi4.repository.OrderDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private ProductService productService;

    @Transactional
    public Order createOrder(List<CartItem> cartItems, String customerName, String shippingAddress) {
        // Tạo Order
        Order order = new Order();
        order.setCustomerName(customerName);
        order.setShippingAddress(shippingAddress);

        long totalAmount = 0;

        // Tạo OrderDetail cho mỗi item trong giỏ hàng
        for (CartItem item : cartItems) {
            Product product = productService.getProductById(item.getProductId());
            if (product != null) {
                OrderDetail detail = new OrderDetail();
                detail.setOrder(order);
                detail.setProduct(product);
                detail.setQuantity(item.getQuantity());
                detail.setPrice(product.getPrice());
                detail.setTotalPrice(product.getPrice() * item.getQuantity());

                order.getOrderDetails().add(detail);
                totalAmount += detail.getTotalPrice();
            }
        }

        order.setTotalAmount(totalAmount);

        // Lưu Order (cascade sẽ tự động lưu OrderDetail)
        return orderRepository.save(order);
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
}
