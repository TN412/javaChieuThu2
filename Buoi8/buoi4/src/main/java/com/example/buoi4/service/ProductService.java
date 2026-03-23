package com.example.buoi4.service;

import com.example.buoi4.model.Product;
import com.example.buoi4.model.Category;
import com.example.buoi4.repository.CategoryRepository;
import com.example.buoi4.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // Lấy sản phẩm với phân trang, tìm kiếm, lọc, sắp xếp
    public Page<Product> getProducts(String keyword, Integer categoryId, String sortBy, int page, int size) {
        Sort sort = Sort.unsorted();

        // Xử lý sắp xếp
        if ("price_asc".equals(sortBy)) {
            sort = Sort.by("price").ascending();
        } else if ("price_desc".equals(sortBy)) {
            sort = Sort.by("price").descending();
        }

        Pageable pageable = PageRequest.of(page, size, sort);

        // Tìm kiếm và lọc
        if (keyword != null && !keyword.isEmpty() && categoryId != null) {
            Category category = categoryRepository.findById(categoryId).orElse(null);
            if (category != null) {
                return productRepository.findByNameContainingIgnoreCaseAndCategory(keyword, category, pageable);
            }
        } else if (keyword != null && !keyword.isEmpty()) {
            return productRepository.findByNameContainingIgnoreCase(keyword, pageable);
        } else if (categoryId != null) {
            Category category = categoryRepository.findById(categoryId).orElse(null);
            if (category != null) {
                return productRepository.findByCategory(category, pageable);
            }
        }

        return productRepository.findAll(pageable);
    }

    public void saveProduct(Product product) {
        productRepository.save(product);
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}