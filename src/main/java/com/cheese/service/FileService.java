package com.cheese.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;


public interface FileService {

    public boolean uploadFile(String path, String fileName, MultipartFile file);

    public default boolean uploadFiles(String path, Map<String, MultipartFile> namedFiles){
        AtomicInteger count = new AtomicInteger();
        namedFiles.forEach((filename, file) -> {
            if (uploadFile(path, filename, file))
                count.getAndIncrement();
        });
        return count.get() == namedFiles.size();
    }


}
