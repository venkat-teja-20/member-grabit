package com.grabit.api.member;

import com.grabit.Utilities.Utility;
import com.grabit.enums.CommonErrors;
import com.grabit.exception.CustomException;
import com.grabit.service.member.DeleteMemberService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

@RestController
@Log4j2
public class DeleteMember {

    private static final String jsonTypeInfo = "error";

    @Autowired
    DeleteMemberService deleteMemberService;

    @DeleteMapping(value = "/member/delete/{memberId}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String,String>> memberDelete(@PathVariable(value = "memberId") String memberId){
        try{
            return deleteMemberService.deleteMember(memberId);
        } catch (CustomException e){
            return ResponseEntity.status(e.getErrorObject().getHttpCode()).body(e.getErrorObject().getErrorMsg());
        }
        catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of(CommonErrors.unknown_error.toString(),CommonErrors.unknown_error.getMessage()));
        }
    }
}
