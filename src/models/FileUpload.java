package models;

import java.io.InputStream;

public class FileUpload {
    private String fieldName;
    private String originalFileName;
    private String contentType;
    private InputStream inputStream;
    private long size;

    public FileUpload(String fieldName, String originalFileName, String contentType, InputStream inputStream, long size) {
        this.fieldName = fieldName;
        this.originalFileName = originalFileName;
        this.contentType = contentType;
        this.inputStream = inputStream;
        this.size = size;
    }

    public String getOriginalFileName() { return originalFileName; }
    public InputStream getInputStream() { return inputStream; }
    public long getSize() { return size; }
}
