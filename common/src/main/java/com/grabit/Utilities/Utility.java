package com.grabit.Utilities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.grabit.enums.CommonErrors;
import com.grabit.exception.APIError;
import com.grabit.exception.CustomException;
import com.grabit.exception.ErrorObject;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Log4j2
public class Utility {
    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static final ObjectMapper SNAKE_CASE_OBJECT_MAPPER = new ObjectMapper();

    public static String toJson(Object o) {
        try {
            OBJECT_MAPPER.registerModule(new JavaTimeModule()).disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            return OBJECT_MAPPER.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            log.error(e);
            return null;
        }
    }

    public static ErrorObject buildErrorObject(String code, String message, int httpStatusCode, String service) {
        ErrorObject errorObject = new ErrorObject();
        errorObject.setErrorMsg(new APIError(code,message));
        errorObject.setHttpCode(httpStatusCode);
        errorObject.setService(service);
        return errorObject;
    }

    public static String toJsonSnakeCase(Object o) {
        try {
            return SNAKE_CASE_OBJECT_MAPPER.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            log.error(e);
            return null;
        }
    }

    public static Boolean isNullOrEmpty(Object o) {
        return o == null || o.toString().trim().isEmpty();
    }

    public static Boolean isNumeric(String str) {
        return str.matches("\\d+");
    }

    public static void validateIfMemberToken(String token, String memberId, String service) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new CustomException(
                    Utility.buildErrorObject(
                            CommonErrors.AUTHENTICATION_REQUIRED.toString(),
                            CommonErrors.AUTHENTICATION_REQUIRED.getMessage(),
                            401,
                            service
                    )
            );
        }

        boolean isEndUser = authentication.getAuthorities().stream()
                .anyMatch(auth -> "END_USER".equals(auth.getAuthority()));
        if (isEndUser && !memberId.equals(String.valueOf(JWTUtil.extractUserId(token.substring(4))))) {
            throw new CustomException(Utility.buildErrorObject(CommonErrors.Forbidden.toString(), CommonErrors.Forbidden.getMessage(), 403, service));
        }
    }
}
