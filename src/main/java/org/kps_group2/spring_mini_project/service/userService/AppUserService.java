package org.kps_group2.spring_mini_project.service.userService;

import org.kps_group2.spring_mini_project.model.dto.Otps;
import org.kps_group2.spring_mini_project.model.dto.Request.AppUserRequestRegister;
import org.kps_group2.spring_mini_project.model.dto.Request.PasswordRequest;
import org.kps_group2.spring_mini_project.model.dto.Response.AppUserResponseRegister;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.time.LocalDateTime;
import java.util.UUID;

public interface AppUserService extends UserDetailsService {
    UUID insertUser(AppUserRequestRegister register);

    AppUserResponseRegister insertOTP(Otps otps);

    Boolean userVerifying(String email);

    void verifyingUserEmail(String otpCode);

    LocalDateTime getExpiration(String otpCode);

    void updatePassword(String email, PasswordRequest passwordRequest);

    Boolean getVerifyByOtpCode(String otpCode);
}
