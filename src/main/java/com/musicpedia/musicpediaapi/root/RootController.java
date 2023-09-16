package com.musicpedia.musicpediaapi.root;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RootController {
    @GetMapping
    public String healthCheck() {
        return "OK";
    }
}
