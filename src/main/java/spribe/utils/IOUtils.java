package spribe.utils;

import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

@Log4j2
public class IOUtils {

    public static void createFile(Path path) {
        try {
            path.toFile().createNewFile();
        } catch (IOException e) {
            log.error("Error creating file. Path: {}\nStacktrace: {}", path, e.getStackTrace());
            throw new RuntimeException(e);
        }
    }

    public static void write(String path, String val) {
        try (PrintWriter writer = new PrintWriter(path, StandardCharsets.UTF_8)) {
            writer.print(val);
        } catch (IOException e) {
            log.error("Cannot write to file '{}'. Stacktrace: {}", path, e.getStackTrace());
            throw new RuntimeException(e);
        }
    }

    public static void cleanFile(String path) {
        write(path, "");
    }

    public static boolean exists(Path path) {
        return path.toFile().exists();
    }
}
