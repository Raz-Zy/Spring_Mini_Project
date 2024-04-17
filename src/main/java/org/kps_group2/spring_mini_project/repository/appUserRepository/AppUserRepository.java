package org.kps_group2.spring_mini_project.repository.appUserRepository;

import org.apache.ibatis.annotations.*;
import org.kps_group2.spring_mini_project.model.dto.AppUser;
import org.kps_group2.spring_mini_project.model.dto.Otps;
import org.kps_group2.spring_mini_project.model.dto.Request.AppUserRequestRegister;

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
    Integer insertUser(@Param("user") AppUserRequestRegister register);

    @Select("""
        INSERT INTO otps VALUES (default, #{otp.optCode}, #{otp.issuedAt}, #{otp.expiration}, #{otp.verify}, #{otp.userId})
        RETURNING user_id;
    """)
    Integer insertOTP(@Param("otp") Otps otps);

    @Select("""
        SELECT * FROM users WHERE user_id = #{userId}
    """)
    @ResultMap("userMapper")
    AppUser findUserById(Integer userId);
}
