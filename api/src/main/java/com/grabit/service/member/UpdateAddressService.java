package com.grabit.service.member;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.grabit.Utilities.ModelMapperUtility;
import com.grabit.Utilities.Utility;
import com.grabit.bean.address.AddressDTO;
import com.grabit.bean.member.MemberDTO;
import com.grabit.entity.Address;
import com.grabit.enums.CommonErrors;
import com.grabit.exception.CustomException;
import com.grabit.repository.AddressRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Log4j2
public class UpdateAddressService {

    @Autowired
    AddressRepository addressRepository;

    public AddressDTO modifyAddress(AddressDTO request, String addressId, String memberId) {
        try {
            Optional<Address> getAddress = addressRepository.findByIdAndMemberId(Long.valueOf(addressId), Long.valueOf(memberId));
            if (getAddress.isEmpty()) {
                throw new EntityNotFoundException("Record Not Found");
            }
            Address address = getAddress.orElse(new Address());
            setAddressDetails(request, address);
            Address updatedAddress = addressRepository.save(address);
            log.info("Updated Address Details : " + Utility.toJson(updatedAddress));
            AddressDTO addressDTO= ModelMapperUtility.map(updatedAddress, AddressDTO.class);
            addressDTO.setMemberDTO(ModelMapperUtility.map(address.getMember(), MemberDTO.class));
            return addressDTO;
        } catch (EntityNotFoundException e) {
            throw new CustomException(Utility.buildErrorObject(CommonErrors.RECORD_NOT_FOUND.toString(), CommonErrors.RECORD_NOT_FOUND.getMessage(), 404, "updateService"));
        } catch (DataIntegrityViolationException e) {
            throw new CustomException(Utility.buildErrorObject("UNABLE_TO_UPDATE_ADDRESS", e.getMessage(),400,"updateMember"));
        } catch (NumberFormatException e) {
            if(!isNumeric(memberId))
                throw new CustomException(Utility.buildErrorObject(CommonErrors.INVALID_MEMBER_ID.toString(), CommonErrors.INVALID_MEMBER_ID.getMessage(), 400, "updateMember"));
            else if(!isNumeric(addressId))
                throw new CustomException(Utility.buildErrorObject(CommonErrors.INVALID_ADDRESS_ID.toString(), CommonErrors.INVALID_ADDRESS_ID.getMessage(), 400, "updateMember"));
            throw new CustomException(Utility.buildErrorObject("TYPE_CONVERSION_ERROR", e.getMessage(), 400, "updateMember"));
        } catch (JsonProcessingException e) {
            throw new CustomException(Utility.buildErrorObject("PARSING_ERROR", "Error while parsing the request", 500, "updateService"));
        }
    }

    private void setAddressDetails(AddressDTO request, Address address) throws JsonProcessingException {
        AddressDTO currentAddressDTO = ModelMapperUtility.map(address, AddressDTO.class);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        String addressRequestJson = objectMapper.writeValueAsString(request);
        objectMapper.readerForUpdating(currentAddressDTO).readValue(addressRequestJson);
        BeanUtils.copyProperties(currentAddressDTO, address);
    }

    private Boolean isNumeric(String str) {
        return str.matches("\\d+");
    }
}
