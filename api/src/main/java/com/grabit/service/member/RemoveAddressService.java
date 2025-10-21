package com.grabit.service.member;

import com.grabit.Utilities.Utility;
import com.grabit.enums.CommonErrors;
import com.grabit.exception.CustomException;
import com.grabit.repository.AddressRepository;
import com.grabit.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RemoveAddressService {

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    MemberRepository memberRepository;

    public void removeAddressById(String memberId,String addressId){
        try{
            Boolean isPresent=addressRepository.existsByIdAndMemberId(Long.valueOf(addressId),Long.valueOf(memberId));
            if(isPresent){
                addressRepository.deleteById(Long.valueOf(addressId));
                return;
            }
            throw new EntityNotFoundException("Record Not Found");
        } catch (NumberFormatException e) {
            if(!isNumeric(memberId))
                throw new CustomException(Utility.buildErrorObject(CommonErrors.INVALID_MEMBER_ID.toString(), CommonErrors.INVALID_MEMBER_ID.getMessage(), 400, "updateMember"));
            else if(!isNumeric(addressId))
                throw new CustomException(Utility.buildErrorObject(CommonErrors.INVALID_ADDRESS_ID.toString(), CommonErrors.INVALID_ADDRESS_ID.getMessage(), 400, "updateMember"));
            throw new CustomException(Utility.buildErrorObject("TYPE_CONVERSION_ERROR", e.getMessage(), 400, "updateMember"));
        }
        catch (EntityNotFoundException e){
            throw new CustomException(Utility.buildErrorObject(CommonErrors.RECORD_NOT_FOUND.toString(),CommonErrors.RECORD_NOT_FOUND.getMessage(),404,"updateService"));
        }
    }

    private Boolean isNumeric(String str) {
        return str.matches("\\d+");
    }
}
