package cn.qisee.cheesemilk.security;

import org.springframework.security.web.util.UrlUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;

public class DebugFilter implements Filter {
    private final Logger logger = new Logger();

    public static final int BUFFER_SIZE = 4096;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        if (!(servletRequest instanceof HttpServletRequest)
                || !(servletResponse instanceof HttpServletResponse)) {
            throw new ServletException("DebugFilter just supports HTTP requests");
        }
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        logger.info("Request received for " + request.getMethod() + " '"
                + UrlUtils.buildRequestUrl(request) + "':\n\n" + request + "\n\n"
                + "servletPath:" + request.getServletPath() + "\n" + "pathInfo:"
                + request.getPathInfo() + "\n\n" + "headers: \n" + formatHeaders(request)
                + "\n" + "request body:\n" + formatBody(request));
        chain.doFilter(servletRequest, servletResponse);
    }

    String formatHeaders(HttpServletRequest request) {
        StringBuilder sb = new StringBuilder();
        Enumeration<String> eHeaderNames = request.getHeaderNames();
        while (eHeaderNames.hasMoreElements()) {
            String headerName = eHeaderNames.nextElement();
            sb.append(headerName);
            sb.append(": ");
            Enumeration<String> eHeaderValues = request.getHeaders(headerName);
            while (eHeaderValues.hasMoreElements()) {
                sb.append(eHeaderValues.nextElement());
                if (eHeaderValues.hasMoreElements()) {
                    sb.append(", ");
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    String formatBody(HttpServletRequest request) throws IOException{

        InputStream body;
        InputStream inputStream = request.getInputStream();
        if (inputStream.markSupported()) {
            inputStream.mark(1);
            body = (inputStream.read() != -1) ? inputStream : null;
            inputStream.reset();
        }
        else {
            PushbackInputStream pushbackInputStream = new PushbackInputStream(inputStream);
            int b = pushbackInputStream.read();
            if (b == -1) {
                body = null;
            }
            else {
                body = pushbackInputStream;
                pushbackInputStream.unread(b);
            }
        }

        if (body == null) {
            return "";
        }

        StringBuilder out = new StringBuilder();
        PushbackReader reader = new PushbackReader(new InputStreamReader(body, StandardCharsets.UTF_8));
        char[] buffer = new char[BUFFER_SIZE];
        int bytesRead = -1;
        while ((bytesRead = reader.read(buffer)) != -1) {
            out.append(buffer, 0, bytesRead);
        }

        return out.toString();
    }
}


class ResetInputHttpServlet extends HttpServletRequestWrapper{
    InputStream inputStream;
    public ResetInputHttpServlet(HttpServletRequest request, InputStream inputStream){
        super(request);
        this.inputStream = inputStream;
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        return super.getInputStream();
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return super.getReader();
    }
}
