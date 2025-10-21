package com.grabit.api.member;

import com.grabit.Utilities.Utility;
import com.grabit.enums.CommonErrors;
import com.grabit.exception.APIError;
import com.grabit.exception.CustomException;
import com.grabit.service.member.GetAddressService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@Log4j2
public class GetMemberAddressById {
    private static final String jsonTypeInfo = "error";

    @Autowired
    GetAddressService getAddressService;

    @GetMapping(value = "/member/{memberId}/address/{addressId}",produces = MediaType.APPLICATION_JSON_VALUE)
    public Object getAllMemberAddresses(@PathVariable(value = "memberId") String memberId, @PathVariable(value = "addressId") String addressId,
                                        HttpServletResponse response){
        try{
            return getAddressService.getAddressByAddressId(memberId,addressId);
        } catch (CustomException e){
            response.setStatus(e.getErrorObject().getHttpCode());
            String errorBody= Utility.toJsonSnakeCase(Collections.singletonMap(jsonTypeInfo,e.getErrorObject().getErrorMsg()));
            log.info("GET Address of a Member By Id Response : "+errorBody);
            return errorBody;
        }
        catch (Exception e){
            response.setStatus(500);
            log.info("GET Address of a Member By Id Response : "+e.getMessage());
            return new APIError(CommonErrors.unknown_error.toString(),CommonErrors.unknown_error.getMessage());
        }
    }
}
