package com.grabit.repository;


import com.grabit.entity.Address;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address,Long> {
    Page<Address> findAllByMemberId(Long memberId, Pageable pageable);

    Optional<Address> findByIdAndMemberId(Long id, Long memberId);

    Boolean existsByIdAndMemberId(Long id, Long memberId);
}
