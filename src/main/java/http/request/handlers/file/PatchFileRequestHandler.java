package http.request.handlers.file;

import http.response.builders.ResponseBuilder;
import com.google.common.hash.Hashing;
import http.response.HTTPStatusCode;
import http.response.Response;
import http.request.Request;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.Charset;

public class PatchFileRequestHandler implements FileRequestHandler {

    @Override
    public Response getResponse(File baseFolder, Request request) {
        try{
            processPatchRequest(request, new File(baseFolder, request.getPath()), request.getBody());
        }catch (ETagMismatchException e){
            return errorResponse();
        }
        return successfulResponse();
    }

    private Response errorResponse() {
        return new ResponseBuilder().withStatusCode(HTTPStatusCode.PRECONDITION_FAILED).build();
    }

    private Response successfulResponse() {
        return new ResponseBuilder().withStatusCode(HTTPStatusCode.NO_CONTENT).build();
    }

    private void processPatchRequest(Request request, File fileToPatch, String patchedContent) {
        if(isETagProvided(request)){
            if(doesETagMatchCurrentFileContent(request, fileToPatch)){
                writePatchedContentToFile(fileToPatch, patchedContent);
            }else{
                throw new ETagMismatchException();
            }
        }else{
            writePatchedContentToFile(fileToPatch, patchedContent);
        }
    }

    private boolean doesETagMatchCurrentFileContent(Request request, File fileToPatch) {
        return request.getIfMatchValue().get().equals(Sha1Hash.generateHash(fileToPatch));
    }

    private boolean isETagProvided(Request request) {
        return request.getIfMatchValue().isPresent();
    }

    private void writePatchedContentToFile(File fileToPatch, String patchedContent) {
        try {
            FileUtils.writeStringToFile(fileToPatch, patchedContent);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public static class Sha1Hash {
        public static String generateHash(String s){
            return Hashing.sha1().hashString(s, Charset.defaultCharset()).toString();
        }

        public static String generateHash(File file) {
            try {
                return generateHash(FileUtils.readFileToString(file));
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        }
    }

    private class ETagMismatchException extends RuntimeException {
    }
}
