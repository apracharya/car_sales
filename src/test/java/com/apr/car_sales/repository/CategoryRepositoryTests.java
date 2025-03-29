package com.apr.car_sales.repository;

import com.apr.car_sales.persistence.category.CategoryEntity;
import com.apr.car_sales.persistence.category.CategoryRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class CategoryRepositoryTests {

    @Autowired
    private CategoryRepository categoryRepository;

    private CategoryEntity createCategory() {
        return CategoryEntity.builder()
                .type("Sedan")
                .description("Something about sedan")
                .build();
    }

    @Test
    void CategoryRepository_Save_ReturnNotNull() {
        CategoryEntity category = createCategory();
        CategoryEntity saved = categoryRepository.save(category);

        Assertions.assertThat(saved).isNotNull();

    }

    @Test
    void CategoryRepository_FindById_ReturnCategory() {
        CategoryEntity category = createCategory();
        CategoryEntity saved = categoryRepository.save(category);

        CategoryEntity found = categoryRepository.findById(saved.getId()).get();

        Assertions.assertThat(found).isNotNull();
        Assertions.assertThat(found.getId()).isPositive();
        Assertions.assertThat(found.getId()).isEqualTo(saved.getId());
    }

    @Test
    void CategoryRepository_FindAll_ReturnMoreThanOne() {
        CategoryEntity category1 = createCategory();
        CategoryEntity category2 = createCategory();

        categoryRepository.save(category1);
        categoryRepository.save(category2);

        List<CategoryEntity> all = categoryRepository.findAll();

        Assertions.assertThat(all).isNotNull().isNotEmpty().hasSize(2);

    }

    @Test
    void CategoryRepository_UpdateCategory_ReturnCategoryNotNull() {
        CategoryEntity category = createCategory();
        CategoryEntity saved = categoryRepository.save(category);

        CategoryEntity found = categoryRepository.findById(saved.getId()).get();
        found.setType("Hatchback");
        found.setDescription("Something about hatchbacks");
        CategoryEntity updated = categoryRepository.save(found);

        Assertions.assertThat(updated).isNotNull();
        Assertions.assertThat(updated.getType()).isEqualTo("Hatchback");
        Assertions.assertThat(updated.getDescription()).isEqualTo("Something about hatchbacks");
    }

    @Test
    void CategoryRepository_DeleteCategoryById_ReturnCategoryIsEmpty() {
        CategoryEntity category = createCategory();
        CategoryEntity saved = categoryRepository.save(category);

        categoryRepository.deleteById(saved.getId());
        Optional<CategoryEntity> deletedCategory = categoryRepository.findById(saved.getId());
        Assertions.assertThat(deletedCategory).isEmpty();
    }

    @Test
    void CategoryRepository_DeleteCategory_ReturnCategoryIsEmpty() {
        CategoryEntity category = createCategory();
        CategoryEntity saved = categoryRepository.save(category);

        CategoryEntity found = categoryRepository.findById(saved.getId()).get();

        categoryRepository.delete(found);

        Optional<CategoryEntity> deletedCategory = categoryRepository.findById(saved.getId());
        Assertions.assertThat(deletedCategory).isEmpty();
    }
}
