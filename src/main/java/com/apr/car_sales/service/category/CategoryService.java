package com.apr.car_sales.service.category;

import com.apr.car_sales.dtos.category.CategoryDto;

import java.util.List;

public interface CategoryService {
    CategoryDto createCategory(CategoryDto categoryDto);
    CategoryModel readCategory(long categoryId);
    List<CategoryModel> readAllCategories();
    CategoryDto updateCategory(CategoryDto categoryDto, long categoryId);
    void deleteCategory(long categoryId);
}
