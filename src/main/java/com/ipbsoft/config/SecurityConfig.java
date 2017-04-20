package com.ipbsoft.config;

import com.ipbsoft.beans.Credential;
import com.ipbsoft.repositories.CredentialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@ComponentScan("com.ipbsoft")
@EnableWebSecurity
@EnableGlobalMethodSecurity(jsr250Enabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    CredentialRepository credentialRepository;

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeRequests()
                .antMatchers("/resources").hasAnyRole()
                .and()
                .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()).and()
                .httpBasic();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

        //---Mongo authentication---//
        auth.userDetailsService(service());

        //---In Memory authentication---//
        /*auth.inMemoryAuthentication()
                .withUser("ipbsoft").password("ipbsoft").roles("USER")
                .and()
                .withUser("admin").password("admin").roles("ADMIN");*/
    }


    /**
     * The User object is much more robust than our Account implementation.
     * It supports things like password expiration and accounts being locked
     */
    @Bean
    UserDetailsService service() {
        UserDetailsService service = (String name) -> {
            Credential credential = credentialRepository.findByName(name);
            if(credential == null)
                throw new UsernameNotFoundException("User does not exist: " + name);

            return new User(
                    credential.getName(),
                    credential.getPassword(),
                    AuthorityUtils.createAuthorityList(credential.getRole()));
        };

        return service;
    }

}
