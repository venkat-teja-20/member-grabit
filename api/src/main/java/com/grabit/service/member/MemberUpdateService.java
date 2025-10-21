package com.grabit.service.member;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.grabit.Utilities.ModelMapperUtility;
import com.grabit.Utilities.Utility;
import com.grabit.bean.member.MemberDTO;
import com.grabit.bean.member.MemberRequest;
import com.grabit.entity.Member;
import com.grabit.enums.CommonErrors;
import com.grabit.exception.CustomException;
import com.grabit.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;

@Service
@Log4j2
public class MemberUpdateService {

    @Autowired
    MemberRepository memberRepository;

    @CachePut(value = "member",key = "#id")
    @Transactional(transactionManager = "platformTransactionManager")
    public Object updateMember(MemberRequest request, String id) {
        try {
            Member member = memberRepository.findById(Long.valueOf(id)).orElseThrow(() -> new EntityNotFoundException("Record Not Found"));
            setMemberDetails(request, member);
            Member updatedMember = memberRepository.save(member);
            log.info("Updated Member Details : " + Utility.toJson(updatedMember));
            return ModelMapperUtility.map(updatedMember, MemberDTO.class);
        } catch (EntityNotFoundException e) {
            throw new CustomException(Utility.buildErrorObject(CommonErrors.RECORD_NOT_FOUND.toString(), CommonErrors.RECORD_NOT_FOUND.getMessage(), 404, "updateService"));
        } catch (DataIntegrityViolationException e) {
            if (String.valueOf(e).contains("member_email_unique"))
                throw new CustomException(Utility.buildErrorObject(CommonErrors.EMAIL_ALREADY_EXISTS.toString(), CommonErrors.EMAIL_ALREADY_EXISTS.getMessage(), 400, "updateMember"));
            if (String.valueOf(e).contains("member_phone_number_unique"))
                throw new CustomException(Utility.buildErrorObject(CommonErrors.PHONE_NUMBER_ALREADY_EXISTS.toString(), CommonErrors.PHONE_NUMBER_ALREADY_EXISTS.getMessage(), 400, "updateMember"));
            throw new RuntimeException(e);
        } catch (NumberFormatException e) {
            throw new CustomException(Utility.buildErrorObject(CommonErrors.INVALID_MEMBER_ID.toString(), CommonErrors.INVALID_MEMBER_ID.getMessage(), 400, "updateMember"));
        } catch (JsonProcessingException e) {
            throw new CustomException(Utility.buildErrorObject("PARSING_ERROR", "Error while parsing the request", 500, "updateService"));
        }
    }

    private void setMemberDetails(MemberRequest request, Member member) throws JsonProcessingException {
        MemberDTO memberDTO = ModelMapperUtility.map(member, MemberDTO.class);
        ignoreUnmatchedAndNullProperties(request, memberDTO, member);
    }

    public void ignoreUnmatchedAndNullProperties(MemberRequest memberRequest, MemberDTO memberDTO, Member member) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        String memberRequestJson = objectMapper.writeValueAsString(memberRequest);
        objectMapper.readerForUpdating(memberDTO).readValue(memberRequestJson);
        BeanUtils.copyProperties(memberDTO, member);
    }
}
