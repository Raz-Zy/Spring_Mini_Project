package org.kps_group2.spring_mini_project.repository.appUserRepository;

import org.apache.ibatis.annotations.*;
import org.kps_group2.spring_mini_project.model.dto.AppUser;
import org.kps_group2.spring_mini_project.model.dto.Otps;
import org.kps_group2.spring_mini_project.model.dto.Request.AppUserRequestRegister;
import org.kps_group2.spring_mini_project.model.dto.Request.PasswordRequest;

import java.time.LocalDateTime;
import java.util.UUID;

@Mapper
public interface AppUserRepository {

    @Select("""
        SELECT * FROM users WHERE email = #{email}
    """)
    @Results(id = "userMapper", value = {
            @Result(property = "userId", column = "user_id"),
            @Result(property = "profileImage", column = "profile_image")
    })
    AppUser findUserByEmail(String email);


    @Select("""
        INSERT INTO users VALUES (default, #{user.email}, #{user.password}, #{user.profileImage})
        RETURNING user_id;
    """)
    UUID insertUser(@Param("user") AppUserRequestRegister register);

    @Select("""
        INSERT INTO otps VALUES (default, #{otp.optCode}, #{otp.issuedAt}, #{otp.expiration}, #{otp.verify}, #{otp.userId})
        RETURNING user_id;
    """)
    UUID insertOTP(@Param("otp") Otps otps);

    @Select("""
        SELECT * FROM users WHERE user_id = #{userId}
    """)
    @ResultMap("userMapper")
    AppUser findUserById(UUID userId);

    @Select("""
        SELECT verify
        FROM otps ot inner join public.users u on u.user_id = ot.user_id
        WHERE ot.user_id = #{userId}
    """)
    Boolean userVerifying(UUID userId);

    @Select("""
        UPDATE otps SET verify = true
        WHERE otp_code = #{otpCode}
    """)
    void verifyingUserEmail(String otpCode);

    @Select("""
        SELECT *
        FROM otps
        WHERE otp_code = #{otpCode}
    """)
    Otps findOtpCode(String otpCode);


    @Select("""
        SELECT expiration
        FROM otps
        WHERE otp_code = #{otpCode}
    """)
    LocalDateTime getExpiration(String otpCode);

    @Select("""
        SELECT * FROM otps WHERE user_id = #{userId}
    """)
    @Results(id = "otpMapper", value = {
            @Result(property = "userId", column = "user_id"),
            @Result(property = "optCode", column = "opt_code"),
            @Result(property = "issuedAt", column = "issued_at"),
            @Result(property = "expiration", column = "expiration"),
            @Result(property = "verify", column = "verify")
    })
    Otps findOtpByUserId(UUID userId);

    @Select("""
        UPDATE otps
        SET otp_code = #{otp.optCode},
            issued_at = #{otp.issuedAt},
            expiration = #{otp.expiration},
            verify = #{otp.verify}
        WHERE user_id = #{otp.userId}
    """)
    void updateOtp(@Param("otp") Otps otp);

    @Update("""
        update  users
        SET password = #{pas.password}
        WHERE email = #{email}
    """)
    void updatePassword(String email,@Param("pas") PasswordRequest passwordRequest);


    @Select("""
        SELECT verify
        FROM otps
        WHERE otp_code = #{otpCode};
    """)
    Boolean getVerifyByOtpCode(String otpCode);
}
