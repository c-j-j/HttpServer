package http.request;

import org.apache.commons.lang.StringUtils;

import java.io.*;

public class RequestReader {

    public String readUntilLineBreak(Reader reader) {
        try {
            StringBuilder stringBuilder = new StringBuilder();
            readInto(stringBuilder, reader);
            return stringBuilder.toString();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public String readNumberOfBytes(Reader reader, int numberOfBytes) {

        try {
            char[] bytesToRead = new char[numberOfBytes];
            reader.read(bytesToRead);
            return String.valueOf(bytesToRead);
        } catch (IOException e){
            throw new UncheckedIOException(e);
        }
    }

    private void readInto(StringBuilder stringBuilder, Reader reader) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(reader);
        String nextLine;
        while (StringUtils.isNotEmpty(nextLine = bufferedReader.readLine())) {
            stringBuilder.append(nextLine).append("\n");
        }
    }

}

