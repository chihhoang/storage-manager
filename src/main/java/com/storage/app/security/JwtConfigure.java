package com.storage.app.security;

import com.storage.app.filter.JwtTokenFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/** @author choang on 10/22/19 */
@RequiredArgsConstructor
public class JwtConfigure
    extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
  private final JwtTokenProvider tokenProvider;

  @Override
  public void configure(HttpSecurity http) {
    JwtTokenFilter jwtFilter = new JwtTokenFilter(tokenProvider);
    http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
  }
}
