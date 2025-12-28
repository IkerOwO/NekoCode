package utils;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Compiler {
    public static void compiler(File file, JTextArea terminal) {
        if (file == null || !file.exists()) {
            terminal.append("File does not exist!\n");
            return;
        }
        String className = file.getName().replaceFirst("[.][^.]+$", "");
        try {
            // COMPILE
            runProcess("javac -cp src " + file.getPath(), terminal);
            terminal.append("**********\n");
            // RUN
            runProcess("java -cp src tests." + className, terminal);
        } catch (Exception e) {
            terminal.append("Error: " + e.getMessage() + "\n");
        }
    }

    private static void runProcess(String command, JTextArea terminal) throws Exception {
        Process process = Runtime.getRuntime().exec(command);

        Thread stdoutThread = new Thread(() -> {
            try {
                printLines(command + " ", process.getInputStream(), terminal);
            } catch (Exception e) {
                terminal.append("Error reading stdout: " + e.getMessage() + "\n");
            }
        });
        Thread stderrThread = new Thread(() -> {
            try {
                printLines(command + " ", process.getErrorStream(), terminal);
            } catch (Exception e) {
                terminal.append("Error reading stderr: " + e.getMessage() + "\n");
            }
        });
        stdoutThread.start();
        stderrThread.start();
        process.waitFor();
        stdoutThread.join();
        stderrThread.join();
        terminal.append(command + " exitValue() " + process.exitValue() + "\n");
    }
    private static void printLines(String prefix, InputStream ins, JTextArea terminal) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(ins));
        String line;
        while ((line = reader.readLine()) != null) {
            terminal.append(prefix + " " + line + "\n");
        }
    }

}
