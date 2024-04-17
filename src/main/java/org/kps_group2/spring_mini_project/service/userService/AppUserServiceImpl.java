package org.kps_group2.spring_mini_project.service.userService;

import lombok.AllArgsConstructor;
import org.kps_group2.spring_mini_project.exception.BadRequestException;
import org.kps_group2.spring_mini_project.model.dto.AppUser;
import org.kps_group2.spring_mini_project.model.dto.Otps;
import org.kps_group2.spring_mini_project.model.dto.Request.AppUserRequestRegister;
import org.kps_group2.spring_mini_project.model.dto.Response.AppUserResponseRegister;
import org.kps_group2.spring_mini_project.repository.appUserRepository.AppUserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AppUserServiceImpl implements AppUserService{
    private final AppUserRepository appUserRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return appUserRepository.findUserByEmail(email);
    }

    @Override
    public Integer insertUser(AppUserRequestRegister register) {
        register.setPassword(passwordEncoder.encode(register.getPassword()));
        AppUser appUser = appUserRepository.findUserByEmail(register.getEmail());
        if (appUser != null)
            throw new BadRequestException("This email is already registered");
        return appUserRepository.insertUser(register);
    }

    @Override
    public AppUserResponseRegister insertOTP(Otps otps) {
        Integer userId = appUserRepository.insertOTP(otps);
        AppUser appUser = appUserRepository.findUserById(userId);
        return modelMapper.map(appUser, AppUserResponseRegister.class);
    }
}
