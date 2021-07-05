package cn.qisee.cheesemilk.security;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

public class ExtAuthenticationDetailsBuilder extends WebAuthenticationDetailsSource {
    @Override
    public WebAuthenticationDetails buildDetails(HttpServletRequest context) {
       return new ExtAuthenticationDetails(context);
    }
}
