package com.grabit.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.naming.AuthenticationException;

@AllArgsConstructor
public class JwtAuthenticationException extends RuntimeException{
    @Getter
    private APIError authenticationError;
}
