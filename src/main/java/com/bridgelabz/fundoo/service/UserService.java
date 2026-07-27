package com.bridgelabz.fundoo.service;

import com.bridgelabz.fundoo.dto.request.*;
import com.bridgelabz.fundoo.dto.response.*;
import com.bridgelabz.fundoo.entity.User;
import com.bridgelabz.fundoo.exception.EmailAlreadyExistsException;
import com.bridgelabz.fundoo.exception.ResourceNotFoundException;
import com.bridgelabz.fundoo.exception.UserNotFoundException;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {
    UserResponseDto registerUser(RegisterRequestDto registerRequest) throws EmailAlreadyExistsException;
    AuthResponseDto loginUser(LoginRequestDto loginRequest) throws UserNotFoundException;
    UserResponseDto getUserById(Long id) throws UserNotFoundException;
//    List<UserResponseDto> getAllUsers();

    Page<UserResponseDto> getAllUsers(
            int page,
            int size,
            String sortBy,
            String direction
    );
    UserResponseDto updateUser(Long id, UpdateRequestDto updateRequest) throws UserNotFoundException;
    UserResponseDto updateUserRole(Long id, com.bridgelabz.fundoo.entity.enums.Role role) throws UserNotFoundException;
    void deleteUser(Long id) throws UserNotFoundException;
    void forgotPassword(ForgotPasswordDto forgotPasswordRequest) throws UserNotFoundException;
    void resetPassword(ResetPasswordDto resetPasswordRequest) throws ResourceNotFoundException;
    User getAuthenticatedUser();
}
