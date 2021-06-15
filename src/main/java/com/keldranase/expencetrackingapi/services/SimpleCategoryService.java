package com.keldranase.expencetrackingapi.services;

import com.keldranase.expencetrackingapi.entities.Category;
import com.keldranase.expencetrackingapi.exceptions.EtBadRequestException;
import com.keldranase.expencetrackingapi.exceptions.EtResourceNotFoundException;
import com.keldranase.expencetrackingapi.repositories.ICategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Additional layer of abstraction, for better extensibility
 */
@Service
@Transactional
public class SimpleCategoryService implements ICategoryService {

    @Autowired
    ICategoryRepository categoryRepository;

    @Override
    public List<Category> fetchAllCategories(Integer userId) {

        return categoryRepository.findAll(userId);
    }

    @Override
    public Category fetchCategoryById(Integer userId, Integer categoryId) throws EtResourceNotFoundException {

        return categoryRepository.findById(userId, categoryId);
    }

    @Override
    public Category addCategory(Integer userId, String title, String description) throws EtBadRequestException {

        if (categoryRepository.isPresent(userId, title)) {
            throw new EtBadRequestException("There is already a category with same title for this user");
        }
        int categoryId = categoryRepository.create(userId, title, description);
        return categoryRepository.findById(userId, categoryId);
    }

    @Override
    public void updateCategory(Integer userId, Integer categoryId, Category category) throws EtBadRequestException {

        categoryRepository.update(userId, categoryId, category);
    }

    @Override
    public void removeCategoryWithAllTransactions(Integer userId, Integer categoryId)
            throws EtResourceNotFoundException {

        this.fetchCategoryById(userId, categoryId);
        categoryRepository.removeById(userId, categoryId);
    }
}
