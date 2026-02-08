package com.grabit.service.member;


import com.grabit.Utilities.ModelMapperUtility;
import com.grabit.Utilities.Utility;
import com.grabit.bean.address.AddressDTO;
import com.grabit.bean.member.MemberDTO;
import com.grabit.entity.Address;
import com.grabit.entity.Member;
import com.grabit.enums.CommonErrors;
import com.grabit.enums.RolesList;
import com.grabit.exception.CustomException;
import com.grabit.repository.MemberRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Log4j2
public class MemberCreateService {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public MemberDTO createMember(MemberDTO request) {
        Member member = setMemberDetails(request, new Member());
        List<AddressDTO> addressDTOList = request.getAddressDTOList();
        if (!Utility.isNullOrEmpty(addressDTOList) && !addressDTOList.isEmpty()) {
            List<Address> addresses = new ArrayList<>();
            for (AddressDTO addressDTO : addressDTOList) {
                Address address = new Address();
                BeanUtils.copyProperties(addressDTO, address);
                address.setMember(member);
                addresses.add(address);
            }
            member.setAddresses(addresses);
        }
        Member savedMember = memberRepository.save(member);
        log.info("Member Created : " + Utility.toJson(savedMember));
        MemberDTO memberDTO = ModelMapperUtility.map(savedMember, MemberDTO.class);
        if (!Utility.isNullOrEmpty((savedMember.getAddresses())))
            memberDTO.setAddressDTOList(savedMember.getAddresses().stream().map(address -> ModelMapperUtility.map(address, AddressDTO.class)).toList());
        return memberDTO;
    }

    private Member setMemberDetails(MemberDTO request, Member member) {
        BeanUtils.copyProperties(request, member);
        member.setPassword(passwordEncoder.encode(request.getPassword()));
        return member;
    }
}
