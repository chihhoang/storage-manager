package com.storage.app.mapper;

import com.storage.app.model.User;
import com.storage.app.model.UserDTO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface UserMapper {
  @Mappings({
    @Mapping(target = "authorities", ignore = true),
  })
  User toUser(UserDTO user);

  @InheritInverseConfiguration
  @Mappings({
    @Mapping(target = "authorities", ignore = true),
  })
  UserDTO toDtoUser(User user);
}
