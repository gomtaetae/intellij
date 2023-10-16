package com.kosa.ShareTour.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PostController {

    @GetMapping(value = "/post")
    public String postPage(Model model){
        return "posts/post";
    }

    @GetMapping(value = "/view")
    public String viewPage(Model model){
        return "posts/view";
    }

    @GetMapping(value = "/write")
    public String writePage(Model model){
        return "posts/write";
    }
}