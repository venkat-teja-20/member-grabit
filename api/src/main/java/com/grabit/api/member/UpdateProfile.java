package com.grabit.api.member;

import com.grabit.Utilities.Utility;
import com.grabit.bean.member.MemberRequest;
import com.grabit.enums.CommonErrors;
import com.grabit.exception.APIError;
import com.grabit.exception.CustomException;
import com.grabit.service.member.MemberUpdateService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@RestController
@Log4j2
public class UpdateProfile {

    private static final String jsonTypeInfo = "error";

    @Autowired
    MemberUpdateService memberUpdateService;

    @PatchMapping(value = "/member/{member_id}/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object updateMemberProfile(@RequestBody MemberRequest request, @PathVariable(value = "member_id") String id, @RequestHeader Map<String, String> headers, HttpServletResponse response) {
        try {
            return memberUpdateService.updateMember(request, id);
        } catch (CustomException e) {
            response.setStatus(e.getErrorObject().getHttpCode());
            String errorBody = Utility.toJsonSnakeCase(Collections.singletonMap(jsonTypeInfo, e.getErrorObject().getErrorMsg()));
            log.info("Update Member Response : " + errorBody);
            return errorBody;
        } catch (Exception e) {
            log.info("Update Member Response : " + e.getMessage());
            response.setStatus(500);
            return new APIError(CommonErrors.unknown_error.toString(), CommonErrors.unknown_error.getMessage());
        }
    }
}
