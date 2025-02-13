package com.example.snippet.service;

import org.springframework.http.ResponseEntity;

public interface MainService {
	public ResponseEntity<?> compileCode(String code);
	public ResponseEntity<?> runCode(String code);
}
