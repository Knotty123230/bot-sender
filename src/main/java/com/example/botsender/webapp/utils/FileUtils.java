package com.example.botsender.webapp.utils;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

import static com.example.botsender.webapp.constants.FileConstants.FILEPATH;

@Component
public class FileUtils {

    public void saveFile(MultipartFile file) throws IOException {
        if (file != null) {
            String originalFilename = file.getOriginalFilename();
            File dest = new File(FILEPATH + originalFilename);
            file.transferTo(dest);
        }
    }
}


