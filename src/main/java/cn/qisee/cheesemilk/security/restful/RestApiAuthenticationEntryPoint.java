package cn.qisee.cheesemilk.security.restful;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RestApiAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final Log logger = LogFactory.getLog(getClass());
    private String realmName = "Bearer";

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        logger.info("authenticate failure, provider: jwtProvider");
//        response.setStatus(HttpStatus.UNAUTHORIZED.value());//
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
    }

    public String getRealmName() {
        return realmName;
    }

    public void setRealmName(String realmName) {
        this.realmName = realmName;
    }
}
