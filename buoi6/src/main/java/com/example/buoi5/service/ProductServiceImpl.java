package com.example.buoi5.service;

import com.example.buoi5.model.Category;
import com.example.buoi5.model.Product;
import com.example.buoi5.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryService categoryService;

    private static final String UPLOAD_DIR = "static/uploads/";

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public List<Product> searchByName(String keyword) {
        return productRepository.searchByName(keyword);
    }

    @Override
    public Product createProduct(Product product, Long categoryId, MultipartFile imageFile) throws IOException {
        // Gắn category
        Category category = categoryService.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found: " + categoryId));
        product.setCategory(category);

        // Lưu ảnh nếu có
        if (imageFile != null && !imageFile.isEmpty()) {
            product.setImage(saveImage(imageFile));
        }

        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Long id, Product updated, Long categoryId, MultipartFile imageFile)
            throws IOException {
        // Tìm sản phẩm hiện tại
        Product existing = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found: " + id));

        // Cập nhật các trường
        existing.setName(updated.getName());
        existing.setPrice(updated.getPrice());

        // Gắn category mới
        Category category = categoryService.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found: " + categoryId));
        existing.setCategory(category);

        // Cập nhật ảnh nếu người dùng upload ảnh mới
        if (imageFile != null && !imageFile.isEmpty()) {
            existing.setImage(saveImage(imageFile));
        }

        return productRepository.save(existing);
    }

    // Helper: lưu file ảnh ra disk, trả về đường dẫn tương đối
    private String saveImage(MultipartFile imageFile) throws IOException {
        String originalFilename = imageFile.getOriginalFilename() != null
                ? imageFile.getOriginalFilename()
                : "image";
        String filename = System.currentTimeMillis() + "_" + StringUtils.cleanPath(originalFilename);
        Path uploadPath = Paths.get(UPLOAD_DIR);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        Files.copy(imageFile.getInputStream(), uploadPath.resolve(filename), StandardCopyOption.REPLACE_EXISTING);
        return "/uploads/" + filename;
    }
}
