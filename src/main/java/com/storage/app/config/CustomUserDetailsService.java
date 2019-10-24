package com.storage.app.config;

import com.storage.app.exception.SystemException;
import com.storage.app.repository.UserRepository;
import javax.annotation.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/** @author choang on 10/23/19 */
@Service
public class CustomUserDetailsService implements UserDetailsService {
  @Resource private UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return userRepository
        .findOneByLogin(username)
        .map(
            user ->
                User.withUsername(username)
                    .password(user.getPassword())
                    .authorities(user.getRoles())
                    .accountExpired(false)
                    .accountLocked(false)
                    .disabled(false)
                    .build())
        .orElseThrow(
            () ->
                new SystemException(
                    "Unable to find user with username " + username, HttpStatus.NOT_FOUND));
  }
}
