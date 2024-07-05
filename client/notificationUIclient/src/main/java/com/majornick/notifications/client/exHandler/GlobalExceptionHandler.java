package com.majornick.notifications.client.exHandler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({HttpClientErrorException.Forbidden.class, HttpClientErrorException.BadRequest.class})
    public String handleHttpClientErrorException(Exception ex, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("message", "Username or password is not correct");
        return "redirect:/login";
    }


    @ExceptionHandler(Exception.class)
    public String handleForbiddenException(Exception ex, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("message", ex.getMessage());
        return "redirect:/error-page";
    }
}
