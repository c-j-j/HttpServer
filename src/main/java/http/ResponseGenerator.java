package http;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.Socket;
import java.util.function.Function;

public class ResponseGenerator implements Function<Socket, Response> {

    private final Function<Socket, Request> requestParser;
    private final File baseFolder;

    public ResponseGenerator(Function<Socket, Request> requestParser, File baseFolder) {
        this.requestParser = requestParser;
        this.baseFolder = baseFolder;
    }

    @Override
    public Response apply(Socket socket) {
        try {
            Request request = requestParser.apply(socket);
            return new Response(readFile(new File(baseFolder, request.getPath())));
        }catch(RuntimeException e){
            return new Response("");
        }
    }

    private String readFile(File requestedFile) {
        String responseContents = null;
        try {
            responseContents = getResponseFromFile(requestedFile);
        } catch (IOException e) {
            responseContents = "";
        }
        return responseContents;
    }

    private String getResponseFromFile(File requestedFile) throws IOException {
        return FileUtils.readFileToString(requestedFile);
    }
}
