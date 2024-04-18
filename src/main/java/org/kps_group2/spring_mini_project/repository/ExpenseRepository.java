package org.kps_group2.spring_mini_project.repository;

import org.apache.ibatis.annotations.Mapper;

import org.apache.ibatis.annotations.*;
import org.kps_group2.spring_mini_project.model.Expense;
import org.kps_group2.spring_mini_project.model.dto.Request.ExpenseRequest;
import org.kps_group2.spring_mini_project.model.dto.Response.CategoryResponse;
import org.kps_group2.spring_mini_project.model.dto.Response.ExpenseResponse;
import org.kps_group2.spring_mini_project.model.dto.Response.UserResponse;

import java.util.List;

@Mapper
public interface ExpenseRepository {


    @Select("SELECT * FROM expenses OFFSET #{offset} LIMIT #{limit}")
    @Results(id = "expenseMapper", value = {
            @Result(property = "expenseId", column = "expense_id"),
            @Result(property = "amount", column = "amount"),
            @Result(property = "description", column = "description"),
            @Result(property = "datetime", column = "date"),
            @Result(property = "category", column = "user_id",
                    one = @One(select = "getCategoryById")),
            @Result(property = "user", column = "user_id",
                    one = @One(select = "getUserById"))
    })
    List<ExpenseResponse> getAllExpense(Integer offset, Integer limit);

    @Select("""
        SELECT category_id, name, description
        FROM categories
        WHERE user_id = #{userId};
    """)
    @Results(id = "categoryMapper", value = {
            @Result(property = "categoryId", column = "category_id")
    })
    CategoryResponse getCategoryById(Integer userId);


    @Select("""
        SELECT user_id, email, profile_image
        FROM users
        WHERE user_id = #{userId}
    """)
    @Results(id = "userMapper", value = {
            @Result(property = "userId", column = "user_id"),
            @Result(property = "profileImage", column = "profile_image")
    })
    UserResponse getUserById(Integer userId);

    @Select("SELECT * FROM expenses WHERE expense_id = #{id}")
    @ResultMap("expenseMapper")
    Expense getAllExpenseById(Integer id);


    @Select("""
        INSERT INTO expenses (amount, description, date, user_id, category_id)
        VALUES (#{expense.amount}, #{expense.description}, #{expense.datetime}, #{userId}, #{expense.categoryId})
        RETURNING *;
    """)
    @ResultMap("expenseMapper")
    ExpenseResponse insertExpense(@Param("expense") ExpenseRequest expenseRequest, Integer userId);
}
