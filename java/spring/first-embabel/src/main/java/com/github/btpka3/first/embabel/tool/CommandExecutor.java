package com.github.btpka3.first.embabel.tool;

import com.github.btpka3.first.embabel.config.FirstEmbabelProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommandExecutor {

    private final FirstEmbabelProperties properties;

    public String execute(String command) {
        List<String> tokens = tokenize(command);
        if (tokens.isEmpty()) {
            throw new IllegalArgumentException("Empty command");
        }
        if (!isWhitelisted(tokens)) {
            throw new SecurityException("Command not in whitelist: " + command);
        }

        ProcessBuilder pb = new ProcessBuilder(tokens);
        pb.redirectErrorStream(true);
        pb.environment().putAll(properties.getCommands().getEnv());

        try {
            Process process = pb.start();
            String output;
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8))) {
                output = reader.lines().collect(Collectors.joining("\n"));
            }
            int exit = process.waitFor();
            if (exit != 0) {
                throw new RuntimeException("Command failed with exit " + exit + ": " + output);
            }
            return output;
        } catch (IOException | InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Failed to execute command: " + command, e);
        }
    }

    private boolean isWhitelisted(List<String> tokens) {
        List<String> white = properties.getCommands().getWhitelist();
        if (white == null || white.isEmpty()) {
            return false;
        }
        for (String entry : white) {
            List<String> entryTokens = tokenize(entry);
            if (isPrefix(entryTokens, tokens)) {
                return true;
            }
        }
        return false;
    }

    private boolean isPrefix(List<String> prefix, List<String> full) {
        if (prefix.size() > full.size()) {
            return false;
        }
        for (int i = 0; i < prefix.size(); i++) {
            if (!prefix.get(i).equals(full.get(i))) {
                return false;
            }
        }
        return true;
    }

    private List<String> tokenize(String command) {
        List<String> tokens = new ArrayList<>();
        StringBuilder current = new StringBuilder();
        boolean inQuotes = false;
        for (char c : command.toCharArray()) {
            if (c == '"') {
                inQuotes = !inQuotes;
            } else if (Character.isWhitespace(c) && !inQuotes) {
                if (!current.isEmpty()) {
                    tokens.add(current.toString());
                    current.setLength(0);
                }
            } else {
                current.append(c);
            }
        }
        if (!current.isEmpty()) {
            tokens.add(current.toString());
        }
        return tokens;
    }
}
