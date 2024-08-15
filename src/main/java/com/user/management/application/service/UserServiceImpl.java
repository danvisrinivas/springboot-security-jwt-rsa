package com.user.management.application.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.user.management.application.dto.request.UserRequest;
import com.user.management.application.entity.Role;
import com.user.management.application.entity.User;
import com.user.management.application.repository.RoleRepository;
import com.user.management.application.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    @Override
    public void register(UserRequest userRequest) {
        User user = this.objectMapper.convertValue(userRequest, User.class);
        Role role = getRole();
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setRoles(List.of(role));
        this.userRepository.save(user);
    }

    @Override
    public String login(UserRequest userRequest) {
        Authentication authentication = this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                userRequest.getUsername(), userRequest.getPassword()
        ));
        return this.tokenService.generateToken(authentication);
    }

    private Role getRole(){
        return this.roleRepository.findByRoleName("USER")
                .orElseGet(() -> Role.builder().roleName("USER").build());
    }
}
