package com.example.robotica.service;

import com.example.robotica.dto.UsersDto;
import com.example.robotica.exception.ValidationException;

import java.util.List;

public interface UsersService {

    UsersDto saveUser(UsersDto usersDto) throws ValidationException;

    void deleteUser(Integer userId);

    UsersDto findByLogin(String login);

    List<UsersDto> findAll();
}
