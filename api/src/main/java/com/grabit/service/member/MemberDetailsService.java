package com.grabit.service.member;

import com.grabit.Utilities.ModelMapperUtility;
import com.grabit.Utilities.Utility;
import com.grabit.bean.member.MemberDTO;
import com.grabit.entity.Member;
import com.grabit.enums.CommonErrors;
import com.grabit.exception.CustomException;
import com.grabit.repository.MemberRepository;
import com.grabit.service.CacheService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class MemberDetailsService {

    @Autowired
    MemberRepository memberRepository;

//    @Autowired
//    private CacheService cacheService;

    public List<MemberDTO> getMembersWithPaginationAndSorting(int pageNumber, int pageSize, String orderBy, String orderField) {
        try {
            Sort sort = "DESC".equalsIgnoreCase(orderBy) ? Sort.by(orderField).descending() : Sort.by(orderField).ascending();
            Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
            Page<Member> membersData = memberRepository.findAll(pageable);
            if(membersData.isEmpty())
                throw new CustomException(Utility.buildErrorObject(CommonErrors.NO_DATA_FOUND.toString(),CommonErrors.NO_DATA_FOUND.getMessage(), 204,"getMemberService"));
            List<MemberDTO> memberList = membersData.stream().map(member -> ModelMapperUtility.map(member, MemberDTO.class)).toList();
            log.debug("Member List Response : "+ Utility.toJson(memberList));
//            cacheService.addMemberToRedisCache(memberList);
            return memberList;
        } catch (IllegalArgumentException e) {
            throw new CustomException(Utility.buildErrorObject("INVALID_PARAMETER", e.getMessage(), 400, "getMemberService"));
        } catch (PropertyReferenceException e) {
            throw new CustomException(Utility.buildErrorObject("INVALID_FIELD_NAME", e.getMessage(), 400, "getMemberService"));
        }
    }

    @Cacheable(value = "member",key = "#id")
    public MemberDTO getMemberById(String id) {
        try {
           Member member=memberRepository.findById(Long.valueOf(id)).orElseThrow(()->new EntityNotFoundException("Record Not Found"));
           return ModelMapperUtility.map(member,MemberDTO.class);
        } catch (NumberFormatException e) {
            throw new CustomException(Utility.buildErrorObject(CommonErrors.INVALID_MEMBER_ID.toString(), CommonErrors.INVALID_MEMBER_ID.getMessage(), 400, "updateMember"));
        } catch (EntityNotFoundException e) {
            throw new CustomException(Utility.buildErrorObject(CommonErrors.RECORD_NOT_FOUND.toString(),CommonErrors.RECORD_NOT_FOUND.getMessage(),404,"updateService"));
        }
    }
}
