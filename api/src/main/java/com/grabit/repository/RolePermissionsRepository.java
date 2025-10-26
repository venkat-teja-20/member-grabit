package com.grabit.repository;

import com.grabit.entity.RolePermissions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolePermissionsRepository extends JpaRepository<RolePermissions,Long> {
}
