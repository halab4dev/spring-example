package com.github.halab4dev;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author halab
 */
@RestController
@RequestMapping("")
public class HomeController {

    @GetMapping
    public String home() {
        return "Hello, world!";
    }
}
