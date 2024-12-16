package library.utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FileUtils extends FileOperations {
    private static final ExecutorService executor = Executors.newFixedThreadPool(2);

    @Override
    public void writeToFile(String filePath, String content) {
        executor.submit(() -> {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                writer.write(content);
                System.out.println("Content written to file successfully on thread: " 
                                   + Thread.currentThread().getName());
            } catch (IOException e) {
                System.err.println("Error writing to file: " + e.getMessage());
            }
        });
    }
}
