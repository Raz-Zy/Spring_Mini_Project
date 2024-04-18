package org.kps_group2.spring_mini_project.model.dto.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseRequest {
    @NotNull
    private Float amount;

    @NotNull
    @NotBlank
    private String description;

    @NotNull
    @NotBlank
    private Date datetime;

    @NotNull
    private Integer categoryId;
}
