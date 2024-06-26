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
public class AppUserRequestLogin {
    @NotNull
    @NotBlank
    @Email(message = "email must well-form and ends with @____.___")
    private String email;

    @NotNull
    @NotBlank
    private String password;
}
