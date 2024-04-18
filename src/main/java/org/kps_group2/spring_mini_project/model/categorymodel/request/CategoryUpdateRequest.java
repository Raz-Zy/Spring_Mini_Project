package org.kps_group2.spring_mini_project.model.categorymodel.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor

public class CategoryUpdateRequest {
    @NotBlank(message = "name can not be blank")
    private String name;
    @NotBlank(message = "name can not be blank")
    private String description;
}
