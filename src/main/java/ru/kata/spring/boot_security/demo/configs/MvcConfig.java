package ru.kata.spring.boot_security.demo.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    // todo без этих контроллеров отваливается регистрация. За что они отвечают? и где подключаются в конфиге?
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
//        registry.addViewController("/user").setViewName("user");
//        registry.addViewController("/login").setViewName("login");
//        registry.addViewController("/news").setViewName("news");
    }
}
