package http.request;

import org.junit.Before;
import org.junit.Test;

import java.io.StringReader;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestReaderTest {

    private RequestReader requestReader;

    @Before
    public void setUp() throws Exception {
        requestReader = new RequestReader();
    }

    @Test
    public void readsUntilEmptyLine(){
        String readString = requestReader.readUntilLineBreak(new StringReader("A\nB\n\nC"));
        assertThat(readString).isEqualTo("A\nB\n");
    }

    @Test
    public void readsNumberOfBytes(){
        String dataToBeRead = "DataToBeRead";
        String readData = requestReader.readNumberOfBytes(new StringReader(dataToBeRead + ":IrrelevantData"), dataToBeRead.getBytes().length);
        assertThat(readData).isEqualTo(dataToBeRead);
    }

    @Test
    public void readsZeroBytes(){
        String readData = requestReader.readNumberOfBytes(new StringReader("Data"), 0);
        assertThat(readData).isEqualTo("");
    }

}