package com.cheese.config;

import com.cheese.web.config.WebMvcConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource(locations = {"classpath:TestRootConfig.xml"})
@Import(WebMvcConfig.class)
public class WebTestConfig {

}
