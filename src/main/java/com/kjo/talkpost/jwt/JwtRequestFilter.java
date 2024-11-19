package com.kjo.talkpost.jwt;

import java.io.IOException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.kjo.talkpost.exception.GlobalException;
import com.kjo.talkpost.exception.errorCode.ErrorCode401;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {

  private final JwtProvider jwtProvider;
  private final UserDetailsService userDetailsService;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    String authorizationHeader = request.getHeader("Authorization");
    System.out.println("Authorization header: " + authorizationHeader);
    if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
      String token = authorizationHeader.substring(7);

      if (jwtProvider.isTokenValid(token)) {
        Long userId = jwtProvider.getSubject(token);
        UserDetails userDetails = userDetailsService.loadUserByUsername(userId.toString());

        if (userDetails != null) {
          UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
              new UsernamePasswordAuthenticationToken(
                  userDetails, "", userDetails.getAuthorities());

          SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        } else {
          throw new RuntimeException();
        }
      } else {
        throw new GlobalException(ErrorCode401.INVALID_TOKEN);
      }
    }

    filterChain.doFilter(request, response);
  }
}
