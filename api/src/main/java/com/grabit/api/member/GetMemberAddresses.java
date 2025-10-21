package com.grabit.api.member;

import com.grabit.Utilities.Utility;
import com.grabit.bean.address.AddressListDTO;
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

import java.util.ArrayList;
import java.util.Collections;

@RestController
@Log4j2
public class GetMemberAddresses {

    private static final String jsonTypeInfo = "error";

    private static final String PAGE_NUMBER="0";
    private static final String PAGE_SIZE = "10";
    private static final String ORDERING = "DESC";
    private static final String SORT_FIELD = "updatedTs";

    @Autowired
    GetAddressService getAddressService;

    @GetMapping(value = "/member/{memberId}/addresses",produces = MediaType.APPLICATION_JSON_VALUE)
    public Object getAllMemberAddresses(@RequestParam(value = "page_number",required = false,defaultValue = PAGE_NUMBER) int pageNumber,
                                        @RequestParam(value = "page_size", required = false, defaultValue = PAGE_SIZE) int pageSize,
                                        @RequestParam(value = "order_by", required = false, defaultValue = ORDERING) String orderBy,
                                        @RequestParam(value = "field", required = false, defaultValue = SORT_FIELD) String orderField,
                                        @PathVariable(value = "memberId") String memberId,
                                        HttpServletResponse response){
        try{
            AddressListDTO addressListDTO=getAddressService.getAllAddressesOfMember(memberId,pageNumber,pageSize,orderBy,orderField);
            return Utility.isNullOrEmpty(addressListDTO)?new ArrayList<>():addressListDTO;
        } catch (CustomException e){
            response.setStatus(e.getErrorObject().getHttpCode());
            String errorBody= Utility.toJsonSnakeCase(Collections.singletonMap(jsonTypeInfo,e.getErrorObject().getErrorMsg()));
            log.info("GET All Addresses of a Member Response : "+errorBody);
            return errorBody;
        }
        catch (Exception e){
            response.setStatus(500);
            log.info("GET All Addresses of a Member Response : "+e.getMessage());
            return new APIError(CommonErrors.unknown_error.toString(),CommonErrors.unknown_error.getMessage());
        }
    }
}
