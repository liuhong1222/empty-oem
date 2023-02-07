package com.cc.oem.common.xss;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * XSS过滤
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-04-01 10:20
 */
public class XssFilter implements Filter {

    private String[] excludedUris;

    @Override
    public void init(FilterConfig config) throws ServletException {
        excludedUris = config.getInitParameter("excludedUri").split(",");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest hsRequest = (HttpServletRequest) request;
        String uri = hsRequest.getServletPath();
        if (isExcludedUri(uri)) {
            chain.doFilter(request, response);
        } else {
            XssHttpServletRequestWrapper xssRequest = new XssHttpServletRequestWrapper(
                    (HttpServletRequest) request);
            chain.doFilter(xssRequest, response);
        }
    }

    @Override
    public void destroy() {
    }


    /**
     * 判断是否为无需过滤的路径
     * @param uri
     * @return
     */
    private boolean isExcludedUri(String uri) {
        if (excludedUris == null || excludedUris.length <= 0) {
            return false;
        }
        for (String ex : excludedUris) {
            uri = uri.trim();
            ex = ex.trim();
            if (uri.toLowerCase().matches(ex.toLowerCase().replace("*", ".*"))) {
                return true;
            }
        }
        return false;
    }


}
