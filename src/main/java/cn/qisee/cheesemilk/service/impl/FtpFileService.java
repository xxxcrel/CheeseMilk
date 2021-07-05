package cn.qisee.cheesemilk.service.impl;

import cn.qisee.cheesemilk.repository.UserRepository;
import cn.qisee.cheesemilk.service.FileService;
import cn.qisee.cheesemilk.util.FtpUtils;
import io.jsonwebtoken.lang.Assert;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Service("ftpFileService")
@PropertySource("classpath:ftp.properties")
public class FtpFileService implements FileService {
    private final Log logger = LogFactory.getLog(getClass());

    @Autowired
    UserRepository userRepository;

    @Autowired
    private FtpUtils ftpUtils;

    @Override
    public boolean uploadFile(String path, String fileName, MultipartFile file) {
        Assert.notNull(path, "path cannot be null");
        Assert.notNull(file, "file cannot be null");

        boolean result = false;
        InputStream in = null;
        FTPClient ftpClient = ftpUtils.getFTPClient();
        if (ftpClient == null) {
            throw new UploadFailureException("can not get FTP client");
        }
        logger.info("start upload avatar");

        //start upload avatar.........
        try {

            if (!ftpClient.changeWorkingDirectory(path)) {
                if (path.contains("/")) {
                    logger.info("no such dir, and you can't create a nested path");
                    return false;
                }
                ftpClient.makeDirectory(path);
                ftpClient.changeWorkingDirectory(path);
            }
            logger.info("current ftp server working dir: " + ftpClient.printWorkingDirectory());

            in = file.getInputStream();

            boolean done = ftpClient.storeUniqueFile(fileName, in);

            if (done) {
                logger.info("upload avatar: " + fileName + " successful");
                result = true;
            }
            else{
                logger.info(ftpClient.getStatus());
                logger.info("upload file: " + fileName + " fail");
            }

        } catch (IOException e) {
            e.printStackTrace();
            throw new UploadFailureException(e);
        } finally {
            ftpUtils.close(in, null, ftpClient);
        }

        return result;
    }


}
