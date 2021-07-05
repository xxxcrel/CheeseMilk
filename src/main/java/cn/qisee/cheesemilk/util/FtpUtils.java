package cn.qisee.cheesemilk.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@Component
@PropertySource("classpath:ftp.properties")
public class FtpUtils {

    private final Log logger = LogFactory.getLog(getClass());
    @Value("${ftp.address}")
    private String ftpAddress;
    @Value("${ftp.port}")
    private String ftpPort;
    @Value("${ftp.login}")
    private String username;
    @Value("${ftp.password}")
    private String password;

    public FTPClient getFTPClient() {
        FTPClient ftpClient = new FTPClient();
        int reply;
        boolean result = true;
        try {
            ftpClient.connect(ftpAddress, Integer.parseInt(ftpPort));
            if (ftpClient.isConnected()) {
                logger.info("Connected to " + ftpAddress);
                logger.info(ftpClient.getReplyString());
                if(!ftpClient.login(username, password)){
                    logger.info("username:" + username + " login fail");
                    return null;
                }
                ftpClient.setControlEncoding("UTF-8");
                ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            } else {
                result = false;
            }
            if (result) {
                return ftpClient;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void close(InputStream in, OutputStream out, FTPClient ftpClient) {
        if (null != in) {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
                logger.info("InputStream close fail");
            }
        }
        if (null != out) {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
                logger.info("OutputStream close fail");
            }
        }
        if (null != ftpClient) {
            try {
                ftpClient.logout();
                ftpClient.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
                logger.info("ftp client close fail");
            }
        }
    }
}
