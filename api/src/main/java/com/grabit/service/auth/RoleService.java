package com.grabit.service.auth;

import com.grabit.Utilities.ModelMapperUtility;
import com.grabit.Utilities.Utility;
import com.grabit.bean.auth.RoleDTO;
import com.grabit.entity.Permission;
import com.grabit.entity.Role;
import com.grabit.enums.RolesList;
import com.grabit.exception.CustomException;
import com.grabit.repository.RoleRepository;
import jakarta.persistence.EntityExistsException;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@Log4j2
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role addRole(RoleDTO request){
        try {
            if (roleRepository.findByRole(RolesList.fromValue(request.getRole())).isPresent()) {
                throw new EntityExistsException("Role Already Exists");
            }
            Role role= ModelMapperUtility.map(request,Role.class);
            role.setPermissions(role.getPermissions().stream().peek(permission -> permission.getRoles().add(role)).collect(Collectors.toSet()));
            roleRepository.save(role);
            return role;
        } catch (EntityExistsException e) {
            throw new CustomException(Utility.buildErrorObject("ROLE_ALREADY_EXISTS","A role already exists with name : "+ RolesList.fromValue(request.getRole()),409,"addRole"));
        }
    }
}
