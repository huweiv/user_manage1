package com.huweiv.exception;

import com.sun.org.apache.bcel.internal.classfile.ClassFormatException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author HUWEIV
 * @version 1.0.0
 * @ClassName resolveException
 * @Description TODO
 * @CreateTime 2022/4/10 10:15
 */
public class ResolveException implements HandlerExceptionResolver {
    /**
     * @title resolveException
     * @description
     * @author HUWEIV
     * @date 2022/3/22 20:30
     * @return org.springframework.web.servlet.ModelAndView
     */
    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        ModelAndView modelAndView = new ModelAndView();

        if (e instanceof ClassCastException)
            modelAndView.addObject("info", "类转换异常");
        else if (e instanceof ClassFormatException)
            modelAndView.addObject("info", "test");

        modelAndView.setViewName("/pages/error/500.jsp");
        return modelAndView;
    }
}
