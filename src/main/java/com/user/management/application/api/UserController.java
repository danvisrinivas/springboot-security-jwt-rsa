package com.user.management.application.api;

import com.user.management.application.dto.request.UserRequest;
import com.user.management.application.dto.response.TokenResponse;
import com.user.management.application.dto.response.UserResponse;
import com.user.management.application.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("test")
    @PreAuthorize("hasAuthority('USER')")
    public String test(){
        return "User test endpoint";
    }

    @PostMapping
    public ResponseEntity<UserResponse<String>> register(@RequestBody UserRequest userRequest){
        this.userService.register(userRequest);
        return new ResponseEntity<>(new UserResponse<>("User registered successfully"), HttpStatus.CREATED);
    }

    @PostMapping("login")
    public ResponseEntity<TokenResponse<String>> login(@RequestBody UserRequest userRequest){
        return new ResponseEntity<>(new TokenResponse<>(this.userService.login(userRequest)), HttpStatus.OK);
    }

}
