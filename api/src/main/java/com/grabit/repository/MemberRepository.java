package com.grabit.repository;

import com.grabit.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member,Long> {
    Optional<Member> findMemberByPhoneNumber(String phoneNumber);

    Optional<Member> findMemberByEmail(String email);
}
