package com.grabit.repository;

import com.grabit.bean.member.LoginDetailsDTO;
import com.grabit.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member,Long> {
    Optional<Member> findByPhoneNumber(String phoneNumber);

    Optional<Member> findMemberByEmail(String email);

    @Query("SELECT new com.grabit.bean.member.LoginDetailsDTO(m.id,m.phoneNumber, m.email, m.password, m.isActive, m.role) FROM Member m WHERE m.email = :email")
    Optional<LoginDetailsDTO> findIdAndEmailAndRoleAndPhoneNumberAndIs_activeAndPasswordByEmail(String email);
}
