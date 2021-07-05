package cn.qisee.cheesemilk.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class StreamUtils {

    public static int DEFAULT_COPY_BUFFER_SIZE = 1024;

    public static long copyStream(InputStream source, OutputStream dest, int bufferSize) throws CopyStreamException {
        int numBytes;
        long total = 0;
        byte[] buffer = new byte[bufferSize > 0 ? bufferSize : DEFAULT_COPY_BUFFER_SIZE];

        try
        {
            while ((numBytes = source.read(buffer)) != -1)
            {
                // Technically, some read(byte[]) methods may return 0 and we cannot
                // accept that as an indication of EOF.

                if (numBytes == 0)
                {
                    int singleByte = source.read();
                    if (singleByte < 0) {
                        break;
                    }
                    dest.write(singleByte);

                    ++total;

                    continue;
                }

                dest.write(buffer, 0, numBytes);

                total += numBytes;

            }
        }
        catch (IOException e)
        {
            throw new CopyStreamException("IOException caught while copying.",
                    total, e);
        }

        return total;
    }
}
