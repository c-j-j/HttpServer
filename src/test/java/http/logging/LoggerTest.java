package http.logging;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class LoggerTest {

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();
    private File logFile;


    @Before
    public void setUp() throws IOException {
        logFile = temporaryFolder.newFile();
    }

    @Test
    public void writesToLogFile() throws IOException {
        String logMessage = "This should be logged.";
        new Logger(logFile).accept(logMessage);

        assertThat(FileUtils.readFileToString(logFile)).isEqualTo(String.format("%s\n", logMessage));
    }
}