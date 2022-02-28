package info.kgeorgiy.ja.zaynidinov.walk;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;

public class RecursiveWalk {
    private static boolean areArgsInvalid(String[] args) {
        return args == null || args.length != 2 || args[0] == null || args[1] == null;
    }

    private static boolean areDirectoriesCreated(Path file) {
        try {
            Files.createDirectories(file.getParent());
        } catch (IOException e) {
            System.err.println("Can not create parent folders for output file '" + file + "': " + e.getMessage());
            return false;
        }

        return true;
    }

    public static void main(String[] args) {
        if (areArgsInvalid(args)) {
            System.err.println("Invalid arguments!");
            System.err.println("Usage: ./walker <input_file_name> <output_file_name>");
            return;
        }

        Path inputFile;
        Path outputFile;

        try {
            inputFile = Path.of(args[0]);
            outputFile = Path.of(args[1]);
        } catch (InvalidPathException e) {
            System.err.println("Invalid path value " + e.getMessage());
            return;
        }

        if (outputFile.getParent() != null && !areDirectoriesCreated(outputFile)) {
            return;
        }

        try (BufferedReader reader = Files.newBufferedReader(inputFile, StandardCharsets.UTF_8)) {
            try (BufferedWriter writer = Files.newBufferedWriter(outputFile, StandardCharsets.UTF_8)) {
                HashFileVisitor hashVisitor = new HashFileVisitor(writer);
                String currentLine;

                while ((currentLine = reader.readLine()) != null) {
                    hashVisitor.generateHashCode(currentLine);
                }
            } catch (IOException e) {
                System.err.println("Problems while processing output file: " + e.getMessage());
            }
        } catch (IOException e) {
            System.err.println("Problems while processing input file: " + e.getMessage());
        }
    }
}
