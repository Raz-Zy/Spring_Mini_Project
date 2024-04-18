package org.kps_group2.spring_mini_project.service.expenses;


import org.kps_group2.spring_mini_project.model.dto.AppUser;
import org.kps_group2.spring_mini_project.model.dto.Request.ExpenseRequest;
import org.kps_group2.spring_mini_project.model.dto.Response.ExpenseResponse;
import org.kps_group2.spring_mini_project.repository.CategoryRepository;
import org.kps_group2.spring_mini_project.repository.appUserRepository.AppUserRepository;
import org.kps_group2.spring_mini_project.service.userService.AppUserService;
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

@Service
@Data
@AllArgsConstructor
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final ModelMapper modelMapper;
    private final AppUserRepository appUserRepository;
    private final CategoryRepository categoryRepository;


    @Override
    public List<ExpenseResponse> getAllExpense(Integer offset, Integer limit) {
        offset=(offset -1 )*limit;
//        List<Expense> expenses = expenseRepository.getAllExpense(offset,limit);
//        List<ExpenseResponse> expenseResponse = new ArrayList<>();
//        for(Expense expense : expenses){
//            ExpenseResponse expenseResponse1 = modelMapper.map(expense,ExpenseResponse.class);
//            expenseResponse.add(expenseResponse1);
//        }
        return expenseRepository.getAllExpense(offset,limit);
    }

    @Override
    public ExpenseResponse getAllExpenseById(Integer id) {
        Expense expense= expenseRepository.getAllExpenseById(id);
        return modelMapper.map(expense,ExpenseResponse.class);
    }

    @Override
    public ExpenseResponse insertExpense(ExpenseRequest expenseRequest) {
        String email = getUsernameOfCurrentUser();
        //get the user by email from database for getting userId
        AppUser appUser = appUserRepository.findUserByEmail(email);

        //check category if it doesn't exist.
//        Category category = categoryRepository.findCategoryById(expenseRequest.getCategoryId());
//        if (category == null){
//            throw new NotFoundException("cate");
//        }

        return expenseRepository.insertExpense(expenseRequest, appUser.getUserId());
    }

    //This method is use the load the current email
    String getUsernameOfCurrentUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        return userDetails.getUsername();
    }

//    @Override
//    public ExpenseResponse insertExpense(ExpenseRequest expenseRequest) {
//        Expense expense = new Expense();
//        Users user = expenseRepository.findUserByUserId(expenseRequest.getUsersId());
//        expense.setAmount(expenseRequest.getAmount());
//        expense.setCategories(expenseRequest.getCategory());
//        expense.setDescription(expenseRequest.getDescription());
//        expense.setUsers(user);
//
//        Expense expenseAfterInsert = expenseRepository.insertExpense(expense);
//        ExpenseResponse expenseResponse = modelMapper.map(expenseAfterInsert,ExpenseResponse.class);
//        UserRespond userRespond = modelMapper.map(user,UserRespond.class);
//        expenseResponse.setUsers(userRespond);
//
//        return expenseResponse;
//
//
//    }
}