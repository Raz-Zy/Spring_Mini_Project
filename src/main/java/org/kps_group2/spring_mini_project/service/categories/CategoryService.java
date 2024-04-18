package org.kps_group2.spring_mini_project.service.categories;

import org.kps_group2.spring_mini_project.model.categorymodel.Category;
import org.kps_group2.spring_mini_project.model.categorymodel.Response.CategoryResponse;
import org.kps_group2.spring_mini_project.model.categorymodel.request.CategoryRequest;
import org.kps_group2.spring_mini_project.model.categorymodel.request.CategoryUpdateRequest;

import java.util.List;

public interface CategoryService {
    List<CategoryResponse> findAllCategory(Integer offset, Integer limit);

    CategoryResponse findCategoryById(Integer id);

    CategoryResponse insertCategory(CategoryRequest categoryRequest);
    CategoryResponse deleteCategoryById(Integer id);
    CategoryResponse updateCategoryByID(Integer id, CategoryUpdateRequest categoryRequest);
}
