package org.kps_group2.spring_mini_project.model.dto.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseResponse {
    private UUID expenseId;
    private Float amount;
    private String description;
    private Date datetime;
    private UserResponse user;
    private CategoryResponse category;
}