package com.inf.Medical.Records.System.web.view;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
public class IndexController {

    @GetMapping("/")
    public String getIndex(Model model) {
        final String welcomeMessage = "Welcome to Medical Records System";
        model.addAttribute("welcome", welcomeMessage);
        return "index";
    }
}
