package cn.qisee.cheesemilk.service;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    boolean uploadFile(String path, String fileName, MultipartFile file);

    default void uploadFiles(String path, Map<String, MultipartFile> imageMap){
//        boolean result = false;
        imageMap.forEach((key, value) ->{
            uploadFile(path, key, value);
        });
    }
}
