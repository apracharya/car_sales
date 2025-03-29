package com.apr.car_sales.endpoint;

import com.apr.car_sales.dtos.category.CategoryDto;
import com.apr.car_sales.response.ApiResponse;
import com.apr.car_sales.service.category.CategoryModel;
import com.apr.car_sales.service.category.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/categories")
public class CategoryEndpoint {
    private final CategoryService categoryService;

    public CategoryEndpoint(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/create")
    public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto categoryDto) {
        CategoryDto category = categoryService.createCategory(categoryDto);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    @GetMapping("/read")
    public ResponseEntity<List<CategoryModel>> readAllCategories() {
        List<CategoryModel> categories = categoryService.readAllCategories();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<CategoryModel> readCategory(@PathVariable("id") long categoryId) {
        CategoryModel category = categoryService.readCategory(categoryId);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }



    @PutMapping("/update/{id}")
    public ResponseEntity<CategoryDto> updateCategory(@RequestBody CategoryDto categoryDto,
                                                      @PathVariable("id") long categoryId) {
        CategoryDto category = categoryService.updateCategory(categoryDto, categoryId);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ApiResponse deleteCategory(@PathVariable("id") long categoryId) {
        categoryService.deleteCategory(categoryId);
        return new ApiResponse("Category deleted!", true);
    }

}
