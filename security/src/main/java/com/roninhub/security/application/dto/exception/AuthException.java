package com.roninhub.security.application.dto.exception;

import com.roninhub.security.application.constant.ResponseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthException extends RuntimeException {
    protected ResponseCode responseCode;
}
