package vn.iotstar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;

import vn.iotstar.config.StorageProperties;
import vn.iotstar.service.IStorageService;

import org.springframework.boot.CommandLineRunner;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
	@Bean
	CommandLineRunner init(IStorageService storageService) {
		return (args) -> {
			
			storageService.init();
		};
	}
	@Bean
	public ServletWebServerFactory servletContainer() {
	    TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
	    factory.addContextCustomizers(context -> {
	        context.setSessionTimeout(30); // Thời gian timeout là 30 phút
	    });
	    return factory;
	}
}
