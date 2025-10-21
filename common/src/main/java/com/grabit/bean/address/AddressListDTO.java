package com.grabit.bean.address;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.grabit.bean.member.MemberDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AddressListDTO {
    @JsonProperty("address_details")
    private List<AddressDTO> addressList;

    @JsonProperty("member_details")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private MemberDTO memberDTO;
}
