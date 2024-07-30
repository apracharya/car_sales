package com.apr.car_sales.service.user;

import com.apr.car_sales.exception.AlreadyExistsException;
import com.apr.car_sales.exception.ResourceNotFoundException;
import com.apr.car_sales.persistence.user.UserEntity;
import com.apr.car_sales.persistence.user.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component
public class UserServiceImpl implements UserService {

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
    public UserModel readUser(int id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "user id", id));
        return modelMapper.map(user, UserModel.class);
    }

    @Override
    public UserModel createUser(UserModel userModel) {
        UserEntity entity = modelMapper.map(new UserModel(
                userModel.getId(),
                userModel.getFirstName(),
                userModel.getLastName(),
                userModel.getEmail(),
                userModel.getUsername(),
                this.passwordEncoder.encode(userModel.getPassword()),
                userModel.getCars(),
                userModel.getBookedCars()
        ), UserEntity.class);

        if( ! userRepository.existsByUsername(entity.getUsername())) {
            UserEntity created = userRepository.save(entity);
            return modelMapper.map(created, UserModel.class);
        } else {
            throw new AlreadyExistsException("User already exists!");
        }
    }

    @Override
    public void deleteUser(int userId) {
        // remaining here, delete all cars and bids and bookings when user deleted.
        userRepository.deleteById(userId);

    }
}
