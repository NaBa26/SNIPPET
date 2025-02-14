package com.example.snippet.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;

@Service
public class MainServiceImpl implements MainService {

    private static final String FILE_NAME = "Main.java";

    @Override
    public ResponseEntity<Map<String, Object>> compileCode(String code) {
        Map<String, Object> response = new HashMap<>();

        try {
            Path filePath = Files.write(
                Path.of("Main.java"), 
                code.getBytes(), 
                StandardOpenOption.CREATE, 
                StandardOpenOption.TRUNCATE_EXISTING
            );

            ProcessBuilder processBuilder = new ProcessBuilder("javac", "Main.java");
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();

            InputStream errorStream = process.getErrorStream();
            String errorOutput = new String(errorStream.readAllBytes());

            process.waitFor();

            if (!errorOutput.isEmpty()) {
                response.put("status", "Compilation Error");
                response.put("message", errorOutput);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            response.put("status", "Success");
            response.put("message", "Compilation Successful");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.put("status", "Error");
            response.put("message", "Error during compilation: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }


    @Override
    public ResponseEntity<Map<String, Object>> runCode(String code) {
        Map<String, Object> response = new HashMap<>();

        try {
            ResponseEntity<Map<String, Object>> compilationResponse = compileCode(code);
            if (compilationResponse.getStatusCode() != HttpStatus.OK) {
                return compilationResponse; 
            }

            ProcessBuilder processBuilder = new ProcessBuilder("java", "Main");
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder output = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

            process.waitFor();

            if (process.exitValue() != 0) {
                response.put("status", "Runtime Error");
                response.put("message", output.toString());
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }

            response.put("status", "Success");
            response.put("message", "Execution Successful");
            response.put("output", output.toString());
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.put("status", "Error");
            response.put("message", "Error during execution: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }


	public static String getFileName() {
		return FILE_NAME;
	}

}
