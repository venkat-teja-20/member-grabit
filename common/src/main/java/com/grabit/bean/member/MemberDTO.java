package com.grabit.bean.member;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.grabit.bean.address.AddressDTO;
import com.grabit.enums.MemberActiveStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MemberDTO {
    @JsonProperty("id")
    private String id;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    @JsonProperty("date_of_birth")
    private String dateOfBirth;

    @JsonProperty("email")
    private String email;

    @JsonProperty("phone_number")
    private String phoneNumber;

    @JsonProperty("otp")
    private String otp;

    @JsonProperty("password")
    private String password;

    @JsonProperty("is_active")
    private MemberActiveStatus isActive;

    @JsonProperty("role_id")
    private String role;

    @JsonProperty("created_by")
    private String createdBy;

    @JsonProperty("created_ts")
    private String createdTs;

    @JsonProperty("updated_by")
    private String updatedBy;

    @JsonProperty("updated_ts")
    private String updatedTs;

    @JsonProperty("address")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<AddressDTO> addressDTOList;
}
