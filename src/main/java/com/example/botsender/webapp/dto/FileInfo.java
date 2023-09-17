package com.example.botsender.webapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Set;

@Data
public class FileInfo implements Serializable {
    @Serial
    private static final long serialVersionUID = 42L;
    @JsonProperty("message")
    private String message;
    @JsonProperty("fileName")
    private String fileName;
    @JsonProperty("path")
    private String path;
    private Set<String> usernames;

    public FileInfo() {
    }

    public FileInfo(String message, String fileName, String path) {
        this.message = message;
        this.fileName = fileName;
        this.path = path;
    }

    public FileInfo(String message, String fileName, String path, Set<String> chatIds) {
        this.message = message;
        this.fileName = fileName;
        this.path = path;
        this.usernames = chatIds;
    }

    public FileInfo(String fileName, String path) {
        this.fileName = fileName;
        this.path = path;
    }
}
