package com.khit.recruit.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration		//환경설정에 사용하는 어노테이션
@EnableWebSecurity
public class SecurityConfig {
	
	@Autowired
	private CustomUserDetailsService customService;
	
	@Autowired
	private CustomCompanyUserDetailsService customCompanyService;
	
	//@Bean는 프로젝트에서 관리가 안되는 클래스를 빈으로 사용할때 필요함
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
		//인증 설정 -> 권한 설정
		//로그인이 필요없는 페이지들 - "/", "/css/**", "/images/**", "/auth/main", "/member/**"
		//그외 페이지들은 로그인 필요
		http.userDetailsService(customService);
		http.userDetailsService(customCompanyService);
        
		http.authorizeHttpRequests(authorize -> authorize
                                .requestMatchers("/**", "/css/**", "/images/**", "/js/**", "/mail/**", "/error").permitAll()
                                .requestMatchers("/board/write").authenticated()
                                .requestMatchers("/member/list").hasAnyAuthority("ADMIN")
                                .requestMatchers("/member/**", "/board/**").permitAll()
                                .anyRequest().authenticated()
                )
                .csrf(csrf -> csrf.disable())
                .formLogin(form -> form.loginPage("/member/login")
                                .defaultSuccessUrl("/main").permitAll()
                );

        //접근 권한 페이지
        http.exceptionHandling(handling -> handling.accessDeniedPage("/auth/accessDenied"));

        http.logout(logout -> logout.logoutUrl("/member/logout")
                .logoutRequestMatcher(new AntPathRequestMatcher("/member/logout"))
                .invalidateHttpSession(true)	//¼¼¼Ç ¹«È¿È­
                .logoutSuccessUrl("/main"));
		
		
		return http.build();
	}
	
	
	//암호화 설정
	//PasswordEncoder를 상속받은 BCryptPasswordEncoder를 반환
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
