package com.grabit.bean.auth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.grabit.enums.PermissionsList;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class PermissionDTO {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("permission")
    private String permission;
}
