package com.apr.car_sales.endpoint;

import com.apr.car_sales.dtos.user.UserDto;
import com.apr.car_sales.service.user.UserModel;
import com.apr.car_sales.service.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/users")
public class UserEndpoint {

    private final UserService userService;

    public UserEndpoint(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<UserModel> readUser(@PathVariable int id) {
        UserModel user = userService.readUser(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<UserModel> createUser(@RequestBody UserModel userModel) {
        UserModel user = userService.createUser(userModel);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
