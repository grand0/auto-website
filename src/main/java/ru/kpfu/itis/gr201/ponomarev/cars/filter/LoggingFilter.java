package ru.kpfu.itis.gr201.ponomarev.cars.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@WebFilter(filterName = "loggingFilter", urlPatterns = "/*")
public class LoggingFilter implements Filter {

    private ServletContext context;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        context = filterConfig.getServletContext();
        context.log("LoggingFilter initialized");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpReq = (HttpServletRequest) request;
        Map<String, String[]> params = httpReq.getParameterMap();
        if (params != null) {
            String paramString = params.keySet().stream().map(key -> key + "=" + Arrays.toString(params.get(key))).collect(Collectors.joining(", ", "{", "}"));
            context.log(httpReq.getRemoteAddr() + ": req params: " + paramString);
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
