package com.example.snippet.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import javax.tools.ToolProvider;
import javax.tools.JavaCompiler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class MainServiceImpl implements MainService {
    
    public ResponseEntity<?> compileCode(String code) {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        if (compiler == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Java compiler not available");
        }
        
        File sourceFile = new File("Main.java");
        try (FileWriter writer = new FileWriter(sourceFile)) {
            writer.write(code);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error writing source file: " + e.getMessage());
        }
        
        int result = compiler.run(null, null, null, sourceFile.getPath());
        if (result == 0) {
            return ResponseEntity.ok("Compilation successful");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body("Compilation failed with exit code: " + result);
        }
    }
    
    public ResponseEntity<?> runCode(String code) {
        try {
            Process process = new ProcessBuilder("java", "Main")
                              .redirectErrorStream(true)
                              .start();
            
            // Read the output from the process
            StringBuilder output = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    output.append(line).append("\n");
                }
            }
            
            int exitCode = process.waitFor();
            return ResponseEntity.ok(
                Map.of(
                    "output", output.toString(),
                    "exitCode", exitCode
                )
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error executing code: " + e.getMessage());
        }
    }

}
