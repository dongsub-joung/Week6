package com.example.week6.service;


import com.example.week6.controller.request.LoginRequestDto;
import com.example.week6.controller.request.MemberRequestDto;
import com.example.week6.controller.request.TokenDto;
import com.example.week6.controller.response.MemberResponseDto;
import com.example.week6.controller.response.ResponseDto;
import com.example.week6.domain.Member;
import com.example.week6.jwt.TokenProvider;
import com.example.week6.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberService {

  private final MemberRepository memberRepository;

  private final PasswordEncoder passwordEncoder;
//  private final AuthenticationManagerBuilder authenticationManagerBuilder;
  private final TokenProvider tokenProvider;

  @Transactional
  public ResponseDto<?> createMember(MemberRequestDto requestDto) {
    if (null != isPresentMember(requestDto.getUsername())) {
      return ResponseDto.fail("DUPLICATED_username",
          "중복된 닉네임 입니다.");
    }

    if (!requestDto.getPassword().equals(requestDto.getPasswordConfirm())) {
      return ResponseDto.fail("PASSWORDS_NOT_MATCHED",
          "비밀번호와 비밀번호 확인이 일치하지 않습니다.");
    }

    Member member = Member.builder()
            .username(requestDto.getUsername())
                .password(passwordEncoder.encode(requestDto.getPassword()))
                    .build();
    memberRepository.save(member);
    return ResponseDto.success(
        MemberResponseDto.builder()
            .id(member.getId())
            .username(member.getUsername())
            .createdAt(member.getCreatedAt())
            .modifiedAt(member.getModifiedAt())
            .build()
    );
  }

  @Transactional
  public ResponseDto<?> login(LoginRequestDto requestDto, HttpServletResponse response) {
    Member member = isPresentMember(requestDto.getUsername());
    if (null == member) {
      return ResponseDto.fail("MEMBER_NOT_FOUND",
          "사용자를 찾을 수 없습니다.");
    }

    if (!member.validatePassword(passwordEncoder, requestDto.getPassword())) {
      return ResponseDto.fail("INVALID_MEMBER", "사용자를 찾을 수 없습니다.");
    }

//    UsernamePasswordAuthenticationToken authenticationToken =
//        new UsernamePasswordAuthenticationToken(requestDto.getusername(), requestDto.getPassword());
//    Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

    TokenDto tokenDto = tokenProvider.generateTokenDto(member);
    tokenToHeaders(tokenDto, response);

    return ResponseDto.success(
        MemberResponseDto.builder()
            .id(member.getId())
            .username(member.getUsername())
            .createdAt(member.getCreatedAt())
            .modifiedAt(member.getModifiedAt())
            .build()
    );
  }

//  @Transactional
//  public ResponseDto<?> reissue(HttpServletRequest request, HttpServletResponse response) {
//    if (!tokenProvider.validateToken(request.getHeader("Refresh-Token"))) {
//      return ResponseDto.fail("INVALID_TOKEN", "Token이 유효하지 않습니다.");
//    }
//    Member member = tokenProvider.getMemberFromAuthentication();
//    if (null == member) {
//      return ResponseDto.fail("MEMBER_NOT_FOUND",
//          "사용자를 찾을 수 없습니다.");
//    }
//
//    Authentication authentication = tokenProvider.getAuthentication(request.getHeader("Access-Token"));
//    RefreshToken refreshToken = tokenProvider.isPresentRefreshToken(member);
//
//    if (!refreshToken.getValue().equals(request.getHeader("Refresh-Token"))) {
//      return ResponseDto.fail("INVALID_TOKEN", "Token이 유효하지 않습니다.");
//    }
//
//    TokenDto tokenDto = tokenProvider.generateTokenDto(member);
//    refreshToken.updateValue(tokenDto.getRefreshToken());
//    tokenToHeaders(tokenDto, response);
//    return ResponseDto.success("success");
//  }

  public ResponseDto<?> logout(HttpServletRequest request) {
    if (!tokenProvider.validateToken(request.getHeader("Refresh-Token"))) {
      return ResponseDto.fail("INVALID_TOKEN", "Token이 유효하지 않습니다.");
    }
    Member member = tokenProvider.getMemberFromAuthentication();
    if (null == member) {
      return ResponseDto.fail("MEMBER_NOT_FOUND",
          "사용자를 찾을 수 없습니다.");
    }

    return tokenProvider.deleteRefreshToken(member);
  }

  @Transactional(readOnly = true)
  public Member isPresentMember(String username) {
    Optional<Member> optionalMember = memberRepository.findByusername(username);
    return optionalMember.orElse(null);
  }

  public void tokenToHeaders(TokenDto tokenDto, HttpServletResponse response) {
    response.addHeader("Authorization", "Bearer " + tokenDto.getAccessToken());
    response.addHeader("Refresh-Token", tokenDto.getRefreshToken());
    response.addHeader("Access-Token-Expire-Time", tokenDto.getAccessTokenExpiresIn().toString());
  }

}
