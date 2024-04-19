package org.kps_group2.spring_mini_project.service.categories;

import org.kps_group2.spring_mini_project.model.categorymodel.Response.CategoryResponse;
import org.kps_group2.spring_mini_project.model.categorymodel.request.CategoryRequest;
import org.kps_group2.spring_mini_project.model.categorymodel.request.CategoryUpdateRequest;

import java.util.List;
import java.util.UUID;

public interface CategoryService {
    List<CategoryResponse> findAllCategory(Integer offset, Integer limit);

    CategoryResponse findCategoryById(UUID id);

    CategoryResponse insertCategory(CategoryRequest categoryRequest);
    CategoryResponse deleteCategoryById(UUID id);
    CategoryResponse updateCategoryByID(UUID id, CategoryUpdateRequest categoryRequest);
}
