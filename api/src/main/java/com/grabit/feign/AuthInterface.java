package com.grabit.feign;

import com.grabit.bean.auth.RoleDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "authservice")
public interface AuthInterface {
    @GetMapping(value = "/role/permissions",produces = MediaType.APPLICATION_JSON_VALUE)
    public RoleDTO getPermissions(@RequestParam(value = "role") String role);
}
