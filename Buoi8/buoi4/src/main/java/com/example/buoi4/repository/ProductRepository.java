package com.example.buoi4.repository;

import com.example.buoi4.model.Product;
import com.example.buoi4.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // Tìm kiếm theo tên sản phẩm (keyword)
    Page<Product> findByNameContainingIgnoreCase(String keyword, Pageable pageable);

    // Lọc theo category
    Page<Product> findByCategory(Category category, Pageable pageable);

    // Tìm kiếm và lọc theo category
    Page<Product> findByNameContainingIgnoreCaseAndCategory(String keyword, Category category, Pageable pageable);

    // Tìm tất cả với phân trang
    Page<Product> findAll(Pageable pageable);
}