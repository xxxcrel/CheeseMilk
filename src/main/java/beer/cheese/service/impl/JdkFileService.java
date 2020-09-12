package beer.cheese.service.impl;

import beer.cheese.constant.AppConstants;
import beer.cheese.service.FileService;
import beer.cheese.util.StreamUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Set;

@Service("jdkFileService")
public class JdkFileService implements FileService {
    private final Log logger = LogFactory.getLog(getClass());

    private static volatile boolean initialized = false;

    @PostConstruct
    protected void init(){
        String basePath = AppConstants.LOCAL_PATH;
        String mkdirPermissions = AppConstants.MKDIR_PERMISSIONS;
        boolean basePathExist = Files.exists(Paths.get(basePath));
        if(!basePathExist){
            logger.info("base path " + basePath +  " doesn't exist, try create");
            try {
                Set<PosixFilePermission> permissions = PosixFilePermissions.fromString(mkdirPermissions);
                FileAttribute<Set<PosixFilePermission>> attribute = PosixFilePermissions.asFileAttribute(permissions);
                Files.createDirectories(Paths.get(basePath), attribute);
            }catch (IOException e){
                if(e instanceof AccessDeniedException){
                    logger.warn("Access denied for create base path:" + basePath + ", you may be running  in a incorrect environment");
                }
                e.printStackTrace();
            }
        }
        initialized = true;
    }
    
    @Override
    public boolean uploadFile(String path, String fileName, MultipartFile file) {
        
        logger.info("path: " + path + ", filename: " + fileName);
        Path imagePath = Paths.get(AppConstants.LOCAL_PATH + path + fileName);
        try (BufferedOutputStream out = new BufferedOutputStream(Files.newOutputStream(imagePath, StandardOpenOption.CREATE_NEW))){
            StreamUtils.copyStream(file.getInputStream(), out, 1024);
        }catch (IOException e){
            logger.info("upload failed");
            e.printStackTrace();
        }
        logger.info("upload successfully");
        return true;
    }

}
