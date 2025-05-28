package vn.iotstar.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**") // Áp dụng CORS cho các API
                .allowedOrigins("http://localhost:8888") // CHỈ cho phép domain đáng tin
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowCredentials(true); // Nếu bạn dùng cookie/session
    }
}

