package org.kps_group2.spring_mini_project.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.kps_group2.spring_mini_project.model.dto.Request.ExpenseRequest;
import org.kps_group2.spring_mini_project.model.dto.Response.ApiResponse;
import org.kps_group2.spring_mini_project.model.dto.Response.ExpenseResponse;
import org.kps_group2.spring_mini_project.service.expenses.ExpenseService;
import org.kps_group2.spring_mini_project.service.userService.AppUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("api/vi/expenses")
@SecurityRequirement(name = "bearerAuth")
public class ExpenseController {

    private final ExpenseService expenseService;

    @GetMapping
    public ResponseEntity<?> getAllExpense(@RequestParam(defaultValue = "1") @Positive Integer offset, @RequestParam(defaultValue = "5") @Positive Integer limit) {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse<>(
                        "All categories have been successfully founded.",
                        expenseService.getAllExpense(offset, limit),
                        HttpStatus.OK,
                        LocalDateTime.now()
                )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAllExpenseById(@PathVariable Integer id){
        ExpenseResponse expenseResponse = expenseService.getAllExpenseById(id);

        return ResponseEntity.ok(expenseResponse);
    }


    @PostMapping
    public ResponseEntity<?> insertExpense(@RequestBody @Valid ExpenseRequest expenseRequest){
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse<>(
                        "Expense Insert successfully",
                        expenseService.insertExpense(expenseRequest),
                        HttpStatus.OK,
                        LocalDateTime.now()
                )
        );
    }


}
