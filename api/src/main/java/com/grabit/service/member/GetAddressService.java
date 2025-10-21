package com.grabit.service.member;


import com.grabit.Utilities.ModelMapperUtility;
import com.grabit.Utilities.Utility;
import com.grabit.bean.address.AddressDTO;
import com.grabit.bean.address.AddressListDTO;
import com.grabit.bean.member.MemberDTO;
import com.grabit.entity.Address;
import com.grabit.enums.CommonErrors;
import com.grabit.exception.CustomException;
import com.grabit.repository.AddressRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Log4j2
public class GetAddressService {

    @Autowired
    AddressRepository addressRepository;

    public AddressListDTO getAllAddressesOfMember(String memberId, int pageNumber, int pageSize, String orderBy, String orderField) {
        try {
            Sort sort = "ASC".equalsIgnoreCase(orderBy) ? Sort.by(orderField).ascending() : Sort.by(orderField).descending();
            Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
            Page<Address> addresses = addressRepository.findAllByMemberId(Long.valueOf(memberId),pageable);
            if(addresses.isEmpty()){
                return null;
            }
            List<AddressDTO> allAddressOfAMemberDTOs = addresses.stream().map(address -> ModelMapperUtility.map(address, AddressDTO.class)).toList();
            MemberDTO memberDTO=addresses.stream().findFirst().map(address -> ModelMapperUtility.map(address.getMember(), MemberDTO.class)).orElse(null);
            AddressListDTO addressListDTO=new AddressListDTO();
            addressListDTO.setAddressList(allAddressOfAMemberDTOs);
            addressListDTO.setMemberDTO(ModelMapperUtility.map(memberDTO, MemberDTO.class));
            log.debug("Addresses Response : "+ Utility.toJson(allAddressOfAMemberDTOs));
            return addressListDTO;
        }
        catch (EntityNotFoundException e) {
            throw new CustomException(Utility.buildErrorObject(CommonErrors.RECORD_NOT_FOUND.toString(),CommonErrors.RECORD_NOT_FOUND.getMessage(),404,"updateService"));
        }
        catch (NumberFormatException e) {
            throw new CustomException(Utility.buildErrorObject(CommonErrors.INVALID_MEMBER_ID.toString(), CommonErrors.INVALID_MEMBER_ID.getMessage(), 400, "get"));
        }
        catch (IllegalArgumentException e) {
            throw new CustomException(Utility.buildErrorObject("INVALID_PARAMETER", e.getMessage(), 400, "getAllAddressesOfMember"));
        }
        catch (PropertyReferenceException e) {
            throw new CustomException(Utility.buildErrorObject("INVALID_FIELD_NAME", e.getMessage(), 400, "getAllAddressesOfMember"));
        }
    }

    public AddressDTO getAddressByAddressId(String memberId, String addressId) {
        try {
            Optional<Address> addressRecord = addressRepository.findByIdAndMemberId(Long.valueOf(addressId), Long.valueOf(memberId));
            if(addressRecord.isEmpty())
                throw new EntityNotFoundException("Record Not Found");
            Address address=addressRecord.orElse(new Address());
            log.debug("Address Response : "+Utility.toJson(address));
            AddressDTO addressDTO=ModelMapperUtility.map(address, AddressDTO.class);
            if(!Utility.isNullOrEmpty(address.getMember()))
                addressDTO.setMemberDTO(ModelMapperUtility.map(address.getMember(), MemberDTO.class));
            return addressDTO;
        }
        //test and add exceptions properly
        catch (EntityNotFoundException e) {
            throw new CustomException(Utility.buildErrorObject(CommonErrors.RECORD_NOT_FOUND.toString(),CommonErrors.RECORD_NOT_FOUND.getMessage(),404,"updateService"));
        }
        catch (NumberFormatException e) {
            throw new CustomException(Utility.buildErrorObject(CommonErrors.INVALID_ADDRESS_ID.toString(), CommonErrors.INVALID_ADDRESS_ID.getMessage(), 400, "updateMember"));
        }
    }
}
