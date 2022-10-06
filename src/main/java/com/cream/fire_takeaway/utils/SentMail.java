package com.cream.fire_takeaway.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

/**
 * @Author: Cream
 * @Date: 2022-09-29-20:20
 * @Description:
 */
@Component
public class SentMail {

    @Value("${fire_takeaway.email.emailHost}")
    private String emailHost;

    @Value("${fire_takeaway.email.transportType}")
    private String transportType;

    @Value("${fire_takeaway.email.userName}")
    private String userName;

    @Value("${fire_takeaway.email.authCode}")
    private String authCode;

    @Value("${fire_takeaway.email.fromEmail}")
    private String fromEmail;

    @Value("${fire_takeaway.email.addressName}")
    private String addressName;

    @Value("${fire_takeaway.email.title}")
    private String title;

    public void sendMail(String email, String emailMsg)
            throws AddressException, MessagingException, UnsupportedEncodingException {
        //email为接收人的邮箱地址   emailMsg为发送的邮件内容
        // 1.创建一个程序与邮件服务器会话对象 Session
        Properties props = new Properties();
        // 设置邮件传输协议为SMTP
        props.setProperty("mail.transport.protocol", transportType);
        // 设置SMTP服务器地址
        props.setProperty("mail.host", emailHost);
        // 设置SMTP服务器是否需要用户验证，需要验证设置为true
        props.setProperty("mail.smtp.auth", "true");
        // 创建验证器
        Authenticator auth = new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userName, authCode);
            }
        };
        Session session = Session.getInstance(props, auth);
        // 2.创建一个Message，它相当于是邮件内容
        Message message = new MimeMessage(session);
        // 设置发送者
        message.setFrom(new InternetAddress(fromEmail, addressName, "utf-8"));

        message.setRecipient(Message.RecipientType.TO, new InternetAddress(email)); // 设置发送方式与接收者
        message.setSubject(MimeUtility.encodeText(title, "utf-8", "B"));
        message.setContent(emailMsg, "text/html;charset=utf-8");
        // 3.创建 Transport用于将邮件发送
        Transport.send(message);
    }
}
