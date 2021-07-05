package cn.qisee.cheesemilk.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.Properties;

@Service
public class MailService {

    private final Log logger = LogFactory.getLog(getClass());
    @Autowired
    Environment env;

    private JavaMailSenderImpl mailSender;

    private SimpleMailMessage mailMessage;

    @PostConstruct
    public void init() {
        mailSender = new JavaMailSenderImpl();
        mailMessage = new SimpleMailMessage();
        String username = env.getProperty("spring.mail.username");
        mailSender.setHost(env.getProperty("spring.mail.host"));
        mailSender.setPort(Integer.parseInt(env.getProperty("spring.mail.port")));
        mailSender.setUsername(username);
        mailSender.setPassword(env.getProperty("spring.mail.password"));
        mailSender.setProtocol(env.getProperty("spring.mail.protocol"));
        mailSender.setDefaultEncoding(env.getProperty("spring.mail.default-encoding"));
        mailSender.setJavaMailProperties(javaMailPros());
        mailMessage.setFrom(username);
        mailMessage.setSubject("Verify");
    }

    public Properties javaMailPros(){
        Properties props = new Properties();
        props.put("mail.smtp.ssl", "true");
        props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        //这个参数会把和邮箱服务器的交互过程都打印出来
        props.put("mail.stmps.debug", "true");
        return props;
    }


    /**
     *
     * @param to
     * @param content
     * @throws MailException
     */
    public void sendMail(String to, String content) {
        try {
            mailMessage.setTo(to);
            mailMessage.setText(content);
            mailMessage.setSentDate(new Date());

            mailSender.send(mailMessage);

            logger.info("send verify mail to " + to + " successful");
        }catch (MailException e){
            logger.info("send mail fail, " + e.getMessage() + " cause");
            throw e;
        }
    }
}
