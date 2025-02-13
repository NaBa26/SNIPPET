package com.example.snippet.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.snippet.service.MainService;



@RestController 
@RequestMapping("/api")
public class MainController {
	
	@Autowired
	private final MainService mainService;

	    public MainController(MainService mainService) {
	    	this.mainService = mainService;
		}
	    
	    @PostMapping("/execute")
	    public ResponseEntity<?> executeCode(@RequestBody Map<String, String> request) {
	        String code = request.get("code");

	        ResponseEntity<?> compileResponse = mainService.compileCode(code);
	        if (!compileResponse.getStatusCode().is2xxSuccessful()) {
	            return compileResponse; // Return errors if compilation fails
	        }

	        ResponseEntity<?> runResponse = mainService.runCode(code);
	        return runResponse;
	    }


}
