package com.cc.oem.common.filter;


import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

/**
 * copyright (C), 2018-2018, 创蓝253
 * fileName UrlFilter
 * author   zhangx
 * date     2018/6/29 13:50
 * description
 */
@Order(0)
@WebFilter(filterName = "urlFilter", urlPatterns = "/*")
public class UrlFilter implements Filter {

    static Logger logger = LoggerFactory.getLogger(UrlFilter.class);


    ServletContext context;

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filter) throws IOException, ServletException {
        if (true) {
            doFilterForDev(request, response, filter);
        } else {
            filter.doFilter(request, response);
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        context = filterConfig.getServletContext();
    }


    public void doFilterForDev(ServletRequest request, ServletResponse response, FilterChain filter) throws IOException, ServletException {
        long start = System.currentTimeMillis();
        HttpServletRequest r = (HttpServletRequest) request;
        String path = r.getQueryString();
        String url = r.getRequestURI();
        RequestWrapper requestWrapper = null;

        String repalceUrl = url.replaceAll("/", "");
        if (null != repalceUrl) {
            repalceUrl = repalceUrl.trim();
        } else {
            return;
        }
        logger.info("\n\n-----------------  url:" + url + " & queryString:" + path);


        if (url != null && !(url.toLowerCase().contains("export") || url.toLowerCase().contains("image") || url.toLowerCase().contains("download")
                || url.toLowerCase().contains("upload")||url.contains("."))) {
            ResponseWrapper responseWrapper = new ResponseWrapper((HttpServletResponse) response);

            if (request instanceof HttpServletRequest) {
                requestWrapper = new RequestWrapper((HttpServletRequest) request);

                try {
                    Map map = request.getParameterMap();
                    logger.info("map:" + JSON.toJSONString(map));
                    BufferedReader bufferedReader = requestWrapper.getReader();
                    String line;
                    StringBuilder sb = new StringBuilder();
                    while ((line = bufferedReader.readLine()) != null) {
                        sb.append(line);
                    }
                    logger.info("request.getReader:" + sb.toString());
                    logger.info("token:"+requestWrapper.getHeader("token"));
                } catch (Exception e) {
                    logger.error(" doFilter :", e);
                }

            }
            if (null == requestWrapper) {
                filter.doFilter(request, response);
            } else {
                filter.doFilter(requestWrapper, responseWrapper);
            }

            // request.getServletContext().
            // if (null != path && path.indexOf("_wadl") > -1
            // || repalceUrl.length() == 0 ) {
            // return;
            // } else {
            // filter.doFilter(request, response);
            // }
            // StatusExposingServletResponse response = new StatusExposingServletResponse((HttpServletResponse)res);

            String result = new String(responseWrapper.getResponseData());

            response.setContentLength(-1);//解决可能在运行的过程中页面只输出一部分
//        response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            out.write(result);
            out.flush();
            out.close();
        } else {
            filter.doFilter(request, response);
        }
        long end = System.currentTimeMillis();
        logger.info("\n----------------  end url:" + url + " httpstatus:" + ((HttpServletResponse) response).getStatus() + ",request ip :" + request.getRemoteAddr() + ",cost:" + (end - start));

    }

}
