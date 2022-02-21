package info.kgeorgiy.ja.zaynidinov.walk;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;

public class RecursiveWalk {
    public static void main(String[] args) {
        if (args == null || args.length != 2 || args[0] == null || args[1] == null) {
            System.out.println("Invalid number of arguments");
        } else {
            try {
                Path inputFileName = Path.of(args[0]);
                Path outputFileName = Path.of(args[1]);
                if (outputFileName.getParent() != null) {
                    try {
                        Files.createDirectories(outputFileName.getParent());
                    } catch (IOException e) {
                        System.out.println("Can not create folder for output file: " + e.getMessage());
                        return;
                    }
                }
                try (BufferedReader reader = Files.newBufferedReader(inputFileName, StandardCharsets.UTF_8)) {
                    try (BufferedWriter writer = Files.newBufferedWriter(outputFileName, StandardCharsets.UTF_8)) {
                        HashFileVisitor hashVisitor = new HashFileVisitor(writer);
                        String currentLine;
                        while ((currentLine = reader.readLine()) != null) {
                            hashVisitor.generateHashCode(currentLine, writer);
                        }
                    } catch (IOException e) {
                        System.out.println("Invalid output value " + e.getMessage());
                    }
                } catch (IOException e) {
                    System.out.println("Invalid input value" + e.getMessage());
                }
            } catch (InvalidPathException e) {
                System.out.println("Invalid path value " + e.getMessage());
            }
        }
    }
}