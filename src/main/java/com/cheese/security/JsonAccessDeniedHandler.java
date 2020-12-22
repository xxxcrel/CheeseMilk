package com.cheese.security;

import com.cheese.web.view.Result;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JsonAccessDeniedHandler implements AccessDeniedHandler {
    private final Log logger = LogFactory.getLog(getClass());
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        logger.info("Access Denied, path: " + request.getServletPath());
        ObjectMapper mapper = new ObjectMapper();
        try {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            mapper.writerWithDefaultPrettyPrinter().writeValue(response.getWriter(), new Result<>("Access Denied"));
        }catch ( JsonGenerationException | JsonMappingException e){
            e.printStackTrace();
        }
    }
}
