package com.grabit.service.member;

import com.grabit.Utilities.Utility;
import com.grabit.enums.CommonErrors;
import com.grabit.exception.CustomException;
import com.grabit.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class MemberExistsService {

    @Autowired
    private MemberRepository memberRepository;

    public ResponseEntity<Map<String, Object>> isMemberPresent(String id){
        try{
            Map<String,Object> result=new HashMap<>();
            result.put("member_id",Long.valueOf(id));
            result.put("is_present",memberRepository.existsById(Long.valueOf(id)));
            return ResponseEntity.ok().body(result);
        } catch (NumberFormatException e){
            throw new CustomException(Utility.buildErrorObject(CommonErrors.INVALID_MEMBER_ID.toString(), CommonErrors.INVALID_MEMBER_ID.getMessage(), 400, "updateMember"));
        }
    }
}
