package org.kps_group2.spring_mini_project.service.expenses;


import org.kps_group2.spring_mini_project.exception.NotFoundException;
import org.kps_group2.spring_mini_project.model.categorymodel.Category;
import org.kps_group2.spring_mini_project.model.dto.AppUser;
import org.kps_group2.spring_mini_project.model.dto.Request.ExpenseRequest;
import org.kps_group2.spring_mini_project.model.dto.Response.ExpenseResponse;
import org.kps_group2.spring_mini_project.repository.CategoryRepository;
import org.kps_group2.spring_mini_project.repository.appUserRepository.AppUserRepository;
import org.kps_group2.spring_mini_project.service.categories.CategoryService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.kps_group2.spring_mini_project.model.Expense;
import org.kps_group2.spring_mini_project.repository.ExpenseRepository;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Data
@AllArgsConstructor
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final ModelMapper modelMapper;
    private final AppUserRepository appUserRepository;
    private final CategoryRepository categoryRepository;
    private final CategoryService categoryService;


    @Override
    public List<ExpenseResponse> getAllExpense(Integer offset, Integer limit, String sortBy, Boolean orderBy) {
        offset=(offset - 1 )*limit;
        String orderByStr = !orderBy ? "DESC" : "ASC";

        List<Expense> expenses = expenseRepository.getAllExpense(offset,limit, sortBy, orderByStr);
        List<ExpenseResponse> expenseResponse = new ArrayList<>();
        for(Expense expense : expenses){
            ExpenseResponse expenseRes = modelMapper.map(expense,ExpenseResponse.class);
            expenseResponse.add(expenseRes);
        }
        return expenseResponse;
    }

    @Override
    public ExpenseResponse getAllExpenseById(UUID id) {
        Expense expense= expenseRepository.getAllExpenseById(id);
        if (expense == null){
            throw new NotFoundException("Expense with id: " + id + " doesn't exist.");
        }
        return modelMapper.map(expense,ExpenseResponse.class);
    }

    @Override
    public ExpenseResponse insertExpense(ExpenseRequest expenseRequest) {
        String email = getUsernameOfCurrentUser();
        //get the user by email from database for getting userId
        AppUser appUser = appUserRepository.findUserByEmail(email);

        //check category if it doesn't exist.
        Category category = categoryRepository.findCategoryById(expenseRequest.getCategoryId());
        if (category == null){
            throw new NotFoundException("Category with id: " + expenseRequest.getCategoryId() + " doesn't exist.");
        }

        Expense expense = expenseRepository.insertExpense(expenseRequest, appUser.getUserId());

        return modelMapper.map(expense, ExpenseResponse.class);
    }

    @Override
    public ExpenseResponse updateExpense(ExpenseRequest expenseRequest, UUID id) {
        getAllExpenseById(id);
        categoryService.findCategoryById(expenseRequest.getCategoryId());

        Expense expense = expenseRepository.updateExpense(expenseRequest, id);
        return modelMapper.map(expense, ExpenseResponse.class);
    }

    @Override
    public ExpenseResponse deleteExpenseById(UUID id) {
        if(expenseRepository.getAllExpenseById(id)==null){
            throw new NotFoundException("Expense with id: " + id + " doesn't exist.");
        }
        expenseRepository.deleteExpenseById(id);
        return null;
    }

    //This method is use the load the current email
    String getUsernameOfCurrentUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        return userDetails.getUsername();
    }

}