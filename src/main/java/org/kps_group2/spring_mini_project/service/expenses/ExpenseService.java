package org.kps_group2.spring_mini_project.service.expenses;



import org.kps_group2.spring_mini_project.model.dto.Request.ExpenseRequest;
import org.kps_group2.spring_mini_project.model.dto.Response.ExpenseResponse;

import java.util.List;

public interface ExpenseService {


    List<ExpenseResponse> getAllExpense(Integer offset, Integer limit);

    ExpenseResponse getAllExpenseById(Integer id);

    ExpenseResponse insertExpense(ExpenseRequest expenseRequest);
}
