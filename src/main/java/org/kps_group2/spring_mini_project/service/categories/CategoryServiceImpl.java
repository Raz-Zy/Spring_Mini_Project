package org.kps_group2.spring_mini_project.service.categories;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.kps_group2.spring_mini_project.exception.NotFoundException;
import org.kps_group2.spring_mini_project.model.categorymodel.Category;
import org.kps_group2.spring_mini_project.model.Users;
import org.kps_group2.spring_mini_project.model.categorymodel.Response.CategoryResponse;
import org.kps_group2.spring_mini_project.model.appUserModel.Response.AppUserRespond;
import org.kps_group2.spring_mini_project.model.categorymodel.request.CategoryRequest;
import org.kps_group2.spring_mini_project.model.categorymodel.request.CategoryUpdateRequest;
import org.kps_group2.spring_mini_project.model.dto.AppUser;
import org.kps_group2.spring_mini_project.repository.CategoryRepository;
import org.kps_group2.spring_mini_project.repository.appUserRepository.AppUserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

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
        return categoryRepository.findAllCategory(offset,limit);
    }

    @Override
    public CategoryResponse findCategoryById(Integer id) {
        Category category= categoryRepository.findCategoryById(id);
        if(category==null){
            throw new NotFoundException(" CategoryId "+id + " Not found");
        }
        return modelMapper.map(category,CategoryResponse.class);
    }

    @Override
    public CategoryResponse deleteCategoryById(Integer id) {
        if(categoryRepository.findCategoryById(id)==null){
            throw new NotFoundException(" CategoryId " +id+ " Not found");
        }
        categoryRepository.deleteCategoryByID(id);
        return null;
    }

    @Override
    public CategoryResponse updateCategoryByID(Integer id, CategoryUpdateRequest categoryRequest) {
        Category category = categoryRepository.findCategoryById(id);
        if (category == null){
            throw new NotFoundException("Category with id: " + id + " doesn't exist.");
        }
        return modelMapper.map(categoryRepository.updateCategoryById(id,categoryRequest),CategoryResponse.class);
    }


    @Override
    public CategoryResponse insertCategory(CategoryRequest categoryRequest) {
        String email = getUsernameOfCurrentUser();
        AppUser appUser = appUserRepository.findUserByEmail(email);

        return categoryRepository.insertCategory(categoryRequest, appUser.getUserId());
    }

    String getUsernameOfCurrentUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        return userDetails.getUsername();
    }



}
