package org.kps_group2.spring_mini_project.controller;

import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import org.kps_group2.spring_mini_project.exception.BadRequestException;
import org.kps_group2.spring_mini_project.exception.NotFoundException;
import org.kps_group2.spring_mini_project.jwt.JwtService;
import org.kps_group2.spring_mini_project.model.dto.AppUser;
import org.kps_group2.spring_mini_project.model.dto.Otps;
import org.kps_group2.spring_mini_project.model.dto.Request.AppUserRequestLogin;
import org.kps_group2.spring_mini_project.model.dto.Request.AppUserRequestRegister;
import org.kps_group2.spring_mini_project.model.dto.Request.PasswordRequest;
import org.kps_group2.spring_mini_project.model.dto.Response.ApiResponse;
import org.kps_group2.spring_mini_project.model.dto.Response.AppUserResponseLogin;
import org.kps_group2.spring_mini_project.model.dto.Response.AppUserResponseRegister;
import org.kps_group2.spring_mini_project.repository.appUserRepository.AppUserRepository;
import org.kps_group2.spring_mini_project.service.userService.AppUserService;
import org.kps_group2.spring_mini_project.service.userService.EmailingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;


@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
public class AuthController {
    private final AppUserService appUserService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final EmailingService emailingService;
    private final AppUserRepository appUserRepository;

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
        //check email
        AppUser user = appUserRepository.findUserByEmail(appUserRequestLogin.getEmail());
        if (user == null){
            throw new NotFoundException("Your email is not found.");
        }

        //check the verification of the user
        Boolean isVerify = appUserService.userVerifying(appUserRequestLogin.getEmail());
        if (!isVerify){
            throw new BadRequestException("Your account is not verify yet");
        }
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
        UUID userId = appUserService.insertUser(register);

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

    @PutMapping("/verify")
    public ResponseEntity<?> verifyingUserEmail(@RequestParam String otpCode){
        LocalDateTime expiration = appUserService.getExpiration(otpCode);
        LocalDateTime now = LocalDateTime.now();

        if (expiration == null){
            throw new NotFoundException("Otp Code is wrong.");
        }

        Boolean isVerify = appUserService.getVerifyByOtpCode(otpCode);
        if (isVerify){
            throw new BadRequestException("Your account is already verified.");
        }

        if (now.isAfter(expiration))
            throw new BadRequestException("Your otp code was expired");


        appUserService.verifyingUserEmail(otpCode);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse<>(
                        "Email successfully verified.",
                        null,
                        HttpStatus.OK,
                        LocalDateTime.now()
                )
        );
    }

    @PostMapping("/resend")
    public ResponseEntity<String> resendOTP(@RequestParam @Email String email) throws MessagingException {
        // Check if the user exists by email
        AppUser user = appUserRepository.findUserByEmail(email);
        if (user == null) {
            throw new BadRequestException("User not found");
        }

        // Find the OTP for the user
        Otps otps = appUserRepository.findOtpByUserId(user.getUserId());
        if (otps == null) {
            throw new BadRequestException("OTP not found");
        }

        // Generate a new OTP
        String otpCode = generateOTP(6);

        // Update the existing OTP details in the database
        otps.setOptCode(otpCode);
        otps.setIssuedAt(LocalDateTime.now());
        otps.setExpiration(LocalDateTime.now().plusMinutes(1));
        appUserRepository.updateOtp(otps);

        // Send the OTP via email
        emailingService.sendMail(otpCode, email);

        return ResponseEntity.ok("OTP has been resent to " + email);
    }

    @PutMapping("/forgot")
    public ResponseEntity<?> forgotPassword(@RequestBody @Valid PasswordRequest passwordRequest,
                                            @RequestParam @Email String email) {
        //check the verification of the user
        Boolean isVerify = appUserService.userVerifying(email);
        if (!isVerify){
            throw new BadRequestException("Your account is not verify yet");
        }
        AppUser user = appUserRepository.findUserByEmail(email);
        if (user==null){
            throw new BadRequestException("User not found");
        }
        appUserService.updatePassword(email, passwordRequest);

        return ResponseEntity.ok("The password has been successfully updated " );
    }



}
