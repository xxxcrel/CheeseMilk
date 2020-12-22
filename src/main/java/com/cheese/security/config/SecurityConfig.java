package com.cheese.security.config;

import com.cheese.database.config.CacheConfig;
import com.cheese.database.repository.UserRepository;
import com.cheese.security.DebugFilter;
import com.cheese.security.JsonAccessDeniedHandler;
import com.cheese.security.SettingCharacterSetFilter;
import com.cheese.security.jwt.JwtAuthenticationEntryPoint;
import com.cheese.security.jwt.JwtAuthenticationFilter;
import com.cheese.security.jwt.JwtAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.context.request.async.WebAsyncManagerIntegrationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebSecurity
@Configuration
@ComponentScan(basePackages = "com.cheese.security")
@Import({AclConfig.class, CacheConfig.class})
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(jwtAuthenticationProvider());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Override
    public void init(WebSecurity web) throws Exception {
        super.init(web);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .cors().and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                .antMatchers("/test").permitAll()
                .antMatchers("/register", "/login").permitAll()
                .antMatchers("/code/**").permitAll()
                .antMatchers("/check/**").permitAll()
                .antMatchers(HttpMethod.GET, "/users/**").permitAll()
                .antMatchers(HttpMethod.GET, "/categories/**").permitAll()
                .antMatchers(HttpMethod.GET, "/posts/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(new SettingCharacterSetFilter(), WebAsyncManagerIntegrationFilter.class)
//                .addFilterAfter(new DebugFilter(), SettingCharacterSetFilter.class)
//                .addFilterBefore(new DelegatingFilterProxy(WebMvcConfig.ERROR_PAGE_FILTER_NAME), WebAsyncManagerIntegrationFilter.class)
                .addFilterBefore(jwtAuthenticationFilter(), ExceptionTranslationFilter.class)
                .exceptionHandling()
                    .accessDeniedHandler(new JsonAccessDeniedHandler())
                    .authenticationEntryPoint(new JwtAuthenticationEntryPoint());
//                .httpBasic().authenticationEntryPoint(new BasicAuthenticationEntryPoint());
    }

    @Bean(name = "authenticationManager")
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    @Bean
    public JwtAuthenticationProvider jwtAuthenticationProvider() {
        JwtAuthenticationProvider provider = new JwtAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserCache(cacheManager.getCache("userCache"));
        provider.setUserRepository(userRepository);
        return provider;
    }
}
