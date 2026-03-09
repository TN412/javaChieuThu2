package com.example.buoi5.service;

import com.example.buoi5.model.Product;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<Product> findAll();

    Optional<Product> findById(Long id);

    void deleteById(Long id);

    List<Product> searchByName(String keyword);

    // Xử lý tạo mới sản phẩm (gắn category + lưu ảnh)
    Product createProduct(Product product, Long categoryId, MultipartFile imageFile) throws IOException;

    // Xử lý cập nhật sản phẩm (gắn category + lưu ảnh nếu có)
    Product updateProduct(Long id, Product product, Long categoryId, MultipartFile imageFile) throws IOException;
}
