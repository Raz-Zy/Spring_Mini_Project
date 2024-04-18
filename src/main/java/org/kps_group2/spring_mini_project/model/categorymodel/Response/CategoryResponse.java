package org.kps_group2.spring_mini_project.model.categorymodel.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.kps_group2.spring_mini_project.model.appUserModel.Response.AppUserRespond;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CategoryResponse {
        private Integer categoryId;
        private String name;
        private String description;
        private AppUserRespond user;
}
