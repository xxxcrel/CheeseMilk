package com.cheese.web.servlet;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CustomDispatcherServlet extends DispatcherServlet {


    //设置是否抛出异常，true:throw exception and custom handler exception :false:default
    public CustomDispatcherServlet(WebApplicationContext wac, boolean throwExceptionIfNoHandlerFound){
        super(wac);
        setThrowExceptionIfNoHandlerFound(throwExceptionIfNoHandlerFound);
    }
    //solution2:继承该方法直接改写
    //todo:second way to resolve noHandlerFound error occur
    @Override
    protected void noHandlerFound(HttpServletRequest request, HttpServletResponse response) throws Exception {
        super.noHandlerFound(request, response);
    }

}
