package com.example.Upbeat_websocket.Model;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.ParseException;

public class WriteFile {
    public void Write(String text , Path outputPath) throws IOException {
        if (!Files.isWritable(outputPath)) {
        throw new IOException("Output file is not writable: " + outputPath);
    }
        try (BufferedWriter writer = new BufferedWriter(Files.newBufferedWriter(outputPath, StandardCharsets.UTF_8))) {
        writer.write(text);
        writer.newLine(); // Add a newline character for readability
    }
        System.out.println("Data written successfully to " + outputPath);
    }
}
