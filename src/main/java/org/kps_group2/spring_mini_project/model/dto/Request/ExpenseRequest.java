package org.kps_group2.spring_mini_project.model.dto.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseRequest {
    @NotNull
    @Positive
    private Float amount;

    @NotNull
    @NotBlank
    private String description;

    @NotNull
    private Date datetime;

    @NotNull
    private UUID categoryId;
}
