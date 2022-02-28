package info.kgeorgiy.ja.zaynidinov.walk;

import java.io.*;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Walk {
    private static final String invalid = "0000000000000000000000000000000000000000";

    public static String generateHashCodeSha1(byte[] input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] messageDigest = md.digest(input);
            BigInteger no = new BigInteger(1, messageDigest);
            StringBuilder hashCodeResult = new StringBuilder(no.toString(16));
            while (hashCodeResult.length() < 40) {
                hashCodeResult.insert(0, "0");
            }
            return hashCodeResult.toString();
        } catch (NoSuchAlgorithmException ignored) {
        }
        return invalid;
    }

    private static String readFile(String currentLine) {
        Path path;
        try {
            path = Path.of(currentLine);
        } catch (InvalidPathException e) {
            return invalid;
        }
        try {
            byte[] bytes = Files.readAllBytes(path);
            return generateHashCodeSha1(bytes);
        } catch (InvalidPathException | FileSystemNotFoundException | IOException ignored) {
            return invalid;
        }
    }

    public static void main(String[] args) {
        if (args == null || args.length != 2 || args[0] == null || args[1] == null) {
            System.err.println("Invalid number of arguments");
        } else {
            try {
                Path inputFileName = Path.of(args[0]);
                Path outputFileName = Path.of(args[1]);
                if (outputFileName.getParent() != null) {
                    try {
                        Files.createDirectories(outputFileName.getParent());
                    } catch (IOException e) {
                        System.err.println("Can not create folder for output file: " + e.getMessage());
                        return;
                    }
                }
                try (BufferedReader reader = Files.newBufferedReader(inputFileName, StandardCharsets.UTF_8)) {
                    try (BufferedWriter writer = Files.newBufferedWriter(outputFileName, StandardCharsets.UTF_8)) {
                        String currentLine;
                        while ((currentLine = reader.readLine()) != null) {
                            writer.write(readFile(currentLine) + " " + currentLine);
                            writer.newLine();
                        }
                    } catch (IOException e) {
                        System.err.println("Invalid output value " + e.getMessage());
                    }
                } catch (IOException e) {
                    System.err.println("Invalid input value" + e.getMessage());
                }
            } catch (InvalidPathException e) {
                System.err.println("Invalid path value " + e.getMessage());
            }
        }
    }
}