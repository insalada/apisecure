package com.ipbsoft.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@ComponentScan("com.ipbsoft")
@EnableWebMvc
@Import({ SecurityConfig.class })
public class AppConfig {
}
