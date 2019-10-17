package com.storage.app.service.impl;

import com.storage.app.exception.SystemException;
import com.storage.app.mapper.UserMapper;
import com.storage.app.model.Authority;
import com.storage.app.model.Login;
import com.storage.app.model.User;
import com.storage.app.model.UserDTO;
import com.storage.app.repository.AuthorityRepository;
import com.storage.app.repository.UserRepository;
import com.storage.app.service.UserService;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final UserMapper userMapper;
  private final PasswordEncoder passwordEncoder;
  private final AuthorityRepository authorityRepository;

  @Override
  public List<User> getAll() {
    return userRepository.findAll();
  }

  @Override
  public User createUser(UserDTO input) {
    User user = userMapper.toUser(input);

    user.setPassword(passwordEncoder.encode(input.getPassword()));

    if (input.getAuthorities() != null) {
      Set<Authority> authorities =
          input.getAuthorities().stream()
              .map(authorityRepository::findById)
              .filter(Optional::isPresent)
              .map(Optional::get)
              .collect(Collectors.toSet());
      user.setAuthorities(authorities);
    }

    log.debug("Created User: {}", user);

    return userRepository.save(user);
  }

  @Override
  public User getUserById(long id) {
    User user = userRepository.getOne(id);

    if (user == null) {
      throw new SystemException("User not found with id " + id);
    }

    return user;
  }

  @Override
  public User updateUser(Login login) {
    Optional<User> user = userRepository.findOneByLogin(login.getUsername());

    if (!user.isPresent()) {
      throw new SystemException("User not found with username " + login.getUsername());
    }

    // Just update/reset password for now
    user.get().setPassword(passwordEncoder.encode(login.getPassword()));

    return user.get();
  }
}
