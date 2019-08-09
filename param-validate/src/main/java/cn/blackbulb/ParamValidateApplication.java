package cn.blackbulb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ParamValidateApplication {
    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(ParamValidateApplication.class);
        springApplication.run(args);
        System.out.println("====================================================");
    }
}
