package com.grabit.service.member;

import com.grabit.Utilities.ModelMapperUtility;
import com.grabit.Utilities.Utility;
import com.grabit.bean.member.MemberDTO;
import com.grabit.entity.Member;
import com.grabit.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class GetMemberPasswordService {
    private final MemberRepository memberRepository;

    public GetMemberPasswordService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Map<String,Object> getMemberPassword(String email, String mobile) {
        Member member = null;
        if (!Utility.isNullOrEmpty(email) && !Utility.isNullOrEmpty(mobile)) {
            member = memberRepository.findMemberByEmailAndPhoneNumber(email, mobile).orElseThrow(() -> new EntityNotFoundException("No member found matching the provided email address and phone number."));
        }
        if (!Utility.isNullOrEmpty(email)) {
            member = memberRepository.findMemberByEmail(email).orElseThrow(() -> new EntityNotFoundException("No member found matching the provided email address."));
        }
        if (!Utility.isNullOrEmpty(mobile)) {
            member = memberRepository.findByPhoneNumber(mobile).orElseThrow(() -> new EntityNotFoundException("No member found matching the provided phone number."));
        }
        if (member == null)
            return new HashMap<>();
        Map<String,Object> memberLoginData=new HashMap<>();
        memberLoginData.put("email",member.getEmail());
        memberLoginData.put("password",member.getPassword());
        memberLoginData.put("roles",member.getRole());
        return memberLoginData;
    }
}
