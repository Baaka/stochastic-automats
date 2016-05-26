package edu.tsu.stochastic.automats.web.shared;

public enum ExportFormat {
    TXT("txt", "application/octet-stream"),
    HTML("html", "text/html"),
    PDF("pdf", "application/pdf"),
    XLS("xls", "application/vnd.ms-excel");

    private final String extension;
    private final String mediaType;

    ExportFormat(String extension, String mediaType) {
        this.extension = extension;
        this.mediaType = mediaType;
    }

    public String getExtension() {
        return extension;
    }

    public String getMediaType() {
        return mediaType;
    }
}
