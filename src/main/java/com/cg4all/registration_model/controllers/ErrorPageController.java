package com.cg4all.registration_model.controllers;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
public class ErrorPageController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {

        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        int statusCode = 500;
        String errorMessage = "Something went wrong";

        if (status != null) {

            statusCode = Integer.parseInt(status.toString());

            switch (statusCode) {
                case 404:
                    errorMessage = "Page Not Found";
                    break;

                case 403:
                    errorMessage = "Access Denied";
                    break;

                case 500:
                    errorMessage = "Internal Server Error";
                    break;

                default:
                    errorMessage = "Unexpected Error";
            }
        }

        model.addAttribute("statusCode", statusCode);
        model.addAttribute("errorMessage", errorMessage);

        return "error";
    }
}