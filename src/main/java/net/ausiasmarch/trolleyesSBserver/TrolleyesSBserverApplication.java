package net.ausiasmarch.trolleyesSBserver;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/*
import org.hibernate.cfg.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
*/

@SpringBootApplication
public class TrolleyesSBserverApplication {

    //@Autowired
    //private Environment env;

    public static void main(String[] args) {
        SpringApplication.run(TrolleyesSBserverApplication.class, args);
    }
     /*
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                String urls = env.getProperty("cors.urls");
                CorsRegistration reg = registry.addMapping("/api/**");
                for (String url : urls.split(",")) {
                    reg.allowedOrigins(url);
                }
            }
        };
    }
    */
}
