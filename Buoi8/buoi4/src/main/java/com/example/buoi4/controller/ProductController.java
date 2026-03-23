package com.example.buoi4.controller;

import com.example.buoi4.model.Product;
import com.example.buoi4.service.CategoryService;
import com.example.buoi4.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public String listProducts(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Integer categoryId,
            @RequestParam(required = false) String sortBy,
            @RequestParam(defaultValue = "0") int page,
            Model model) {

        int pageSize = 5; // 5 sản phẩm mỗi trang
        Page<Product> productPage = productService.getProducts(keyword, categoryId, sortBy, page, pageSize);

        model.addAttribute("products", productPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", productPage.getTotalPages());
        model.addAttribute("totalItems", productPage.getTotalElements());
        model.addAttribute("keyword", keyword);
        model.addAttribute("categoryId", categoryId);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("categories", categoryService.getAllCategories());

        return "product/list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "product/add";
    }

    @PostMapping("/save")
    public String saveProduct(@ModelAttribute("product") Product product,
                              @RequestParam("imageFile") MultipartFile imageFile) throws IOException {
        if (!imageFile.isEmpty()) {
            String filename = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();
            Path uploadDir = Paths.get("static/images");
            Files.createDirectories(uploadDir);
            Files.copy(imageFile.getInputStream(), uploadDir.resolve(filename),
                    StandardCopyOption.REPLACE_EXISTING);
            product.setImage(filename);
        }
        productService.saveProduct(product);
        return "redirect:/products";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);
        model.addAttribute("categories", categoryService.getAllCategories());
        return "product/edit";
    }

    @PostMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") Long id) {
        productService.deleteProduct(id);
        return "redirect:/products";
    }
}
