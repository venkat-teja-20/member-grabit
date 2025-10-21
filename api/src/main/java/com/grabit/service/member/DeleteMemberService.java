package com.grabit.service.member;

import com.grabit.Utilities.Utility;
import com.grabit.enums.CommonErrors;
import com.grabit.exception.CustomException;
import com.grabit.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class DeleteMemberService {

    @Autowired
    MemberRepository memberRepository;

    @CacheEvict(value = "member",key = "#id")
    public ResponseEntity<Map<String,String>> deleteMember(String id){
        try{
            if(memberRepository.existsById(Long.valueOf(id))){
                memberRepository.deleteById(Long.valueOf(id));
                return ResponseEntity.ok().body(Map.of("status","SUCCESS"));
            }
            throw new CustomException(Utility.buildErrorObject(CommonErrors.MEMBER_NOT_FOUND.toString(), CommonErrors.MEMBER_NOT_FOUND.getMessage(), 404, "updateMember"));
        }catch (NumberFormatException e){
            throw new CustomException(Utility.buildErrorObject(CommonErrors.INVALID_MEMBER_ID.toString(), CommonErrors.INVALID_MEMBER_ID.getMessage(), 400, "updateMember"));
        }
    }
}
