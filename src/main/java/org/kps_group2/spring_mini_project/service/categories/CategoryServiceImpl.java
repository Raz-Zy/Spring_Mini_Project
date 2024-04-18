package org.kps_group2.spring_mini_project.service.categories;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.kps_group2.spring_mini_project.model.Category;
import org.kps_group2.spring_mini_project.model.Users;
import org.kps_group2.spring_mini_project.model.appUserModel.Request.CategoryRequest;
import org.kps_group2.spring_mini_project.model.appUserModel.Response.CategoryResponse;
import org.kps_group2.spring_mini_project.repository.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Data
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;


    @Override
    public List<CategoryResponse> findAllCategory(Integer offset, Integer limit) {
        offset=(offset -1 )*limit;
        List<Category> categories = categoryRepository.findAllCategory(offset,limit);
        List<CategoryResponse> categoryResponses = new ArrayList<>();
        for(Category category : categories){
            CategoryResponse categoryResponse = modelMapper.map(category,CategoryResponse.class);
            categoryResponses.add(categoryResponse);
        }
        return categoryResponses;
    }

    @Override
    public CategoryResponse findCategoryById(Integer id) {
        Category category= categoryRepository.findCategoryById(id);
        return modelMapper.map(category,CategoryResponse.class);
    }

    @Override
    public CategoryResponse deleteCategoryById(Integer id) {
        Category category= categoryRepository.findCategoryById(id);
        return modelMapper.map(category, CategoryResponse.class);
    }


//    @Override
//    public CategoryResponse insertCategory(CategoryRequest categoryRequest) {
//        Category category = new Category();
//        Users user = categoryRepository.findUserByUserId(categoryRequest.getUsersId());
//        category.setName(categoryRequest.getName());
//        category.setDescription(categoryRequest.getDescription());
//        category.setUsers(user);
//
//        Category categoryAfterInsert = categoryRepository.insertCategory(category);
//        return modelMapper.map(categoryAfterInsert, CategoryResponse.class);
//    }



}
