package com.grabit.entity;

import com.grabit.enums.MemberActiveStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity(name = "Member")
@Table(
        name = "Member",
        uniqueConstraints = {
                @UniqueConstraint(name = "member_email_unique", columnNames = "email"),
                @UniqueConstraint(name = "member_phone_number_unique", columnNames = "phone_number")
        })
@Getter
@Setter
public class Member extends AuditDetails<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name",
            length = 25,
            nullable = false)
    private String firstName;

    @Column(name = "last_name",
            length = 25)
    private String lastName;

    @Column(name = "date_of_birth")
    private String dateOfBirth;

    @Column(name = "email")
    private String email;

    @Column(
            name = "phone_number",
            length = 10,
            updatable = false
    )
    private String phoneNumber;

    @Column(name = "otp")
    private String otp;

    @Column(name = "password",nullable = false)
    @NotNull
    private String password;

    @Column(name = "is_active",nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull
    private MemberActiveStatus isActive;

    @OneToOne
    @JoinColumn(name = "role_id",referencedColumnName = "id")
    private Role role;

    @OneToMany(mappedBy = "member",cascade = CascadeType.ALL)
    private List<Address> addresses;

    @PrePersist
    public void memberDefaultStatus(){
        if(isActive==null)
            isActive=MemberActiveStatus.TRUE;
    }

}
