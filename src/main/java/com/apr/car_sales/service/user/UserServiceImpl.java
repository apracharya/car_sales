package com.apr.car_sales.service.user;

import com.apr.car_sales.exception.ResourceNotFoundException;
import com.apr.car_sales.persistence.user.UserEntity;
import com.apr.car_sales.persistence.user.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;


@Component
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public UserModel readUser(int id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "user id", id));
        return modelMapper.map(user, UserModel.class);
    }

    @Override
    public UserModel createUser(UserModel userModel) {
        UserEntity entity = modelMapper.map(userModel, UserEntity.class);
        UserEntity created = userRepository.save(entity);
        return modelMapper.map(created, UserModel.class);
    }
}
