package org.kps_group2.spring_mini_project.model.dto.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppUserResponseRegister {
    private Integer userId;
    private String email;
    private String profileImage;
}
