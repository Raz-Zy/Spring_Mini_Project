package org.kps_group2.spring_mini_project.model.appUserModel.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppUserRequest {
    private String email;
    private UUID password;
    private UUID confirmPassword;
    private String profileImage;
}
