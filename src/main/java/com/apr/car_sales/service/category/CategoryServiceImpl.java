package com.apr.car_sales.service.category;

import com.apr.car_sales.dtos.category.CategoryDto;
import com.apr.car_sales.exception.ResourceNotFoundException;
import com.apr.car_sales.persistence.category.CategoryEntity;
import com.apr.car_sales.persistence.category.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        CategoryEntity entity = modelMapper.map(categoryDto, CategoryEntity.class);
        CategoryEntity created = categoryRepository.save(entity);
        return modelMapper.map(created, CategoryDto.class);
    }

    @Override
    public CategoryModel readCategory(int categoryId) {
        CategoryEntity category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "category id", categoryId));
        return modelMapper.map(category, CategoryModel.class);
    }

    @Override
    public List<CategoryModel> readAllCategories() {
        List<CategoryEntity> categories = categoryRepository.findAll();
        return categories.stream()
                .map(cat -> modelMapper.map(cat, CategoryModel.class))
                .toList();
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, int categoryId) {
        CategoryEntity category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "category id", categoryId));

        category.setType(categoryDto.getType());
        category.setDescription(categoryDto.getDescription());

        categoryRepository.save(category);

        return modelMapper.map(category, CategoryDto.class);
    }

    @Override
    public void deleteCategory(int categoryId) {
//        categoryRepository.deleteById(categoryId);

        CategoryEntity category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "category id", categoryId));
        categoryRepository.delete(category);


    }
}
