package org.kps_group2.spring_mini_project.controller;

import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.kps_group2.spring_mini_project.exception.BadRequestException;
import org.kps_group2.spring_mini_project.jwt.JwtService;
import org.kps_group2.spring_mini_project.model.dto.Otps;
import org.kps_group2.spring_mini_project.model.dto.Request.AppUserRequestLogin;
import org.kps_group2.spring_mini_project.model.dto.Request.AppUserRequestRegister;
import org.kps_group2.spring_mini_project.model.dto.Response.AppUserResponseLogin;
import org.kps_group2.spring_mini_project.model.dto.Response.AppUserResponseRegister;
import org.kps_group2.spring_mini_project.service.userService.AppUserService;
import org.kps_group2.spring_mini_project.service.userService.EmailingService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Random;


@RestController
@RequestMapping("/auth/v1")
@AllArgsConstructor
public class AuthController {
    private final AppUserService appUserService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final EmailingService emailingService;

    private void authenticate(String username, String password) throws Exception {
        try {
            UserDetails userApp = appUserService.loadUserByUsername(username);
            if (userApp == null){throw new BadRequestException("Wrong Email");}
            if (!passwordEncoder.matches(password, userApp.getPassword())){
                throw new BadRequestException("Wrong Password");}
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);}
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody @Valid AppUserRequestLogin appUserRequestLogin) throws Exception {
        authenticate(appUserRequestLogin.getEmail(), appUserRequestLogin.getPassword());
        final UserDetails userDetails = appUserService.loadUserByUsername(appUserRequestLogin.getEmail());
        final String token = jwtService.generateToken(userDetails);
        AppUserResponseLogin authResponse = new AppUserResponseLogin(token);
        return ResponseEntity.ok(authResponse);
    }

    @PostMapping("/register")
    public ResponseEntity<?> userRegister(@RequestBody @Valid AppUserRequestRegister register) throws MessagingException {
        if(!register.getPassword().equals(register.getConfirmPassword())){
            throw new BadRequestException("Your confirm password does not match with your password");
        }

        LocalDateTime issuedAt = LocalDateTime.now();
        LocalDateTime expiration = issuedAt.plusMinutes(1);
        String otpCode = generateOTP(6);
        Integer userId = appUserService.insertUser(register);

        Otps otps = Otps.builder()
                .optCode(otpCode)
                .issuedAt(issuedAt)
                .expiration(expiration)
                .verify(false)
                .userId(userId)
                .build();

        AppUserResponseRegister responseRegister = appUserService.insertOTP(otps);

        //Inject to the mail service
        emailingService.sendMail(otpCode, register.getEmail());

        return ResponseEntity.ok(responseRegister);
    }

    public String generateOTP(int length){
        String numbers = "0123456789";
        Random random = new Random();
        char[] otp = new char[length];

        for(int i = 0 ; i<length; i++){
            otp[i] = numbers.charAt(random.nextInt(numbers.length()));
        }
        return new String(otp);
    }

}
