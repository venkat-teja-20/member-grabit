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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@Log4j2
public class GetProfiles {
    private static final String PAGE_NUMBER = "0";
    private static final String PAGE_SIZE = "10";
    private static final String ORDERING = "ASC";
    private static final String SORT_FIELD = "id";

    private static final String jsonTypeInfo = "error";

    @Autowired
    MemberDetailsService memberDetailsService;

    @GetMapping(value = "/member/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object getmembers(@RequestParam(value = "page_number", required = false, defaultValue = PAGE_NUMBER) int pageNumber,
                             @RequestParam(value = "page_size", required = false, defaultValue = PAGE_SIZE) int pageSize,
                             @RequestParam(value = "order_by", required = false, defaultValue = ORDERING) String orderBy,
                             @RequestParam(value = "field", required = false, defaultValue = SORT_FIELD) String orderField,
                             HttpServletResponse response) {
        try {
            return memberDetailsService.getMembersWithPaginationAndSorting(pageNumber, pageSize, orderBy, orderField);
        } catch (CustomException e) {
            response.setStatus(e.getErrorObject().getHttpCode());
            String errorBody = Utility.toJsonSnakeCase(Collections.singletonMap(jsonTypeInfo, e.getErrorObject().getErrorMsg()));
            log.info("GET Profiles List Response : " + errorBody);
            return errorBody;
        } catch (Exception e) {
            log.info("GET Profiles List Response : " + e.getMessage());
            response.setStatus(500);
            return new APIError(CommonErrors.unknown_error.toString(), CommonErrors.unknown_error.getMessage());
        }
    }
}
