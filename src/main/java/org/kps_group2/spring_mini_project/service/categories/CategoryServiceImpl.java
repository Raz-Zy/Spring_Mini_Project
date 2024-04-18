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
import org.kps_group2.spring_mini_project.repository.CategoryRepository;
import org.modelmapper.ModelMapper;
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
        Category category= categoryRepository.deleteCategoryByID(id);
        return modelMapper.map(category, CategoryResponse.class);
    }

    @Override
    public CategoryResponse updateCategoryByID(Integer id, CategoryUpdateRequest categoryRequest) {
        return modelMapper.map(categoryRepository.updateCategoryById(id,categoryRequest),CategoryResponse.class);
    }


    @Override
    public CategoryResponse insertCategory(CategoryRequest categoryRequest) {
        Category category = new Category();
        Users user = categoryRepository.findUserByUserId(categoryRequest.getUsersId());
        category.setDescription(categoryRequest.getDescription());
        category.setUsers(user);

        Category categoryAfterInsert = categoryRepository.insertCategory(category);
        CategoryResponse categoryResponse = modelMapper.map(categoryAfterInsert, CategoryResponse.class);
        AppUserRespond appUserRespond = modelMapper.map(user, AppUserRespond.class);
        categoryResponse.setUsers(appUserRespond);

        return categoryResponse;
    }



}
