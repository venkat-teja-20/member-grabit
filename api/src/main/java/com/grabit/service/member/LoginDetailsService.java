package com.grabit.service.member;

import com.grabit.Utilities.ModelMapperUtility;
import com.grabit.bean.member.LoginDetailsDTO;
import com.grabit.entity.Member;
import com.grabit.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class LoginDetailsService {

    private final MemberRepository memberRepository;

    public LoginDetailsService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public LoginDetailsDTO getDetails(String mobile){
        Member member=memberRepository.findByPhoneNumber(mobile).orElseThrow(()->new EntityNotFoundException("No user exists with mobile : "+mobile));
        return ModelMapperUtility.map(member, LoginDetailsDTO.class);
    }
}
