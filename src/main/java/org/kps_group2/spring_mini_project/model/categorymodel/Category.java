package org.kps_group2.spring_mini_project.model.categorymodel;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.kps_group2.spring_mini_project.model.Users;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    private Integer categoryId;
    private String name;
    private String description;
    private Users users;
}
