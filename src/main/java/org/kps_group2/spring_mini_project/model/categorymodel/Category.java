package org.kps_group2.spring_mini_project.model.categorymodel;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.kps_group2.spring_mini_project.model.Users;
import org.kps_group2.spring_mini_project.model.appUserModel.Response.AppUserRespond;
import org.kps_group2.spring_mini_project.model.dto.AppUser;
import org.kps_group2.spring_mini_project.model.dto.Response.UserResponse;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    private UUID categoryId;
    private String name;
    private String description;
    private AppUserRespond user;
}
