package info.kgeorgiy.ja.zaynidinov.walk;

import java.io.BufferedWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashFileVisitor extends SimpleFileVisitor<Path> {
    public BufferedWriter writer;

    public HashFileVisitor(BufferedWriter writer) {
        this.writer = writer;
    }

    public static final String invalidHashCode = "0000000000000000000000000000000000000000";

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
            throws IOException {
        try {
            generateHashCode(file.toString(), writer);
        } catch (IOException e) {
            writer.write(invalidHashCode + " " + file);
            writer.newLine();
        }
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc)
            throws IOException {
        writer.write(invalidHashCode + " " + file.toString());
        writer.newLine();
        return FileVisitResult.CONTINUE;
    }

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
        return invalidHashCode;
    }

    public void generateHashCode(String currentLine, BufferedWriter writer) throws IOException {
        Path path;
        String hashCode = invalidHashCode;
        try {
            path = Path.of(currentLine);
            if (Files.isDirectory(path)) {
                Files.walkFileTree(path, this);
            } else {
                byte[] bytes = Files.readAllBytes(path);
                hashCode = generateHashCodeSha1(bytes);
            }
        } catch (InvalidPathException | FileSystemNotFoundException | IOException ignored) {
            hashCode = invalidHashCode;
        }
        try {
            path = Path.of(currentLine);
            if (!Files.isDirectory(path)) {
                writer.write(hashCode + " " + currentLine);
                writer.newLine();
            }
        } catch (InvalidPathException ignored) {
            writer.write(invalidHashCode + " " + currentLine);
            writer.newLine();
        }
    }
}