package com.grabit.api.member;

import com.grabit.bean.member.MemberDTO;
import com.grabit.service.member.GetMemberDataService;
import com.grabit.service.member.GetMemberService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@Log4j2
public class GetMemberData {

    private final GetMemberService getMemberService;

    public GetMemberData(GetMemberDataService getMemberDataService) {
        this.getMemberService = getMemberDataService;
    }

    @GetMapping(value = {"/member/data","/member/data/"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object getMemberData(@RequestParam(name = "email", required = false) String email,
                                @RequestParam(name = "mobile", required = false) String mobile,
                                HttpServletResponse response
    ) {
        MemberDTO memberDTO = getMemberService.getMemberData(email, mobile);
        return ResponseEntity.ok().body(memberDTO == null ? new HashMap<>() : memberDTO);
    }
}
