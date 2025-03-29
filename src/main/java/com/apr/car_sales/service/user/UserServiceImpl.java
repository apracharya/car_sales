package com.apr.car_sales.service.user;

import com.apr.car_sales.dtos.user.UserRequest;
import com.apr.car_sales.dtos.user.UserResponse;
import com.apr.car_sales.exception.CustomException;
import com.apr.car_sales.exception.ResourceNotFoundException;
import com.apr.car_sales.persistence.user.UserEntity;
import com.apr.car_sales.persistence.user.UserRepository;
import com.apr.car_sales.service.BaseService;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component
public class UserServiceImpl extends BaseService implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository,
                           ModelMapper modelMapper,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserModel readUser(long id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "user id", id));
        if(!user.isActive()) {
            throw new CustomException("The user might have been deleted.");
        }
        return modelMapper.map(user, UserModel.class);
    }

    @Override
    public UserResponse updateUser(long id, UserRequest request) {
        UserEntity user = findEntityByIdOrThrow("User", id, userRepository::findById);
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        UserEntity updated = userRepository.save(user);
        return modelMapper.map(updated, UserResponse.class);
    }

    @Override
    public void deleteUser(long userId) {
        // remaining here, delete all cars and bids and bookings when user deleted.
        UserEntity user = findEntityByIdOrThrow("User", userId, userRepository::findById);
        user.setActive(false);
        userRepository.save(user);
    }

    @Override
    public void recoverDeletedUser(long userId) {
        UserEntity user = findEntityByIdOrThrow("User", userId, userRepository::findById);
        if(user.isActive()) {
            throw new CustomException("User is already active.");
        }
        user.setActive(true);
        userRepository.save(user);
    }
}
