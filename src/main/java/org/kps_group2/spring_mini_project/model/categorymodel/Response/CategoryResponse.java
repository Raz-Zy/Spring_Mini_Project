package org.kps_group2.spring_mini_project.model.categorymodel.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.kps_group2.spring_mini_project.model.appUserModel.Response.AppUserRespond;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CategoryResponse {
        private UUID categoryId;
        private String name;
        private String description;
        private AppUserRespond user;
}
