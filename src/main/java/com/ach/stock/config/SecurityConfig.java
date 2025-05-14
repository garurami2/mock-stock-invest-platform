package com.ach.stock.config;

import com.ach.stock.filter.JwtAuthenticationFilter;
import com.ach.stock.handler.CustomAccessDeniedHandler;
import com.ach.stock.handler.CustomLoginSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.IF_REQUIRED;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private CustomLoginSuccessHandler customLoginSuccessHandler;    // 로그인 관련 Handler

    @Autowired
    private CustomAccessDeniedHandler customAccessDeniedHandler;    // 관리자 페이지 접근 관련 Handler

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/css/**","/login/**", "/signup").permitAll()
                        // JwtAuthenticationFilter 파일에서 UsernamePasswordAuthenticationToken 객체를 생성할때 SimpleGrantedAuthority 선언한 'ROLE_'
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/api/**", "/main", "/stocks/**", "/users/**", "/trades/**").authenticated()
                        .anyRequest().authenticated())
                .sessionManagement(session -> session.sessionCreationPolicy(IF_REQUIRED))
                .formLogin(form -> form
                        .loginPage("/login")    // 사용자 정의 로그인 페이지로 이동하는 url
                        .loginProcessingUrl("/login")   // 로그인 제출 요청 처리 URL(ex : <form action="/login" method="post")
                        .usernameParameter("userId")
                        .passwordParameter("userPw")
                        .successHandler(customLoginSuccessHandler)
                        .failureUrl("/login/error?error=true")// 세션 기반 로그인 유지
                        .permitAll())
                .logout(logout -> logout.logoutUrl("/logout").logoutSuccessUrl("/login"))
                .exceptionHandling(ex -> ex.accessDeniedHandler(customAccessDeniedHandler));

        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // PasswordEncoder Bean 등록
    // 회원가입시 DB 등록 하기전 Password 암호화
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
