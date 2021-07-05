package cn.qisee.cheesemilk.security;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.authentication.WebAuthenticationDetails;

public class ExtAuthenticationDetails extends WebAuthenticationDetails {

    public ExtAuthenticationDetails(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String getRemoteAddress() {
        return super.getRemoteAddress();
    }

    @Override
    public String getSessionId() {
        return super.getSessionId();
    }
}
