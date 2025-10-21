package com.grabit.bean.address;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.grabit.bean.member.MemberDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class AddressDTO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("house_number")
    private String houseOrFlatNumber;

    @JsonProperty("address_line")
    private String addressLine;

    @JsonProperty("land_mark")
    private String landMark;

    @JsonProperty("pin_code")
    private String pinCode;

    @JsonProperty("latitude")
    private String latitude;

    @JsonProperty("longitude")
    private String longitude;

    @JsonProperty("address_type")
    private String addressType;

    @JsonProperty("created_by")
    private String createdBy;

    @JsonProperty("created_ts")
    private String createdTs;

    @JsonProperty("updated_by")
    private String updatedBy;

    @JsonProperty("updated_ts")
    private String updatedTs;

    @JsonProperty("member_details")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private MemberDTO memberDTO;
}
