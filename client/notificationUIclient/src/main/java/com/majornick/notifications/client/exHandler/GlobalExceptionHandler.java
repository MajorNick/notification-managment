package com.majornick.notifications.client.exHandler;

import com.majornick.notifications.client.exceptions.UnauthorizedException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.nio.file.AccessDeniedException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(HttpClientErrorException.Forbidden.class)
    public ModelAndView handleForbiddenException(HttpServletRequest request, HttpClientErrorException.Forbidden ex, RedirectAttributes redirectAttributes) {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("redirect:/login");
        return modelAndView;
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ModelAndView handleAccessDeniedException(HttpServletRequest request, AccessDeniedException ex, RedirectAttributes redirectAttributes) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/login");
        return modelAndView;
    }
}
