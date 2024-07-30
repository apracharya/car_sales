package com.apr.car_sales.service.user;

public interface UserService {
    UserModel readUser(int id);
    UserModel createUser(UserModel userModel);

    void deleteUser(int userId);
}
