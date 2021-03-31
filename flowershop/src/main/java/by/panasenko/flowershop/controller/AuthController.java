package by.panasenko.flowershop.controller;

import by.panasenko.flowershop.util.PagePath;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {
    @GetMapping("/")
    public String index(){
        return PagePath.INDEX;
    }

    @GetMapping("/direction")
    public String direction() {
        return PagePath.DIRECTION;
    }
}
