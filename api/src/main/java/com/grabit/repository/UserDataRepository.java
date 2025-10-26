package com.grabit.repository;

import com.grabit.entity.UserData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDataRepository extends JpaRepository<UserData,Long> {
    Optional<UserData> findByMobile(String mobile);
}
