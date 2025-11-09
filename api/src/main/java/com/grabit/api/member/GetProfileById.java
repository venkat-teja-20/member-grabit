package com.grabit.api.member;

import com.grabit.Utilities.Utility;
import com.grabit.bean.member.MemberDTO;
import com.grabit.enums.CommonErrors;
import com.grabit.exception.APIError;
import com.grabit.exception.CustomException;
import com.grabit.service.member.MemberDetailsService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
@Log4j2
public class GetProfileById {

    @Autowired
    MemberDetailsService memberDetailsService;

    @GetMapping(value = "/member/{member_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyAuthority('END_USER','VIEW_MEMBER')")
    public MemberDTO getMemberById(@PathVariable(value = "member_id") String memberId, @RequestHeader Map<String, String> headers, HttpServletResponse response) {
        Utility.validateIfMemberToken(headers.get("authorization"),memberId,"getMemberById");
        return memberDetailsService.getMemberById(memberId);
    }
}
