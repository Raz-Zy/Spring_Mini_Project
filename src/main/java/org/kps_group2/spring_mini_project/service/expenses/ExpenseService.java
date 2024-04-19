package org.kps_group2.spring_mini_project.service.expenses;



import org.kps_group2.spring_mini_project.model.dto.Request.ExpenseRequest;
import org.kps_group2.spring_mini_project.model.dto.Response.ExpenseResponse;

import java.util.List;
import java.util.UUID;

public interface ExpenseService {


    List<ExpenseResponse> getAllExpense(Integer offset, Integer limit, String sortBy, Boolean orderBy);

    ExpenseResponse getAllExpenseById(UUID id);

    ExpenseResponse insertExpense(ExpenseRequest expenseRequest);

    ExpenseResponse updateExpense(ExpenseRequest expenseRequest, UUID id);

    ExpenseResponse deleteExpenseById(UUID id);

}
