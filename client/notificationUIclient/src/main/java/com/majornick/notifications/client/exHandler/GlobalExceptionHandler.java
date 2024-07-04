package com.majornick.notifications.client.exHandler;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.nio.file.AccessDeniedException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({HttpClientErrorException.Forbidden.class, HttpClientErrorException.BadRequest.class})
    public String handleHttpClientErrorException(Exception ex, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("message", "Username or password is not correct");
        return "redirect:/login";
    }


    @ExceptionHandler(AccessDeniedException.class)
    public String handleAccessDeniedException(HttpServletRequest request, AccessDeniedException ex, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("message", "Username or password is not correct");
        return "redirect:/login";
    }

    @ExceptionHandler(Exception.class)
    public String handleForbiddenException(Exception ex, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("message", ex.getMessage());
        return "redirect:/error-page";
    }
}
