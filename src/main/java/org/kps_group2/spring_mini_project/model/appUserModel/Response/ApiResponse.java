package org.kps_group2.spring_mini_project.model.appUserModel.Response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse <T> {
    private String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T payload;
    private HttpStatus status;
    private LocalDateTime time;
}
