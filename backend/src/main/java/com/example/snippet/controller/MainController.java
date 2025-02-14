package com.example.snippet.controller;

import com.example.snippet.service.MainService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class MainController {

    private final MainService mainService;

    public MainController(MainService mainService) {
        this.mainService = mainService;
    }

    @PostMapping("/compile")
    public ResponseEntity<Map<String, Object>> compileCode(@RequestBody Map<String, String> request) {
        String code = request.get("code");
        return mainService.compileCode(code);
    }

    @PostMapping("/execute")
    public ResponseEntity<Map<String, Object>> executeCode(@RequestBody Map<String, String> request) {
        String code = request.get("code");
        return mainService.runCode(code);
    }
}
