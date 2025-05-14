package com.ach.stock.handler;

import com.ach.stock.dto.Users;
import com.ach.stock.repository.LoginRepository;
import com.ach.stock.util.JwtUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtUtil jwtUtil;
    private final LoginRepository loginRepository;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        User springUser = (User) authentication.getPrincipal();   // 여기선 Srping Security User
        String userId = springUser.getUsername(); // userId 추출

        // 다시 DB에서 Users 조회
        Users user = loginRepository.findByUserId(userId).orElse(null);

        if(user != null){
            String token = jwtUtil.generateToken(user);

            Cookie cookie = new Cookie("jwt_token", token);
            cookie.setHttpOnly(true);
            cookie.setPath("/");
            cookie.setMaxAge(3600); // 1 시간

            response.addCookie(cookie);

            // Session 추가
            request.getSession().setAttribute("loginUser", user);

            response.sendRedirect(request.getContextPath() + "/main");
        }else{
            response.sendRedirect("/login/error");
        }
    }
}
