package org.kps_group2.spring_mini_project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.kps_group2.spring_mini_project.model.dto.AppUser;
import org.kps_group2.spring_mini_project.model.dto.Response.CategoryResponse;
import org.kps_group2.spring_mini_project.model.dto.Response.UserResponse;

import java.sql.Date;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Expense {
    private UUID expenseId;
    private Float amount;
    private String description;
    private Date datetime;
    private UserResponse user;
    private CategoryResponse category;
}

