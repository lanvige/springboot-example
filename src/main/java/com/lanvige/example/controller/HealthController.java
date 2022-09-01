package com.lanvige.example.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping(value = "/v1")
public class HealthController {

    // health api
    @RequestMapping(method = RequestMethod.GET, path = "/health")
    public Object health(HttpServletRequest request) {
        log.debug("");
        return "";
    }
}
