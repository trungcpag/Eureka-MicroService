package com.app.photoappapiusers.service;

import com.app.photoappapiusers.shared.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UsersService extends UserDetailsService {
    UserDto createUser(UserDto userDetail);
    UserDto getUserDetailsByUserName(String email);
}
