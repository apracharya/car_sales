package com.apr.car_sales.service.user;

import com.apr.car_sales.dtos.user.UserRequest;
import com.apr.car_sales.dtos.user.UserResponse;

public interface UserService {
    UserModel readUser(long id);
    UserResponse updateUser(long id, UserRequest request);
    void deleteUser(long userId);
    void recoverDeletedUser(long id);
}
