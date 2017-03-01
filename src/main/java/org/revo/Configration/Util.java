package org.revo.Configration;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.revo.Domain.User;
import org.revo.Service.UserService;
import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

/**
 * Created by ashraf on 18/01/17.
 */
@Configuration
public class Util {
    @Bean
    public CookieCsrfTokenRepository csrfTokenRepository() {
        return CookieCsrfTokenRepository.withHttpOnlyFalse();
    }

    @Bean
    public AuditorAware<User> aware(UserService userService) {
        return userService::current;
    }

    @Bean
    CommandLineRunner runner(UserService userService, AppEnv appEnv) {
        return x -> {
            if (userService.count() == 0) {
                appEnv.getUsers().forEach(userService::save);
            }
        };
    }

    @Bean
    public MethodInvokingFactoryBean ss() {
        MethodInvokingFactoryBean methodInvokingFactoryBean = new MethodInvokingFactoryBean();
        methodInvokingFactoryBean.setTargetClass(SecurityContextHolder.class);
        methodInvokingFactoryBean.setTargetMethod("setStrategyName");
        methodInvokingFactoryBean.setArguments(new Object[]{SecurityContextHolder.MODE_INHERITABLETHREADLOCAL});
        return methodInvokingFactoryBean;
    }

    @Bean
    public Cloudinary cloudinary(AppEnv appEnv) {
        AppEnv.Cloudinary cloudinary = appEnv.getCloudinary();
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", cloudinary.getName(),
                "api_key", cloudinary.getKey(),
                "api_secret", cloudinary.getSecret()));
    }


    @Bean
    UserDetailsService userDetailsService(UserService userService) {
        return s -> userService.findByEmail(s).orElseThrow(() -> new UsernameNotFoundException(s));
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    RememberMeServices rememberMeServices(AppEnv appEnv, UserDetailsService userDetailsService) {
        TokenBasedRememberMeServices tokenBasedRememberMeServices = new TokenBasedRememberMeServices(appEnv.getKey(), userDetailsService);
        tokenBasedRememberMeServices.setAlwaysRemember(true);
        return tokenBasedRememberMeServices;
    }
}