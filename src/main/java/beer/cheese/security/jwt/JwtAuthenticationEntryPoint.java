package beer.cheese.security.jwt;

import beer.cheese.view.Result;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final Log logger = LogFactory.getLog(getClass());
    private String realmName = "Bearer";

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        logger.info("authenticate failure, provider: jwtProvider");
        response.addHeader("WWW-Authenticate", "Basic realm=\"" + realmName + "\"");
        ObjectMapper mapper = new ObjectMapper();
//        response.setStatus(HttpStatus.UNAUTHORIZED.value());//
        mapper.writerWithDefaultPrettyPrinter().writeValue(response.getWriter(), new Result<>("Requires Authentication"));
    }

    public String getRealmName() {
        return realmName;
    }

    public void setRealmName(String realmName) {
        this.realmName = realmName;
    }
}
