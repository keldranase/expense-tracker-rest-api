package com.keldranase.expencetrackingapi.unit;

import com.keldranase.expencetrackingapi.entities.Category;
import com.keldranase.expencetrackingapi.exceptions.EtBadRequestException;
import com.keldranase.expencetrackingapi.exceptions.EtResourceNotFoundException;
import com.keldranase.expencetrackingapi.repositories.ICategoryRepository;
import com.keldranase.expencetrackingapi.services.ICategoryService;
import com.keldranase.expencetrackingapi.services.SimpleCategoryService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class CategoryServiceTest extends Assertions {

    ICategoryRepository categoryRepository = new ICategoryRepository() {
        @Override
        public List<Category> findAll(Integer userId) throws EtResourceNotFoundException {

            return null;
        }

        @Override
        public Category findById(Integer userId, Integer categoryId) throws EtResourceNotFoundException {

            return new Category(1, 1, "valid", "desc", 0.0);
        }

        @Override
        public boolean isPresent(Integer userId, String categoryTitle) {

            return userId == 1 && categoryTitle == "present";
        }

        @Override
        public Integer create(Integer userId, String title, String description) throws EtBadRequestException {

            return 1;
        }

        @Override
        public void update(Integer userId, Integer categoryId, Category category) throws EtBadRequestException {

        }

        @Override
        public void removeById(Integer userId, Integer categoryId) {

        }
    };

    ICategoryService categoryService = new SimpleCategoryService(categoryRepository);

    @Test
    public void shouldNotAddIfPresent() {

        assertThrows(EtBadRequestException.class, () -> categoryService.addCategory(1, "present", "desc"));
    }

    @Test
    public void shouldNotAddIfInvalidName() {

        String longTitle = "tittle length more than 16 symbols";
        assertThrows(EtBadRequestException.class, () -> categoryService.addCategory(1, longTitle, "desc"));
        assertThrows(EtBadRequestException.class, () -> categoryService.addCategory(1, "", "desc"));
        assertThrows(EtBadRequestException.class, () -> categoryService.addCategory(1, null, "desc"));

    }

    @Test
    public void shouldNotAddIfInvalidDescription() {

        String longDesc = "description length more than 64 characters description length more than 64 characters";
        assertThrows(EtBadRequestException.class, () -> categoryService.addCategory(1, longDesc, "desc"));
        assertThrows(EtBadRequestException.class, () -> categoryService.addCategory(1, longDesc, "desc"));
    }

    @Test
    public void shouldAddIfNotPresentAndValidName() {

        // todo: I feel like it's bad test design. Redo.
        Category expected = new Category(1, 1, "valid", "desc", 0.0);
        Category real = null;
        try {
            real = categoryService.addCategory(1, "valid", "desc");

        } catch (Exception e) {
            // if control gets here - then there was an exception, and assertion should fail
        }
        assertNotNull(real);

        Assertions.assertEquals(expected.getUserId(), real.getUserId());
        Assertions.assertEquals(expected.getTitle(), real.getTitle());
        Assertions.assertEquals(expected.getDescription(), real.getDescription());
        Assertions.assertEquals(expected.getTotalExpense(), real.getTotalExpense());
    }
}
