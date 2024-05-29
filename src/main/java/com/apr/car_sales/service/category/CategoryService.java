package com.apr.car_sales.service.category;

import com.apr.car_sales.dtos.category.CategoryDto;

import java.util.List;

public interface CategoryService {
    CategoryDto createCategory(CategoryDto categoryDto);
    CategoryModel readCategory(int categoryId);
    List<CategoryModel> readAllCategories();
    CategoryDto updateCategory(CategoryDto categoryDto, int categoryId);
    void deleteCategory(int categoryId);
}
