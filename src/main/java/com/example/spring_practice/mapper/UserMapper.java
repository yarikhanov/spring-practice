package com.example.spring_practice.mapper;

import com.example.spring_practice.dto.UserDto;
import com.example.spring_practice.entity.User;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

  UserDto map (User user);
  @InheritInverseConfiguration
  User map (UserDto userDto);
}
