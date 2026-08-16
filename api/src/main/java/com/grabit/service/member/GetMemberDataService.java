package com.grabit.service.member;

import com.grabit.Utilities.ModelMapperUtility;
import com.grabit.Utilities.Utility;
import com.grabit.bean.member.MemberDTO;
import com.grabit.entity.Member;
import com.grabit.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class GetMemberDataService implements GetMemberService {

    private final MemberRepository memberRepository;

    public GetMemberDataService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public MemberDTO getMemberData(String email, String mobile) {
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
            return null;
        return ModelMapperUtility.map(member, MemberDTO.class);
    }
}
