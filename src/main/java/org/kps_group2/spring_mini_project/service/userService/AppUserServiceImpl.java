package org.kps_group2.spring_mini_project.service.userService;

import lombok.AllArgsConstructor;
import org.kps_group2.spring_mini_project.exception.BadRequestException;
import org.kps_group2.spring_mini_project.exception.NotFoundException;
import org.kps_group2.spring_mini_project.model.dto.AppUser;
import org.kps_group2.spring_mini_project.model.dto.Otps;
import org.kps_group2.spring_mini_project.model.dto.Request.AppUserRequestRegister;
import org.kps_group2.spring_mini_project.model.dto.Request.PasswordRequest;
import org.kps_group2.spring_mini_project.model.dto.Response.AppUserResponseRegister;
import org.kps_group2.spring_mini_project.repository.appUserRepository.AppUserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

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
    public Boolean userVerifying(String email) {
        AppUser appUser = appUserRepository.findUserByEmail(email);
        if (appUser == null){
            throw new NotFoundException("Your email not found.");
        }
        return appUserRepository.userVerifying(appUser.getUserId());
    }

    @Override
    public void verifyingUserEmail(String otpCode) {
        Otps otps = appUserRepository.findOtpCode(otpCode);
        if (otps == null){
            throw new NotFoundException("Otp code doesn't exist.");
        }

        appUserRepository.verifyingUserEmail(otpCode);
    }

    @Override
    public LocalDateTime getExpiration(String otpCode) {
        return appUserRepository.getExpiration(otpCode);
    }

    @Override
    public void updatePassword(String email, PasswordRequest passwordRequest) {
        appUserRepository.updatePassword(email, passwordRequest);
    }

    @Override
    public Boolean getVerifyByOtpCode(String otpCode) {
        return appUserRepository.getVerifyByOtpCode(otpCode);
    }

    @Override
    public UUID insertUser(AppUserRequestRegister register) {
        register.setPassword(passwordEncoder.encode(register.getPassword()));
        AppUser appUser = appUserRepository.findUserByEmail(register.getEmail());
        if (appUser != null)
            throw new BadRequestException("This email is already registered");
        return appUserRepository.insertUser(register);
    }

    @Override
    public AppUserResponseRegister insertOTP(Otps otps) {
        UUID userId = appUserRepository.insertOTP(otps);
        AppUser appUser = appUserRepository.findUserById(userId);
        return modelMapper.map(appUser, AppUserResponseRegister.class);
    }
}
