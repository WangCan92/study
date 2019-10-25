package cn.blackbulb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author wangcan
 */
@SpringBootApplication
public class JWTApplication {

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(JWTApplication.class);
        springApplication.run(args);
        System.out.println("========================================");
    }
}
