package com.grabit.api.member;

import com.grabit.bean.member.LoginDetailsDTO;
import com.grabit.service.member.LoginDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetLoginDetails {

    @Autowired
    private LoginDetailsService loginDetailsService;

    @GetMapping(value = "/member/credentials",produces = MediaType.APPLICATION_JSON_VALUE)
    public LoginDetailsDTO getDetails(@RequestParam(value = "email") String email){
        return loginDetailsService.getDetails(email);
    }
}
