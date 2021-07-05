package cn.qisee.cheesemilk;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
@EnableCaching
@EnableRedisHttpSession
public class BaseConfig {

    @Bean
    public RedisSerializer springSessionDefaultRedisSerializer(){
        ObjectMapper mapper = new ObjectMapper();
        return new GenericJackson2JsonRedisSerializer(mapper);
    }
}
