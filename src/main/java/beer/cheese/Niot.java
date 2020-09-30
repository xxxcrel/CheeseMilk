package beer.cheese;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class Niot {

    public static void main(String[] args) {
        SpringApplication.run(Niot.class, args);
    }
}
