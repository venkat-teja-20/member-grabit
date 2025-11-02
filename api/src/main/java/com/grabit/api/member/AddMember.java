package com.grabit.api.member;


import com.grabit.Utilities.Utility;
import com.grabit.bean.member.MemberDTO;
import com.grabit.enums.CommonErrors;
import com.grabit.enums.RolesList;
import com.grabit.exception.APIError;
import com.grabit.exception.CustomException;
import com.grabit.service.member.MemberCreateService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@RestController
@Log4j2
public class AddMember {

    private static final String jsonTypeInfo = "error";

    @Autowired
    MemberCreateService memberCreateService;

    private static final HttpStatus status=HttpStatus.valueOf(201);

    @PostMapping(value = "/member/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MemberDTO> createMemberProfile(@RequestBody MemberDTO request, @RequestHeader Map<String, String> headers) {
        request.setRole(RolesList.USER);
        MemberDTO memberDTO=memberCreateService.createMember(request);
        return ResponseEntity.status(201).body(memberDTO);
    }
}
