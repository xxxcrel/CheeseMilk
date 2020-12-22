package com.cheese.web.config;

import com.cheese.security.config.SecurityConfig;
import com.cheese.util.FtpUtils;
import com.cheese.web.controller.error.BasicErrorController;
import com.cheese.web.error.*;
import org.hibernate.validator.HibernateValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.*;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

@EnableWebMvc
@EnableSpringDataWebSupport
@Configuration
@ComponentScan(basePackages = {"com.cheese.web.controller", "com.cheese.exception", "com.cheese.service"})
@PropertySource("classpath:application.properties")
@Import({SecurityConfig.class})
@ImportResource(locations = "classpath:databaseInitData.xml")
public class WebMvcConfig implements WebMvcConfigurer {

    public static final String ERROR_PAGE_FILTER_NAME = "errorPageFilter";

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.defaultContentType(MediaType.APPLICATION_JSON);
    }

    @Bean
    public ViewResolver cnViewResolver(ContentNegotiationManager cnm) {
        ContentNegotiatingViewResolver viewResolver = new ContentNegotiatingViewResolver();
        viewResolver.setContentNegotiationManager(cnm);
        return viewResolver;
    }

    @Bean
    public MultipartResolver multipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        return new StandardServletMultipartResolver();
    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.removeIf((converter) -> {
            Class<?> clazz = StringHttpMessageConverter.class;
            return converter.getClass().isAssignableFrom(clazz);
        });
        converters.add(new StringHttpMessageConverter(StandardCharsets.UTF_8));
    }

    @Bean
    public SimpleDateFormat dateFormat(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        return dateFormat;
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowCredentials(true)
                .allowedOrigins(CorsConfiguration.ALL);
    }

    @Override
    public Validator getValidator() {
        return validator();
    }

    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor(){
        MethodValidationPostProcessor processor = new MethodValidationPostProcessor();
        processor.setValidator(validator());
        return processor;
    }

    @Bean LocalValidatorFactoryBean validator(){
        LocalValidatorFactoryBean validatorFactoryBean = new LocalValidatorFactoryBean();
        validatorFactoryBean.setProviderClass(HibernateValidator.class);
        validatorFactoryBean.setValidationMessageSource(messageSource());
        Properties properties = new Properties();
        properties.setProperty("hibernate.validator.fail_fast", "true");
        validatorFactoryBean.setValidationProperties(properties);
        return validatorFactoryBean;
    }
    @Bean
    public MessageSource messageSource(){
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setUseCodeAsDefaultMessage(false);
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setCacheSeconds(60);
        return messageSource;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new EncodeResolverInterceptor());
//        registry.addInterceptor(new SetCharsetInterceptor());
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public FtpUtils ftpUtils(){
        return new FtpUtils();
    }


    @Bean(name = ERROR_PAGE_FILTER_NAME)
    public ErrorPageFilter errorPageFilter(ErrorProperties errorProperties){
        ErrorPage defaultErrorPage = new ErrorPage(errorProperties.getPath());
        ErrorPageFilter errorPageFilter = new ErrorPageFilter();
        errorPageFilter.addErrorPages(defaultErrorPage);
        return errorPageFilter;
    }

    @Configuration
    static class ErrorConfig {

        @Value("${error.path:/error")
        private String path;

        @Value("${error.include-exception}")
        private String includeException;

        @Value("${error.include-stacktrace}")
        private String includeStacktrace;

        @Bean
        public ErrorProperties errorProperties(){
            ErrorProperties errorProperties = new ErrorProperties();
            errorProperties.setPath(path);
            errorProperties.setIncludeException(Boolean.getBoolean(includeException));
            return errorProperties;
        }
        @Bean
        public DefaultErrorAttributes errorAttributes(){
            return new DefaultErrorAttributes(Boolean.getBoolean(includeStacktrace));
        }

        @Bean
        public BasicErrorController basicErrorController(ErrorAttributes errorAttributes, ErrorProperties errorProperties){
            return new BasicErrorController(errorAttributes, errorProperties);
        }

    }
}