package com.grabit.api.member;

import com.grabit.bean.member.MemberDTO;
import com.grabit.service.member.GetMemberDataService;
import com.grabit.service.member.GetMemberPasswordService;
import com.grabit.service.member.GetMemberService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@Log4j2
public class GetMemberPasswordAPI {
    private final GetMemberPasswordService getMemberPasswordService;

    public GetMemberPasswordAPI(GetMemberPasswordService getMemberPasswordService) {
        this.getMemberPasswordService = getMemberPasswordService;
    }

    @GetMapping(value = {"/member/credentials","/member/credentials/"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object getMemberPassword(@RequestParam(name = "email", required = false) String email,
                                @RequestParam(name = "mobile", required = false) String mobile,
                                HttpServletResponse response
    ) {
        Map<String,Object> memberDTO = getMemberPasswordService.getMemberPassword(email, mobile);
        return ResponseEntity.ok().body(memberDTO);
    }
}
