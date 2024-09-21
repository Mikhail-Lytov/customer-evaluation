package com.example.mihail.lytov.configuration.swagger;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/")
@Hidden
public class SwaggerController {
    @GetMapping(value = "/")
    public void root(HttpServletResponse rsp) throws IOException {
        rsp.sendRedirect("swagger-ui/index.html");
    }
}