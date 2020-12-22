package com.cheese.web.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.Collection;
import java.util.Scanner;

public class EncodeResolverInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(request.getContentType() == null)
            return true;
        Collection<Part> parts = request.getParts();
//        System.out.println(part.getContentType());
//        System.out.println(new Scanner(parts.get(0).getInputStream()).nextLine());
        parts.stream().map(part -> {
            try {
                return new Scanner(part.getInputStream()).nextLine();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }).forEach(System.out::println);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
