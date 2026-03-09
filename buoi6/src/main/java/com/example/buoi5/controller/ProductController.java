package com.example.buoi5.controller;

import com.example.buoi5.model.Product;
import com.example.buoi5.service.CategoryService;
import com.example.buoi5.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public String list(@RequestParam(required = false) String keyword, Model model) {
        List<Product> products = (keyword != null && !keyword.isBlank())
                ? productService.searchByName(keyword)
                : productService.findAll();
        model.addAttribute("products", products);
        model.addAttribute("keyword", keyword);
        return "products/list";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryService.findAll());
        return "products/create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute Product product,
            @RequestParam("categoryId") Long categoryId,
            @RequestParam("imageFile") MultipartFile imageFile) throws IOException {
        productService.createProduct(product, categoryId, imageFile);
        return "redirect:/products";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        Product product = productService.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found: " + id));
        model.addAttribute("product", product);
        model.addAttribute("categories", categoryService.findAll());
        return "products/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable Long id,
            @ModelAttribute Product product,
            @RequestParam("categoryId") Long categoryId,
            @RequestParam("imageFile") MultipartFile imageFile) throws IOException {
        productService.updateProduct(id, product, categoryId, imageFile);
        return "redirect:/products";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        productService.deleteById(id);
        return "redirect:/products";
    }
}
