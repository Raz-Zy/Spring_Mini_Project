package org.kps_group2.spring_mini_project.service.categories;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.kps_group2.spring_mini_project.exception.NotFoundException;
import org.kps_group2.spring_mini_project.model.categorymodel.Category;
import org.kps_group2.spring_mini_project.model.categorymodel.Response.CategoryResponse;
import org.kps_group2.spring_mini_project.model.categorymodel.request.CategoryRequest;
import org.kps_group2.spring_mini_project.model.categorymodel.request.CategoryUpdateRequest;
import org.kps_group2.spring_mini_project.model.dto.AppUser;
import org.kps_group2.spring_mini_project.repository.CategoryRepository;
import org.kps_group2.spring_mini_project.repository.appUserRepository.AppUserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Data
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    private final AppUserRepository appUserRepository;

    @Override
    public List<CategoryResponse> findAllCategory(Integer offset, Integer limit) {
        offset=(offset -1 )*limit;
        List<Category> categories = categoryRepository.findAllCategory(offset,limit);
        List<CategoryResponse> categoryResponses = new ArrayList<>();
        for(Category category : categories){
            CategoryResponse categoryResponse = modelMapper.map(category, CategoryResponse.class);
            categoryResponses.add(categoryResponse);
        }
        return categoryResponses;
    }

    @Override
    public CategoryResponse findCategoryById(UUID id) {
        Category category= categoryRepository.findCategoryById(id);
        if(category==null){
            throw new NotFoundException("Category with id: " + id + " doesn't exist.");
        }
        return modelMapper.map(category,CategoryResponse.class);
    }

    @Override
    public CategoryResponse deleteCategoryById(UUID id) {
        if(categoryRepository.findCategoryById(id)==null){
            throw new NotFoundException("Category with id: " + id + " doesn't exist.");
        }
        categoryRepository.deleteCategoryByID(id);
        return null;
    }

    @Override
    public CategoryResponse updateCategoryByID(UUID id, CategoryUpdateRequest categoryRequest) {
        Category category = categoryRepository.findCategoryById(id);
        if (category == null){
            throw new NotFoundException("Category with id: " + id + " doesn't exist.");
        }

        Category categoryUpdated = categoryRepository.updateCategoryById(id, categoryRequest);
        return modelMapper.map(categoryUpdated,CategoryResponse.class);
    }


    @Override
    public CategoryResponse insertCategory(CategoryRequest categoryRequest) {
        String email = getUsernameOfCurrentUser();
        AppUser appUser = appUserRepository.findUserByEmail(email);

        Category category = categoryRepository.insertCategory(categoryRequest, appUser.getUserId());
        return modelMapper.map(category, CategoryResponse.class);
    }

    String getUsernameOfCurrentUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        return userDetails.getUsername();
    }
}
