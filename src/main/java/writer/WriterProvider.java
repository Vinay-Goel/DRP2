package writer;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;

public class WriterProvider {
    public static PrintWriter getPrintWriter(Path filePath) {
        try {
            return new PrintWriter(new BufferedWriter(new FileWriter(filePath.toString())));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
