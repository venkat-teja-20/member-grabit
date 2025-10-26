package com.grabit.bean.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.grabit.enums.RolesList;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDataDTO {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("mobile")
    private String mobile;

    @JsonProperty("password")
    private String password;

    @JsonProperty("role")
    private RolesList role;
}
