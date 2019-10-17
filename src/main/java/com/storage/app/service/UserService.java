package com.storage.app.service;

import com.storage.app.model.Login;
import com.storage.app.model.User;
import com.storage.app.model.UserDTO;
import java.util.List;

public interface UserService {
  List<User> getAll();

  User createUser(UserDTO user);

  User getUserById(long id);

  User updateUser(Login login);
}
