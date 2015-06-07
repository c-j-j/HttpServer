package http.request;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestReaderTest {

    private RequestReader requestReader;

    @Before
    public void setUp() throws Exception {
        requestReader = new RequestReader();
    }

    @Test
    public void readsUntilEmptyLine(){
        String readString = requestReader.readUntilLineBreak(getReaderWithText("A\nB\n\nC"));
        assertThat(readString).isEqualTo("A\nB\n");
    }

    private BufferedReader getReaderWithText(String s) {
        InputStream inputStream = IOUtils.toInputStream(s);
        return new BufferedReader(new InputStreamReader(inputStream));
    }

    @Test
    public void readsNumberOfBytes(){
        String dataToBeRead = "DataToBeRead";
        String readData = requestReader.readNumberOfBytes(getReaderWithText(dataToBeRead + ":IrrelevantData"), dataToBeRead.getBytes().length);
        assertThat(readData).isEqualTo(dataToBeRead);
    }

    @Test
    public void readsZeroBytes(){
        String readData = requestReader.readNumberOfBytes(getReaderWithText("Data"), 0);
        assertThat(readData).isEqualTo("");
    }

}