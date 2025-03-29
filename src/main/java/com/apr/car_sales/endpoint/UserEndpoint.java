package com.apr.car_sales.endpoint;

import com.apr.car_sales.response.ApiResponse;
import com.apr.car_sales.service.user.UserModel;
import com.apr.car_sales.service.user.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/users")
@Tag(name = "User APIs")
public class UserEndpoint {

    private final UserService userService;

    public UserEndpoint(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<UserModel> readUser(@PathVariable long id) {
        UserModel user = userService.readUser(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping("/delete/{id}")
    public ApiResponse deleteUser(@PathVariable long id) {
        userService.deleteUser(id);
        return new ApiResponse("User deleted successfully.", true);
    }

    @PutMapping("/recover/{id}")
    public ApiResponse recoverUser(@PathVariable long id) {
        userService.recoverDeletedUser(id);
        return new ApiResponse("User recovered successfully.", true);
    }

}
