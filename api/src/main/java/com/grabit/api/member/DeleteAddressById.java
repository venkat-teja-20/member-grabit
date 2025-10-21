package com.grabit.api.member;

import com.grabit.Utilities.Utility;
import com.grabit.enums.CommonErrors;
import com.grabit.exception.APIError;
import com.grabit.exception.CustomException;
import com.grabit.service.member.RemoveAddressService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
@Log4j2
public class DeleteAddressById {

    private static final String jsonTypeInfo = "error";

    @Autowired
    RemoveAddressService removeAddressService;

    @DeleteMapping(value = "/member/{memberId}/address/{addressId}/remove", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object deleteAddressById(@PathVariable(value = "memberId") String memberId, @PathVariable(value = "addressId") String addressId, HttpServletResponse response) {
        try {
            removeAddressService.removeAddressById(memberId, addressId);
            return ResponseEntity.ok().body(Map.of("status","SUCCESS"));
        } catch (CustomException e) {
            response.setStatus(e.getErrorObject().getHttpCode());
            String errorBody = Utility.toJsonSnakeCase(Collections.singletonMap(jsonTypeInfo, e.getErrorObject().getErrorMsg()));
            log.info("Delete Address By Id Response : " + errorBody);
            return errorBody;
        } catch (Exception e) {
            response.setStatus(500);
            return new APIError(CommonErrors.unknown_error.toString(), CommonErrors.unknown_error.getMessage());
        }
    }
}
