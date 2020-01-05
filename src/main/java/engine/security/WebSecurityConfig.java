package engine.security;

import engine.domain.User;
import engine.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.servlet.http.HttpServletResponse;
import java.util.Collections;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserService userService;

    @Autowired
    public WebSecurityConfig(UserService userService) {
        this.userService = userService;
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(username -> {
                    User user = userService.findByEmail(username);
                    if (user != null) {
                        return new org.springframework.security.core.userdetails.User
                                (user.getEmail(), user.getEncryptedPassword(), Collections.emptyList());
                    } else {
                        throw new UsernameNotFoundException("Username not found");
                    }
                }
        ).passwordEncoder(new BCryptPasswordEncoder());
    }

    public void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/quizzes", "/api/quizzes/**").authenticated()
                .antMatchers("/api/register").permitAll()
                .and()
                .httpBasic();
    }
}
