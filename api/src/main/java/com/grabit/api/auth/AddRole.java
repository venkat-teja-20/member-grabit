package com.grabit.api.auth;

import com.grabit.Utilities.Utility;
import com.grabit.bean.user.UserDataDTO;
import com.grabit.enums.CommonErrors;
import com.grabit.enums.RolesList;
import com.grabit.exception.APIError;
import com.grabit.exception.CustomException;
import com.grabit.service.user.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@Log4j2
public class AddRole {
    private static final String jsonTypeInfo = "error";

    private UserService userService;

    public AddRole(UserService userService){
        this.userService=userService;
    }

    @PostMapping(value = "/add/role",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public Object addNewUser(@RequestBody UserDataDTO userDataDTO, HttpServletResponse response){
        try{
            response.setStatus(201);
            return userService.addAUser(userDataDTO);
        } catch (CustomException e){
            response.setStatus(e.getErrorObject().getHttpCode());
            String errorBody= Utility.toJsonSnakeCase(Collections.singletonMap(jsonTypeInfo,e.getErrorObject().getErrorMsg()));
            log.info("POST New User Response : "+errorBody);
            return errorBody;
        }
        catch (Exception e){
            response.setStatus(500);
            log.info("POST New User Response : "+e.getMessage());
            return new APIError(CommonErrors.unknown_error.toString(),CommonErrors.unknown_error.getMessage());
        }
    }
}
