package org.kps_group2.spring_mini_project.repository;

import jakarta.validation.constraints.NotNull;
import org.apache.ibatis.annotations.*;
import org.kps_group2.spring_mini_project.model.appUserModel.Response.AppUserRespond;
import org.kps_group2.spring_mini_project.model.categorymodel.Category;
import org.kps_group2.spring_mini_project.model.categorymodel.request.CategoryRequest;
import org.kps_group2.spring_mini_project.model.categorymodel.request.CategoryUpdateRequest;

import java.util.List;
import java.util.UUID;

@Mapper
public interface CategoryRepository {
    @Select("""
        SELECT * FROM categories LIMIT #{limit} OFFSET #{offset};
        """)
    @Results(id="categoryMapper", value={
            @Result(property = "categoryId", column = "category_id"),
            @Result(property ="user" ,column="user_id",one = @One (select = "findUserByUserId")),
    })
    List<Category> findAllCategory(Integer offset, Integer limit);

    @Select("""
        SELECT * FROM users where user_id = #{userId}
    """)
    @Results(id ="userMapper",value = {
            @Result(property = "userId", column = "user_id"),
            @Result(property = "profileImage", column = "profile_image")
    })
    AppUserRespond findUserByUserId(UUID userId);

    @Select("""
        select * from categories WHERE category_id = #{id}
    """)
    @ResultMap("categoryMapper")
    Category findCategoryById(UUID id);

    @Select("""
        INSERT INTO categories (name, description, user_id)
        values( #{category.name}, #{category.description}, #{userId})
        returning *;
    """)
    @ResultMap("categoryMapper")
    Category insertCategory(@Param("category") CategoryRequest categoryRequest, UUID userId);


    @Select("""
            DELETE FROM categories WHERE category_id=#{id} returning *;
    """)
    @ResultMap("categoryMapper")
    void deleteCategoryByID(UUID id);


    @Select("""
        update categories set name= #{category.name} , description= #{category.description}
        WHERE category_id= #{id}
        returning *;
    """)
    @ResultMap("categoryMapper")
    Category updateCategoryById(UUID id, @Param("category") CategoryUpdateRequest categoryRequest);
}
