package com.huangTaiQi.www.filter;


import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author 14629
 */
public class BaseFilter implements Filter {
    private static final Logger LOGGER = Logger.getLogger(BaseFilter.class.getName());
    @Override
    public void init(FilterConfig filterConfig) {
        LOGGER.info("BaseFilter initialized");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            boolean isValid = doBeforeProcessing((HttpServletRequest) request, (HttpServletResponse) response, chain);
            if(isValid){
                //放行
                chain.doFilter(request, response);
            }
            doAfterProcessing((HttpServletRequest)request, (HttpServletResponse)response, chain);
        } catch (Throwable t) {
            LOGGER.log(Level.SEVERE, "An exception occurred : " + t.getMessage(), t);
            try {
                throw t;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    protected boolean doBeforeProcessing(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws Exception {
        // 子类可以实现此方法，实现在处理请求前要执行的逻辑
        return true;
    }

    protected void doAfterProcessing(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 子类可以实现此方法，实现在处理请求后要执行的逻辑
    }

    @Override
    public void destroy() {
        LOGGER.info("BaseFilter destroyed");
    }
}

