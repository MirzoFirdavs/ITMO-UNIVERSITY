package info.kgeorgiy.ja.zaynidinov.walk;

import java.io.BufferedWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashFileVisitor extends SimpleFileVisitor<Path> {
    private static final String INVALID_HASH_CODE = "0000000000000000000000000000000000000000";
    public BufferedWriter writer;

    public HashFileVisitor(BufferedWriter writer) {
        this.writer = writer;
    }

    private void safeWriteLine(String hashCode, String fileName) {
        try {
            writer.write(hashCode + " " + fileName);
            writer.newLine();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    private void safeWriteInvalidLine(String fileName) {
        safeWriteLine(INVALID_HASH_CODE, fileName);
    }

    private void safeWriteInvalidLine(Path path) {
        safeWriteLine(INVALID_HASH_CODE, path.toString());
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
        generateHashCode(file.toString());

        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) {
        safeWriteInvalidLine(file);

        return FileVisitResult.CONTINUE;
    }

    private static String generateHashCodeSha1(byte[] input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] messageDigest = md.digest(input);
            StringBuilder hashCodeResult = new StringBuilder(
                    new BigInteger(1, messageDigest)
                            .toString(16)
            );

            while (hashCodeResult.length() < 40) {
                hashCodeResult.insert(0, "0");
            }

            return hashCodeResult.toString();
        } catch (NoSuchAlgorithmException ignored) {
            // no actions
        }

        return INVALID_HASH_CODE;
    }

    protected void generateHashCode(String currentLine) {
        try {
            // if path were autocloseable, we could use here try with resources
            Path path = Path.of(currentLine);

            if (Files.isDirectory(path)) {
                Files.walkFileTree(path, this);
            } else {
                String hashCode = generateHashCodeSha1(Files.readAllBytes(path));
                safeWriteLine(hashCode, currentLine);
            }
        } catch (InvalidPathException | FileSystemNotFoundException | IOException e) {
            System.err.println(e.getMessage());

            safeWriteInvalidLine(currentLine);
        }
    }
}
