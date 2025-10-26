package com.grabit.service.user;

import com.grabit.Utilities.ModelMapperUtility;
import com.grabit.Utilities.Utility;
import com.grabit.bean.user.UserDataDTO;
import com.grabit.entity.UserData;
import com.grabit.enums.CommonErrors;
import com.grabit.exception.CustomException;
import com.grabit.repository.UserDataRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserDataRepository userDataRepository;

    private final PasswordEncoder passwordEncoder;

    public UserService(UserDataRepository userDataRepository, PasswordEncoder passwordEncoder) {
        this.userDataRepository = userDataRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserDataDTO addAUser(UserDataDTO request){
        try {
            if (userDataRepository.findByMobile(request.getMobile()).isPresent()) {
                throw new EntityExistsException("Mobile number already exists");
            }

            UserData userData = ModelMapperUtility.map(request, UserData.class);
            userData.setPassword(passwordEncoder.encode(request.getPassword()));
            UserData savedUser = userDataRepository.save(userData);
            UserDataDTO responseDTO = ModelMapperUtility.map(savedUser, UserDataDTO.class);
            responseDTO.setPassword("*******");
            return responseDTO;
        }catch (EntityExistsException e) {
            throw new CustomException(Utility.buildErrorObject("MOBILE_EXISTS",e.getMessage(),400,"updateService"));
        }
    }
}
