package com.grabit.bean.member;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.grabit.bean.address.AddressDTO;
import com.grabit.enums.MemberActiveStatus;
import com.grabit.enums.RolesList;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class MemberDTO {
    @JsonProperty("id")
    private Long id;

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
    @JsonIgnore
    private String otp;

    @JsonProperty(value = "password",access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @JsonProperty("is_active")
    @Enumerated(EnumType.STRING)
    private MemberActiveStatus isActive;

    @JsonProperty("role")
    @Enumerated(EnumType.STRING)
    private RolesList role;

    @JsonProperty("address")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<AddressDTO> addressDTOList;
}
