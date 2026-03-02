package com.example.buoi4.config;

import com.example.buoi4.model.Category;
import com.example.buoi4.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CategoryConverter implements Converter<String, Category> {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Category convert(String id) {
        if (id == null || id.isEmpty()) return null;
        return categoryRepository.findById(Integer.parseInt(id)).orElse(null);
    }
}
