package com.grabit.api.member;

import com.grabit.Utilities.ModelMapperUtility;
import com.grabit.Utilities.Utility;
import com.grabit.enums.CommonErrors;
import com.grabit.exception.APIError;
import com.grabit.exception.CustomException;
import com.grabit.service.member.MemberExistsService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
@Log4j2
public class CheckMemberExistence {

    private static final String jsonTypeInfo = "error";

    @Autowired
    MemberExistsService memberExistsService;

    @GetMapping(value = "/member/{memberId}/exists",produces = "application/json")
    public ResponseEntity<Map<String, Object>> checkIfMemberExists(@PathVariable(value = "memberId") String memberId, HttpServletResponse response){
        try {
            return memberExistsService.isMemberPresent(memberId);
        }catch (CustomException e) {
            String errorBody = Utility.toJsonSnakeCase(Collections.singletonMap(jsonTypeInfo, e.getErrorObject().getErrorMsg()));
            log.info("Check Member Exists API Response : " + errorBody);
            Map<String,Object> errorMap=new HashMap<>();
            errorMap.put("code",e.getErrorObject().getErrorMsg().get("code"));
            errorMap.put("message",e.getErrorObject().getErrorMsg().get("message"));
            return ResponseEntity.status(e.getErrorObject().getHttpCode()).body(errorMap);
        } catch (Exception e) {
            log.info("Check Member Exists API Response : " + e.getMessage());
            APIError apiError=new APIError(CommonErrors.unknown_error.toString(), CommonErrors.unknown_error.getMessage());
            return ResponseEntity.internalServerError().body(ModelMapperUtility.map(apiError,Map.class));
        }
    }
}
