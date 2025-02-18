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
    public ResponseEntity<Map<String, Object>> runCode(String code) {
        Map<String, Object> response = new HashMap<>();

        try {
            Path filePath = Files.write(
                Path.of(FILE_NAME),
                code.getBytes(),
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING
            );

            Process compileProcess = new ProcessBuilder("javac", FILE_NAME).redirectErrorStream(true).start();
            compileProcess.waitFor();

            String compileErrors = new String(compileProcess.getInputStream().readAllBytes());
            if (!compileErrors.isEmpty()) {
                response.put("status", "Compilation Error");
                response.put("message", compileErrors);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            Process executeProcess = new ProcessBuilder("java", "Main").redirectErrorStream(true).start();
            BufferedReader outputReader = new BufferedReader(new InputStreamReader(executeProcess.getInputStream()));
            BufferedReader errorReader = new BufferedReader(new InputStreamReader(executeProcess.getErrorStream()));

            StringBuilder output = new StringBuilder();
            StringBuilder errors = new StringBuilder();
            String line;

            while ((line = outputReader.readLine()) != null) {
                output.append(line).append("\n");
            }
            while ((line = errorReader.readLine()) != null) {
                errors.append(line).append("\n");
            }

            executeProcess.waitFor();

            if (!errors.toString().isEmpty()) {
                response.put("status", "Runtime Error");
                response.put("message", errors.toString());
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
        } finally {
            try {
                Files.deleteIfExists(Path.of(FILE_NAME));
            } catch (IOException ignored) {
            }
        }
    }
}
