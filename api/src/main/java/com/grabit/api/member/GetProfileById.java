package com.grabit.api.member;

import com.grabit.Utilities.Utility;
import com.grabit.enums.CommonErrors;
import com.grabit.exception.APIError;
import com.grabit.exception.CustomException;
import com.grabit.service.member.MemberDetailsService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@Log4j2
public class GetProfileById {

    private static final String jsonTypeInfo = "error";

    @Autowired
    MemberDetailsService memberDetailsService;

    @GetMapping(value = "/member/{member_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object getMemberById(@PathVariable(value = "member_id") String memberId, HttpServletResponse response) {
        try {
            return memberDetailsService.getMemberById(memberId);
        } catch (CustomException e) {
            response.setStatus(e.getErrorObject().getHttpCode());
            String errorBody = Utility.toJsonSnakeCase(Collections.singletonMap(jsonTypeInfo, e.getErrorObject().getErrorMsg()));
            log.info("GET Profile By Id Response : " + errorBody);
            return errorBody;
        } catch (Exception e) {
            log.info("GET Profile By Id Response : " + e.getMessage());
            response.setStatus(500);
            return new APIError(CommonErrors.unknown_error.toString(), CommonErrors.unknown_error.getMessage());
        }
    }
}
