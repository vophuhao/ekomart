package vn.iotstar.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import vn.iotstar.repository.UserInfoRepository;

import jakarta.servlet.http.Cookie;

import java.util.List;

@Configuration //Đánh dấu lớp này là một lớp 'cấu hình Spring'.
@EnableWebSecurity //Kích hoạt các tính năng bảo mật của Spring Security cho ứng dụng.
public class SecurityConfig {
	
	@Autowired
    private JwtRequestFilter jwtRequestFilter;
	
	@Autowired
	private UserInfoRepository repository; //được tiêm để sử dụng truy vấn UserInfo từ cơ sở dữ liệu.

	//Authentication
	@Bean
	UserDetailsService userDetailsService() {
		return new UserInfoService(repository);
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

	@Bean
	AuthenticationProvider authenticationProvider() {
		//DaoAuthenticationProvider được cấu hình để sử dụng UserDetailsService và PasswordEncoder đã tạo ở trên.
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService());
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		return authenticationProvider;
	}
	private String[] PUBLIC_CSS_VENDOR = {"/vendor/js/**","/vendor/css/**","/vendor/fonts/**","/vendor/images/**"};
	private String[] PUBLIC_END_POINT = {"/login","/register", "/forgotPassword/**", "/authenticate", "/register/**",
			"/authenticatelogin", "/css/**", "/fonts/**", "/images/**", "/js/**", "/verify-otp/**",
			"/admin/**", "/view/**","/vendor/**", "/home/**","/vendor/register/**","/api/v1/vendor/**"};
	// security 6.1+
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception { 
		http.authorizeHttpRequests(auth -> auth
					.requestMatchers("/admin/**","/admin/vendor/**").hasRole("ADMIN")
					.requestMatchers("/vendor/register/**").hasAnyRole("USER","VENDOR")
					.requestMatchers(PUBLIC_CSS_VENDOR).hasAnyRole("USER","VENDOR")
					.requestMatchers("/vendor/**").hasRole("VENDOR")
						.requestMatchers("/user/**").hasAnyRole("USER","VENDOR")
					.requestMatchers(PUBLIC_END_POINT).permitAll()
					.anyRequest().authenticated())
			.logout(logout -> logout
	                .logoutUrl("/logout")
	                .logoutSuccessHandler((request, response, authentication) -> {
	                    // Xoá cookie JWT khi logout
	                    Cookie jwtCookie = new Cookie("JWT", null);
	                    jwtCookie.setPath("/"); // Áp dụng cho toàn bộ ứng dụng
	                    jwtCookie.setHttpOnly(true); // Bảo mật cho cookie
	                    jwtCookie.setMaxAge(0); // Đặt thời gian sống là 0 để xoá cookie
						jwtCookie.setSecure(true);
	                    response.addCookie(jwtCookie);

	                    // Điều hướng hoặc trả về response sau khi logout
	                    response.sendRedirect("/home?logout=true");
	                })
	                .permitAll()
            )
			.sessionManagement(session -> session
	                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
	        )
				.formLogin(AbstractHttpConfigurer::disable);
	    http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
		http.csrf(AbstractHttpConfigurer::disable);
		return http.build();
	}

}