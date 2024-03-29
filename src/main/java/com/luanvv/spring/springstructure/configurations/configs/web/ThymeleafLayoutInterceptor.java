package com.luanvv.spring.springstructure.configurations.configs.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class ThymeleafLayoutInterceptor extends HandlerInterceptorAdapter {
	private static final String DEFAULT_LAYOUT = "layouts/default";
	private static final String DEFAULT_VIEW_ATTRIBUTE_NAME = "view";

	 @Override
	    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
	        if (modelAndView == null || !modelAndView.hasView()) {
	            return;
	        }
	        String originalViewName = modelAndView.getViewName();
	        if (isRedirectOrForward(originalViewName)) {
	            return;
	        }
	        String layoutName = getLayoutName(handler);
	        modelAndView.setViewName(layoutName);
	        modelAndView.addObject(DEFAULT_VIEW_ATTRIBUTE_NAME, originalViewName);
	    }
	 
	    private boolean isRedirectOrForward(String viewName) {
	        return viewName.startsWith("redirect:") || viewName.startsWith("forward:");
	    }
	 
	    private String getLayoutName(Object handler) {
	        HandlerMethod handlerMethod = (HandlerMethod) handler;
	        Layout layout = handlerMethod.getMethodAnnotation(Layout.class);
	        if (layout == null) {
	            return DEFAULT_LAYOUT;
	        } else {
	            return layout.value();
	        }
	    }
}
