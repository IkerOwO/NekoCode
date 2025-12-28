package utils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class CreateFileClass {
    // DIR WHERE THE FILE WILL BE CREATED, CAN BE CHANGED LATER
    private static final String BASE_DIR = "src/tests/";

    // CREATE FILE
    public static File createFile(String fileName) {
        if (fileName == null || fileName.trim().isEmpty()) {
            throw new IllegalArgumentException("File name cannot be empty");
        }
        try {
            // IF THE FILENAME ENDS WITH .java, TRIM THE EXTENSION
            if (!fileName.endsWith(".java")) {
                fileName = fileName.trim() + ".java";
            }
            File file = new File(BASE_DIR + fileName);
            // IN CASE THE FOLDER DOESN'T EXIST
            File parentDir = file.getParentFile();
            if (!parentDir.exists()) {
                parentDir.mkdirs();
            }
            if (!file.exists()) {
                file.createNewFile();
                System.out.println("File created: " + file.getAbsolutePath());
            } else {
                System.out.println("File already exists: " + file.getAbsolutePath());
            }
            return file;
        } catch (IOException e) {
            throw new RuntimeException("Error creating file: " + e.getMessage(), e);
        }
    }

    // WRITE CODE INTO FILE
    public static void writeToFile(File file, String code) {
        if (file == null) {
            throw new IllegalArgumentException("File cannot be null");
        }
        // IF THERE ISN'T ANY CODE WRITTEN ON THE TEXTAREA
        if (code == null) code = "";
        // WRITE THE CODE
        try {
            Files.writeString(
                    file.toPath(),
                    code,
                    StandardCharsets.UTF_8
            );
            System.out.println("Content written successfully to " + file.getAbsolutePath());
        } catch (IOException e) {
            throw new RuntimeException("Error writing to file: " + e.getMessage(), e);
        }
    }

    // IN CASE FILE EXISTS, READ FROM IT
    public static String readFile(File file) {
        try {
            return Files.readString(file.toPath(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Error reading file: "+e);
        }
    }
}
