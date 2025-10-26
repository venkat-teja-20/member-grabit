package com.grabit.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.grabit.enums.PermissionsList;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "role_permissions")
@Getter
@Setter
@Table(name = "role_permissions")
public class Permissions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "permission")
    @Enumerated(EnumType.STRING)
    private PermissionsList permission;

    @ManyToOne
    @JoinColumn(name = "role_id",referencedColumnName = "id", nullable = false)
    @JsonIgnore
    private Roles roles;
}
