package com.apr.car_sales.endpoint;

import com.apr.car_sales.dtos.user.UserDto;
import com.apr.car_sales.response.ApiResponse;
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

    @PostMapping("/create")
    public ResponseEntity<UserModel> createUser(@RequestBody UserModel userModel) {
        UserModel user = userService.createUser(userModel);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<UserModel> readUser(@PathVariable int id) {
        UserModel user = userService.readUser(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ApiResponse deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
        return new ApiResponse("User deleted successfully.", true);
    }

    @PostMapping("/login")
    public ResponseEntity<UserModel> loginUser(@RequestBody UserDto userDto) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
