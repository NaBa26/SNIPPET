package com.example.snippet.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;

public interface MainService {
	public ResponseEntity<Map<String, Object>> compileCode(String code);
	public ResponseEntity<Map<String, Object>> runCode(String code);
}
