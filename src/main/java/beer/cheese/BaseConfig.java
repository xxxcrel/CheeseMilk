package beer.cheese;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class BaseConfig {

    @Bean
    public RedisSerializer springSessionDefaultRedisSerializer(){
        ObjectMapper mapper = new ObjectMapper();
        return new GenericJackson2JsonRedisSerializer(mapper);
    }
}
