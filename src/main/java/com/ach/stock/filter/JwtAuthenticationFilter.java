package com.ach.stock.filter;

import com.ach.stock.dto.Users;
import com.ach.stock.repository.LoginRepository;
import com.ach.stock.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;


@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private LoginRepository usersRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        /**
         * 클라이언트 요청의 쿠키에서 'jwt_token'이라는 이름의 값을 추출한다.
         * 로그인 시 생성된 JWT 토큰이 클라이언트 쿠키에 저장되어 있어야함
         */
        String token = extractTokenFromCookie(request);

        // 토큰이 있을 경우
        if (token != null) {
            // 1. 토큰 형식 검증
            if (jwtUtil.validateToken(token)) {
                // 2. 토큰에서 userId 추출
                String userId = jwtUtil.extractUserId(token);

                // 3. 사용자 DB에서 실제 존재하는지 확인
                Users user = usersRepository.findByUserId(userId).orElse(null);

                if (user != null) {
                    // 4. 인증 객체 생성 및 등록
                    UsernamePasswordAuthenticationToken auth =
                            new UsernamePasswordAuthenticationToken(
                                    user,
                                    null,
                                    List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole()))
                            );
                    SecurityContextHolder.getContext().setAuthentication(auth);
                } else {
                    // ❌ 사용자 존재하지 않음 → 쿠키 제거 + 인증 정보 초기화
                    invalidateJwtCookie(response);
                    SecurityContextHolder.clearContext();
                }
            } else {
                // ❌ 유효하지 않은 토큰 → 쿠키 제거 + 인증 정보 초기화
                invalidateJwtCookie(response);
                SecurityContextHolder.clearContext();
            }
        }

        filterChain.doFilter(request, response);
    }

    private String extractTokenFromCookie(HttpServletRequest request) {
        if (request.getCookies() == null) return null;

        log.info("cookies => {}", (Object[]) request.getCookies());

        return Arrays.stream(request.getCookies())
                .filter(c -> "jwt_token".equals(c.getName()))
                .map(Cookie::getValue)
                .findFirst()
                .orElse(null);
    }

    private void invalidateJwtCookie(HttpServletResponse response) {
        Cookie expiredCookie = new Cookie("jwt_token", null);
        expiredCookie.setPath("/");             // 전체 경로에 적용
        expiredCookie.setMaxAge(0);             // 즉시 만료
        expiredCookie.setHttpOnly(true);        // JS 접근 제한
        response.addCookie(expiredCookie);       // 브라우저에 쿠키 삭제 지시
    }

}
