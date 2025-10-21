package com.grabit.service.member;

import com.grabit.Utilities.ModelMapperUtility;
import com.grabit.Utilities.Utility;
import com.grabit.bean.address.AddressDTO;
import com.grabit.bean.member.MemberDTO;
import com.grabit.entity.Address;
import com.grabit.entity.Member;
import com.grabit.enums.CommonErrors;
import com.grabit.exception.CustomException;
import com.grabit.repository.AddressRepository;
import com.grabit.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class AddAddressService {

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    MemberRepository memberRepository;

    public AddressDTO saveAddress(AddressDTO request, String memberId) {
        try {
            Member member = memberRepository.findById(Long.valueOf(memberId)).orElseThrow(() -> new EntityNotFoundException("Record Not Found"));
            Address address = setAddress(request, new Address());
            address.setMember(member);
            Address savedAddress = addressRepository.save(address);
            log.info("Saved Address : " + Utility.toJson(savedAddress));
            MemberDTO memberDTO= ModelMapperUtility.map(savedAddress.getMember(),MemberDTO.class);
            AddressDTO addressDTO=ModelMapperUtility.map(savedAddress, AddressDTO.class);
            addressDTO.setMemberDTO(memberDTO);
            return addressDTO;
        }
        catch (EntityNotFoundException e){
            throw new CustomException(Utility.buildErrorObject(CommonErrors.RECORD_NOT_FOUND.toString(),CommonErrors.RECORD_NOT_FOUND.getMessage(), 400,"Add Address Service"));
        }
        catch (NumberFormatException e){
            throw new CustomException(Utility.buildErrorObject(CommonErrors.INVALID_MEMBER_ID.toString(),CommonErrors.INVALID_MEMBER_ID.getMessage(), 400,"Add Address Service"));
        }
        catch (DataIntegrityViolationException e){
            log.info(e);
            throw new CustomException(Utility.buildErrorObject("UNABLE_TO_ADD_ADDRESS", e.getMessage(),400,"createMember"));
        }
    }

    private Address setAddress(AddressDTO request, Address address) {
        BeanUtils.copyProperties(request, address);
        return address;
    }
}
