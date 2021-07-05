package cn.qisee.cheesemilk.security.restful;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Order(Ordered.LOWEST_PRECEDENCE - 3)
public class RestApiAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    public final Log logger = LogFactory.getLog(getClass());

    static final String AUTH_TOKE_NAME = "CHEESE-Auth-Token";

    public RestApiAuthenticationFilter() {
        super(new AntPathRequestMatcher("/api/authenticate"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        if(!"POST".equals(request.getMethod())){
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
        return getAuthenticationManager().authenticate(authRequest);
    }
}
