package org.kps_group2.spring_mini_project.model.dto.Request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppUserRequestRegister {
    @NotNull
    @NotBlank
    @Email
    private String email;

    @NotNull
    @NotBlank
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$",
            message = "password must be at least 8 characters long and include both letters and numbers")
    private String password;

    @NotNull
    @NotBlank
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$",
            message = "password must be at least 8 characters long and include both letters and numbers")
    private String confirmPassword;

    @NotNull
    @NotBlank
    @Pattern(regexp = "^[^ ]+\\.(jpg|jpeg|png|gif|bmp)$",
            message = "profile must be contain file extension such as jpg, png, gif and bmp only")
    private String profileImage;
}
