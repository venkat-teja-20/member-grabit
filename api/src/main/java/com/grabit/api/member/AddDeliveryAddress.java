package com.grabit.api.member;


import com.grabit.Utilities.Utility;
import com.grabit.bean.address.AddressDTO;
import com.grabit.enums.CommonErrors;
import com.grabit.exception.APIError;
import com.grabit.exception.CustomException;
import com.grabit.service.member.AddAddressService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@Log4j2
public class AddDeliveryAddress {

    private static final String jsonTypeInfo = "error";

    @Autowired
    AddAddressService addAddressService;

    @PostMapping(value = "/member/{memberId}/addresses", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object addNewAddress(@PathVariable(value = "memberId") String memberId, @RequestBody AddressDTO request, HttpServletResponse response) {
        response.setStatus(201);
        return addAddressService.saveAddress(request, memberId);
    }

}
