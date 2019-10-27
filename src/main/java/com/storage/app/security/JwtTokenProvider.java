package com.storage.app.security;

import com.storage.app.exception.SystemException;
import com.storage.app.model.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@Slf4j
public class JwtTokenProvider {
  private static final String AUTHORIZATION_HEADER = "Authorization";
  private static final String AUTHORITIES_KEY = "auth";

  @Value("${security.jwt.secret:secret}")
  private String secret;

  @Value("${security.jwt.expire-milliseconds:360000}") // default an hour
  private long validityInMilliseconds;

  @PostConstruct
  protected void init() {
    secret = Base64.getEncoder().encodeToString(secret.getBytes());
  }

  public String createToken(String login, Set<Role> roles) {
    Date now = new Date();
    Date validity = new Date(now.getTime() + validityInMilliseconds);

    return Jwts.builder()
        .setSubject(login)
        .claim(
            AUTHORITIES_KEY,
            roles.stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(",")))
        .setIssuedAt(now)
        //        .setExpiration(validity)
        .signWith(SignatureAlgorithm.HS512, secret)
        .compact();
  }

  public Authentication getAuthentication(String token) {
    Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    String login = claims.getSubject();

    Collection<? extends GrantedAuthority> authorities =
        Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());

    User principal = new User(login, "", authorities);

    return new UsernamePasswordAuthenticationToken(principal, "", authorities);
  }

  public String getUserLogin(String token) {
    return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
  }

  public String resolveToken(HttpServletRequest request) {
    String bearerToken = request.getHeader(AUTHORIZATION_HEADER);

    if (!StringUtils.isEmpty(bearerToken) && bearerToken.startsWith("Bearer ")) {
      return bearerToken.substring(7);
    }

    return null;
  }

  public boolean validateToken(String token) {
    try {
      Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
      return true;
    } catch (SecurityException | MalformedJwtException e) {
      log.debug("Invalid JWT signature.", e);
    } catch (ExpiredJwtException e) {
      log.debug("Expired JWT token.", e);
    } catch (UnsupportedJwtException e) {
      log.debug("Unsupported JWT token.", e);
    } catch (IllegalArgumentException e) {
      log.debug("JWT token compact of handler are invalid.", e);
    }

    throw new SystemException("Expired or invalid JWT token", HttpStatus.UNAUTHORIZED);
  }
}
