package http.response;

import builders.ResponseBuilder;
import com.google.common.io.ByteSource;
import com.google.common.io.Files;
import http.ContentType;
import http.HTTPStatusCode;
import http.RequestHeader;
import http.Response;
import org.apache.commons.io.FilenameUtils;

import java.io.File;

public class GetFileContentResponseResolver implements ResponseResolver {

    @Override
    public Response getResponse(File baseFolder, RequestHeader requestHeader) {
        File requestedFile = getRequestedFile(baseFolder, requestHeader);
        return new ResponseBuilder()
                .withStatusCode(HTTPStatusCode.OK)
                .withContentType(determineContentType(requestedFile))
                .withContentLength(requestedFile.length())
                .withContent(readFile(requestedFile))
                .build();
    }

    private ContentType determineContentType(File file) {
        String extension = FilenameUtils.getExtension(file.getAbsolutePath());
        if(extension.equalsIgnoreCase("JPEG")){
            return ContentType.JPEG;
        }else{
            return ContentType.PLAIN;
        }
    }

    private ByteSource readFile(File requestedFile) {
        return Files.asByteSource(requestedFile);
    }

    private File getRequestedFile(File baseFolder, RequestHeader requestHeader) {
        return new File(baseFolder, requestHeader.getPath());
    }

}
