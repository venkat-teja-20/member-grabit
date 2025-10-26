package com.grabit.entity;

import com.grabit.enums.RolesList;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "roles")
@Getter
@Setter
@Table(name = "roles")
public class Roles {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "role",unique = true,nullable = false)
    @Enumerated(EnumType.STRING)
    private RolesList role;

    @OneToMany(mappedBy = "roles",cascade = CascadeType.ALL)
    private List<Permissions> permissions=new ArrayList<>();
}
