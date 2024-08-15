package com.user.management.application.service;

import com.user.management.application.dto.request.UserRequest;

public interface UserService {
    void register(UserRequest userRequest);
    String login(UserRequest userRequest);
}
