package org.kps_group2.spring_mini_project.service.userService;

import org.kps_group2.spring_mini_project.model.dto.Otps;
import org.kps_group2.spring_mini_project.model.dto.Request.AppUserRequestRegister;
import org.kps_group2.spring_mini_project.model.dto.Response.AppUserResponseRegister;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AppUserService extends UserDetailsService {
    Integer insertUser(AppUserRequestRegister register);

    AppUserResponseRegister insertOTP(Otps otps);
}
