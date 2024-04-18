package org.kps_group2.spring_mini_project.repository;

import org.apache.ibatis.annotations.*;
import org.kps_group2.spring_mini_project.model.appUserModel.Response.AppUserRespond;
import org.kps_group2.spring_mini_project.model.categorymodel.Category;
import org.kps_group2.spring_mini_project.model.categorymodel.Response.CategoryResponse;
import org.kps_group2.spring_mini_project.model.categorymodel.request.CategoryRequest;
import org.kps_group2.spring_mini_project.model.categorymodel.request.CategoryUpdateRequest;

import java.util.List;

@Mapper
public interface CategoryRepository {
    @Select("""
        SELECT * FROM categories LIMIT #{limit} OFFSET #{offset};
        """)
    @Results(id="categoryMapper", value={
            @Result(property ="user" ,column="user_id",one = @One (select = "findUserByUserId")),
            @Result(property = "categoryId", column = "category_id"),
            @Result(property = "name", column = "name")
    })
    List<CategoryResponse> findAllCategory(Integer offset, Integer limit);

    @Select("""
        SELECT * FROM users where user_id = #{userId}
    """)
    @Results(id ="userMapper",value = {
            @Result(property = "userId", column = "user_id"),
            @Result(property = "profileImage", column = "profile_image")
    })
    AppUserRespond findUserByUserId(Integer userId);

    @Select("""
        select * from categories WHERE category_id = #{id}
    """)
    @ResultMap("categoryMapper")
    Category findCategoryById(Integer id);

    @Select("""
        INSERT INTO categories (name, description, user_id)
        values( #{category.name}, #{category.description}, #{userId}) 
        returning *;
    """)
    @ResultMap("categoryMapper")
    CategoryResponse insertCategory(@Param("category") CategoryRequest categoryRequest, Integer userId);


    @Select("""
            DELETE FROM categories WHERE category_id=#{id} returning *;
    """)
    @ResultMap("categoryMapper")
    void deleteCategoryByID(Integer id);


    @Select("""
        update mini_db.public.categories set name= #{category.name} , description= #{category.description} WHERE category_id= #{id} returning *;
    """)
    @ResultMap("categoryMapper")
    Category updateCategoryById(Integer id, @Param("category") CategoryUpdateRequest categoryRequest);
}
