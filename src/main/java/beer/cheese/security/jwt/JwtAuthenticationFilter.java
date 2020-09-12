package beer.cheese.security.jwt;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Order(Ordered.LOWEST_PRECEDENCE - 3)
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    public final Log logger = LogFactory.getLog(getClass());

    @Autowired
    private AuthenticationManager authenticationManager;

    private AuthenticationEntryPoint authenticationEntryPoint = new JwtAuthenticationEntryPoint();

    private JwtAuthenticationResolver jwtAuthenticationResolver = new JwtAuthenticationResolver();

    protected AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource = new WebAuthenticationDetailsSource();

    public JwtAuthenticationFilter() {
        super();
    }


    public AuthenticationManager getAuthenticationManager() {
        return authenticationManager;
    }

    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    public AuthenticationEntryPoint getAuthenticationEntryPoint() {
        return authenticationEntryPoint;
    }

    public void setAuthenticationEntryPoint(AuthenticationEntryPoint authenticationEntryPoint) {
        this.authenticationEntryPoint = authenticationEntryPoint;
    }

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        final boolean debug = this.logger.isDebugEnabled();

        try {
            JwtAuthenticationToken authentication = jwtAuthenticationResolver.resolve(request);
            logger.info("authentication: " + (authentication == null ? "null" : "find") + " path: " + request.getServletPath());
            if (authentication == null) {
                chain.doFilter(request, response);
                return;
            }

            //tting request details eg: remote address..
           setDetails(request, authentication);

            Authentication authResult = this.authenticationManager
                    .authenticate(authentication);

            if (debug) {
                this.logger.debug("Authentication success: " + authResult);
            }

            SecurityContextHolder.getContext().setAuthentication(authResult);

        } catch (AuthenticationException failed) {
            SecurityContextHolder.clearContext();

            if (debug) {
                this.logger.debug("Authentication request for failed: " + failed);
            }
            this.authenticationEntryPoint.commence(request, response, failed);

            return;
        }
        chain.doFilter(request, response);
    }


    protected void setDetails(HttpServletRequest request,
                              JwtAuthenticationToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }

}
