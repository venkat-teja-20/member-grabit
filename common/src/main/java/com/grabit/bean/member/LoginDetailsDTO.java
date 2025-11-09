package com.grabit.bean.member;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.grabit.enums.MemberActiveStatus;
import com.grabit.enums.RolesList;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDetailsDTO {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("phone_number")
    private String phoneNumber;

    @JsonProperty("password")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String password;

    @JsonProperty("email")
    private String email;

    @JsonProperty("is_active")
    @Enumerated(EnumType.STRING)
    private MemberActiveStatus isActive;

    @JsonProperty("role")
    @Enumerated(EnumType.STRING)
    private RolesList role;

    public LoginDetailsDTO(Long id,String phoneNumber, String email, String password, MemberActiveStatus isActive, RolesList role) {
        this.id=id;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password=password;
        this.isActive = isActive;
        this.role = role;
    }
}
