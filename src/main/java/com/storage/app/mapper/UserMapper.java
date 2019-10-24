package com.storage.app.mapper;

import com.storage.app.model.User;
import com.storage.app.model.UserDTO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface UserMapper {
  User toUser(UserDTO user);

  @InheritInverseConfiguration
  UserDTO toDtoUser(User user);
}
