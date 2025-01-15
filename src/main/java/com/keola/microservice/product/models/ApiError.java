package com.keola.microservice.product.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiError {
    @JsonProperty("code")
    private HttpStatus errorCode;
    @JsonProperty("error_message")
    private String errorMessage;
    @JsonProperty("detail")
    private String detail;
    @JsonProperty("debugMessage")
    private String debugMessage;
    @JsonProperty("error_validation")
    private List<String> listErrorValidation;
}