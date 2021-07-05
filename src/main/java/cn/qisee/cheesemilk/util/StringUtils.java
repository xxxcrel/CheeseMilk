package cn.qisee.cheesemilk.util;

import java.time.LocalTime;

public class StringUtils {
    public static String generateFileName(String identifier, String originalFilename){
        String filenameSuffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        return identifier + LocalTime.now().toNanoOfDay() + filenameSuffix;
    }
}
