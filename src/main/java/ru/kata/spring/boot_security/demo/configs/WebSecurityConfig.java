package ru.kata.spring.boot_security.demo.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.thymeleaf.dialect.IDialect;
import ru.kata.spring.boot_security.demo.service.UserService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final SuccessUserHandler successUserHandler;
    public WebSecurityConfig(SuccessUserHandler successUserHandler) {
        this.successUserHandler = successUserHandler;
    }

    @Autowired
    private UserService userService;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // настройка секьюрности по определенным URL
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        System.out.println("работает WebSecurityConfig метод configure (HttpSecurity http)");
        http
            .csrf().disable() // хрень
            // запрос конфигурации разрешений через метод authorizeRequests()
            .authorizeRequests()
                // доступно неавторизованным пользователям для регистрации
//                .antMatchers("/registration").not().fullyAuthenticated()
                //Доступ только для пользователей с ролью ADMIN
                .antMatchers("/admin/**").hasRole("ADMIN") // даем разрешение на просмотр
                //Доступ только для пользователей с ролью USER
                .antMatchers("/user").hasAnyRole("USER", "ADMIN") // даем разрешение на просмотр
                //Доступ разрешен для всех пользователей, данные urls защищать не надо(.permitAll())
//                .antMatchers("/", "/resources/**").permitAll()
                .antMatchers("/resources/**").permitAll()
            // Все остальные страницы требуют аутентификации .authenticated()
            .anyRequest().authenticated()
            .and()
                // Form-Based аутентификация
                .formLogin()
                // .loginPage("/") //определяется страница для совершения логина в приложение, которая доступна всем
                // .loginPage("/login")
                //Перенарпавление на главную страницу после успешного входа
                .successHandler(successUserHandler)
//                .defaultSuccessUrl("/")
                .permitAll()
            .and()
                .logout()
                .logoutUrl("/logout")
                .permitAll()
                .logoutSuccessUrl("/login");
    }

    @Autowired
    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        System.out.println("аутентифицируем юзера" + auth.toString());
        auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder());
    }
}