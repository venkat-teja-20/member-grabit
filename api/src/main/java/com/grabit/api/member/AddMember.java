package com.grabit.api.member;


import com.grabit.Utilities.Utility;
import com.grabit.bean.member.MemberDTO;
import com.grabit.enums.CommonErrors;
import com.grabit.exception.APIError;
import com.grabit.exception.CustomException;
import com.grabit.service.member.MemberCreateService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
@Log4j2
public class AddMember {

    private static final String jsonTypeInfo = "error";

    @Autowired
    MemberCreateService memberCreateService;

    @PostMapping(value = "/member/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object createMemberProfile(@RequestBody MemberDTO request, @RequestHeader Map<String, String> headers, HttpServletResponse response) {
        try {
            response.setStatus(201);
            return memberCreateService.createMember(request);
        } catch (CustomException e) {
            response.setStatus(e.getErrorObject().getHttpCode());
            String errorBody = Utility.toJsonSnakeCase(Collections.singletonMap(jsonTypeInfo, e.getErrorObject().getErrorMsg()));
            log.info("Member Create Response : " + errorBody);
            return errorBody;
        } catch (Exception e) {
            log.info("Member Create Response : " + e.getMessage());
            response.setStatus(500);
            return new APIError(CommonErrors.unknown_error.toString(), CommonErrors.unknown_error.getMessage());
        }
    }
}
