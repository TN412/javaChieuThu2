package com.example.buoi4;

import com.example.buoi4.model.Category;
import com.example.buoi4.model.Product;
import com.example.buoi4.repository.CategoryRepository;
import com.example.buoi4.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

@SpringBootApplication
public class Buoi4Application {

	public static void main(String[] args) {
		SpringApplication.run(Buoi4Application.class, args);
	}

	@Bean
	CommandLineRunner seedCoffeeCategories(CategoryRepository categoryRepository) {
		return args -> {
			List<String> coffeeCategories = Arrays.asList(
					"Cà phê hạt",
					"Cà phê rang xay",
					"Cà phê pha máy",
					"Cà phê pha phin",
					"Cold Brew",
					"Combo cà phê"
			);

			Set<String> existingNames = new HashSet<>();
			for (Category category : categoryRepository.findAll()) {
				existingNames.add(category.getName().trim().toLowerCase(Locale.ROOT));
			}

			for (String categoryName : coffeeCategories) {
				String key = categoryName.trim().toLowerCase(Locale.ROOT);
				if (!existingNames.contains(key)) {
					Category category = new Category();
					category.setName(categoryName);
					categoryRepository.save(category);
					existingNames.add(key);
				}
			}
		};
	}

	@Bean
	CommandLineRunner seedCoffeeProducts(CategoryRepository categoryRepository,
									ProductRepository productRepository) {
		return args -> {
			Set<String> existingProductNames = new HashSet<>();
			for (Product product : productRepository.findAll()) {
				existingProductNames.add(product.getName().trim().toLowerCase(Locale.ROOT));
			}

			Map<String, List<ProductSeed>> productsByCategory = new LinkedHashMap<>();
			productsByCategory.put("Cà phê hạt", Arrays.asList(
					new ProductSeed("Arabica Cầu Đất 500g", 235000),
					new ProductSeed("Robusta Buôn Ma Thuột 500g", 185000)
			));
			productsByCategory.put("Cà phê rang xay", Arrays.asList(
					new ProductSeed("Blend House Rang Vừa 250g", 139000),
					new ProductSeed("Blend Signature Rang Đậm 500g", 249000)
			));
			productsByCategory.put("Cà phê pha máy", Arrays.asList(
					new ProductSeed("Espresso Blend Classic 1kg", 419000),
					new ProductSeed("Espresso Blend Premium 1kg", 499000)
			));
			productsByCategory.put("Cà phê pha phin", Arrays.asList(
					new ProductSeed("Phin Truyền Thống 500g", 169000),
					new ProductSeed("Phin Mộc Đậm Vị 500g", 179000)
			));
			productsByCategory.put("Cold Brew", Arrays.asList(
					new ProductSeed("Cold Brew Túi Lọc 10 Gói", 129000),
					new ProductSeed("Cold Brew Fruit Notes 250g", 159000)
			));
			productsByCategory.put("Combo cà phê", Arrays.asList(
					new ProductSeed("Combo Khởi Đầu 3 Vị", 329000),
					new ProductSeed("Combo Quà Tặng Cà Phê", 459000)
			));

			List<Product> productsToSave = new ArrayList<>();
			for (Map.Entry<String, List<ProductSeed>> entry : productsByCategory.entrySet()) {
				String categoryName = entry.getKey();
				Category category = categoryRepository.findAll().stream()
						.filter(c -> c.getName().equalsIgnoreCase(categoryName))
						.findFirst()
						.orElse(null);

				if (category == null) {
					continue;
				}

				for (ProductSeed seed : entry.getValue()) {
					String key = seed.name.trim().toLowerCase(Locale.ROOT);
					if (!existingProductNames.contains(key)) {
						Product product = new Product();
						product.setName(seed.name);
						product.setPrice(seed.price);
						product.setCategory(category);
						product.setImage(null);
						productsToSave.add(product);
						existingProductNames.add(key);
					}
				}
			}

			if (!productsToSave.isEmpty()) {
				productRepository.saveAll(productsToSave);
			}
		};
	}

	private record ProductSeed(String name, long price) {
	}

}
