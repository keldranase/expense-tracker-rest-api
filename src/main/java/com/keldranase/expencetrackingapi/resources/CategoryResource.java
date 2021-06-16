package com.keldranase.expencetrackingapi.resources;

import com.keldranase.expencetrackingapi.entities.Category;
import com.keldranase.expencetrackingapi.services.ICategoryService;
import com.keldranase.expencetrackingapi.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Provides endpoints for interactions with Categories data
 */
@RestController
@RequestMapping("/api/categories")
public class CategoryResource {

    ICategoryService categoryService;

    @Autowired
    public CategoryResource(ICategoryService categoryService) {
        this.categoryService = categoryService;
    }

    /**
     * Get all categories for given user
     * @param request JWT user token
     */
    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories(HttpServletRequest request) {

        int userId = (Integer) request.getAttribute("userId"); // todo: change hardcore to enum or something
        List<Category> categories = categoryService.fetchAllCategories(userId);

        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    /**
     * Get category for given user by global category id
     * @param request JWT user token
     * @param categoryId global id of category
     */
    @GetMapping("/{categoryId}")
    public ResponseEntity<Category> getCategoryById(HttpServletRequest request,
                                                    @PathVariable("categoryId") Integer categoryId) {

        int userId = (Integer) request.getAttribute("userId");
        Category category = categoryService.fetchCategoryById(userId, categoryId);

        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    /**
     * Add new category for user
     * @param request JWT user token
     * @param categoryMap category title and description in form of Json
     */
    @PostMapping("")
    public ResponseEntity<Category> addCategory(HttpServletRequest request,
                                                @RequestBody Map<String, Object> categoryMap) {

        int userId = JWTUtils.getUserIdFromRequest(request);
        String title = (String) categoryMap.get("title");
        String description = (String) categoryMap.get("description");
        Category category = categoryService.addCategory(userId, title, description);

        return new ResponseEntity<>(category, HttpStatus.CREATED);
    }

    /**
     * Edit existing category (replace title and description)
     * @param request JWT user token
     * @param categoryId global id of category
     * @param category edit date (new title and description)
     */
    @PutMapping("/{categoryId}")
    public ResponseEntity<Map<String, Boolean>> updateCategory(HttpServletRequest request,
                                                              @PathVariable("categoryId") Integer categoryId,
                                                              @RequestBody Category category) {

        int userId = JWTUtils.getUserIdFromRequest(request);
        categoryService.updateCategory(userId, categoryId, category);
        Map<String, Boolean> response = new HashMap<>();
        response.put("success", true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Delete category, WITH ALL IT'S TRANSACTIONS
     * @param request JWT user token
     * @param categoryId id of category to delete
     */
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Map<String, Boolean>> deleteCategory(HttpServletRequest request,
                                                               @PathVariable("categoryId") Integer categoryId) {

        int userId = JWTUtils.getUserIdFromRequest(request);
        categoryService.removeCategoryWithAllTransactions(userId, categoryId);
        Map<String, Boolean> map = new HashMap<>();
        map.put("success", true);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}
