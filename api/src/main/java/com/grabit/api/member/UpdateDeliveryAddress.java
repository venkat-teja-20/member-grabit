package com.grabit.api.member;

import com.grabit.Utilities.Utility;
import com.grabit.bean.address.AddressDTO;
import com.grabit.enums.CommonErrors;
import com.grabit.exception.APIError;
import com.grabit.exception.CustomException;
import com.grabit.service.member.UpdateAddressService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@Log4j2
public class UpdateDeliveryAddress {
    private static final String jsonTypeInfo = "error";

    @Autowired
    UpdateAddressService updateAddressService;

    @PatchMapping(value = "/member/{memberId}/addresses/{addressId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object updateAddress(@PathVariable(value = "memberId") String memberId, @PathVariable(value = "addressId") String addressId, @RequestBody AddressDTO request, HttpServletResponse response) {
        try {
            return updateAddressService.modifyAddress(request, addressId, memberId);
        } catch (CustomException e) {
            response.setStatus(e.getErrorObject().getHttpCode());
            String errorBody = Utility.toJsonSnakeCase(Collections.singletonMap(jsonTypeInfo, e.getErrorObject().getErrorMsg()));
            log.info("Update Delivery Address Response : " + errorBody);
            return errorBody;
        } catch (Exception e) {
            log.info("Update Delivery Address Response : " + e.getMessage());
            response.setStatus(500);
            return new APIError(CommonErrors.unknown_error.toString(), CommonErrors.unknown_error.getMessage());
        }
    }
}
