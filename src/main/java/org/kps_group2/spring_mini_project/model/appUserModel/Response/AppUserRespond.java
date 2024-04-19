package org.kps_group2.spring_mini_project.model.appUserModel.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppUserRespond {
    private UUID userId;
    private String email;
    private String profileImage;
}
