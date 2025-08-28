package com.itai.itaiJavaPlay.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class LayoutModelInterceptor implements HandlerInterceptor {

  @Override
  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
      ModelAndView modelAndView) throws Exception {
    if (modelAndView != null) {
      String requestURI = request.getRequestURI();
      modelAndView.addObject("requestURI", requestURI); // this helps us set the highlight on the menu
    }
  }
}