package http.request;

import org.apache.commons.lang.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UncheckedIOException;

public class RequestReader {

    public String readUntilLineBreak(BufferedReader reader) {
        try {
            StringBuilder stringBuilder = new StringBuilder();
            readInto(stringBuilder, reader);
            return stringBuilder.toString();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public String readNumberOfBytes(BufferedReader reader, int numberOfBytes) {

        try {
            char[] bytesToRead = new char[numberOfBytes];
            reader.read(bytesToRead);
            return String.valueOf(bytesToRead);
        } catch (IOException e){
            throw new UncheckedIOException(e);
        }
    }

    private void readInto(StringBuilder stringBuilder, BufferedReader reader) throws IOException {
        String nextLine;
        while (StringUtils.isNotEmpty(nextLine = reader.readLine())) {
            stringBuilder.append(nextLine).append("\n");
        }
    }

}

