package com.github.halab4dev;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/*
 *
 * @author halab
 */
@Controller
@RequestMapping("/test")
public class TestController {

    private int count;

    @GetMapping("/hello")
    public String hello() {
        this.count ++;
        return "Hello";
    }
}
