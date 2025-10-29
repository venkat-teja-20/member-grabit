package com.grabit.entity;

import com.grabit.enums.PermissionsList;
import com.grabit.enums.RolesList;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity(name = "role")
@Getter
@Setter
@Table(name = "role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "role",unique = true,nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull
    private RolesList role;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "role_permissions",
    joinColumns = @JoinColumn(name = "role_id"),
    inverseJoinColumns = @JoinColumn(name = "permission_id"))
    private Set<Permission> permissions=new HashSet<>();
}
