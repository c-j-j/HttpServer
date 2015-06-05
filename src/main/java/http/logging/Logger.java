package http.logging;

import java.io.*;
import java.util.function.Consumer;

public class Logger implements Consumer<String> {

    private final File logFile;

    public Logger(File logFile) {
        this.logFile = logFile;
    }

    @Override
    public void accept(String messageToLog) {
        try {
            writeToLog(messageToLog);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private void writeToLog(String messageToLog) throws IOException {
        try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(logFile, true)))) {
            out.println(messageToLog);
        }
    }
}
