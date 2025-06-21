package com.klef.jfsd.userservice.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MailService {


    private final JavaMailSender mailSender;



    public    String  sendMail(String email, String fullName, String verificationLink)  {
        MimeMessage message = mailSender.createMimeMessage();
        String htmlContent = """
                <!DOCTYPE html>
                <html>
                <head>
                  <meta charset="UTF-8" />
                  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
                  <title>Password Reset</title>
                  <style>
                    body {
                      font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                      background-color: #f8f9fa;
                      margin: 0;
                      padding: 0;
                    }
                    .container {
                      max-width: 600px;
                      margin: 30px auto;
                      background-color: #ffffff;
                      border-radius: 8px;
                      padding: 30px;
                      box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
                    }
                    .header {
                      text-align: center;
                      padding-bottom: 20px;
                    }
                    .header h1 {
                      color: #5e3bd2;
                    }
                    .content {
                      font-size: 16px;
                      color: #333333;
                      line-height: 1.6;
                    }
                    .btn {
                      display: inline-block;
                      margin-top: 20px;
                      background-color: #5e3bd2;
                      color: white;
                      padding: 12px 24px;
                      text-decoration: none;
                      border-radius: 5px;
                      font-weight: bold;
                    }
                    .footer {
                      margin-top: 30px;
                      text-align: center;
                      font-size: 13px;
                      color: #888;
                    }
                  </style>
                </head>
                <body>
                  <div class="container">
                    <div class="header">
                      <h1>SkillSync</h1>
                    </div>
                    <div class="content">
                      <p>Hi <strong>{{fullName}}</strong>,</p>
                      <p>We received a request to reset your password. Click the button below to proceed:</p>
                      <a href="{{resetLink}}" class="btn">Reset Password</a>
                      <p>If you didn’t request this, you can safely ignore this email. The link is valid for 10 minutes.</p>
                    </div>
                    <div class="footer">
                      &copy; 2025 SkillSync. All rights reserved.
                    </div>
                  </div>
                </body>
                </html>
                """;
        String emailContent = htmlContent.replace("{{verificationLink}}", verificationLink)
                            .replace("{{fullName}}", fullName);
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message,true);
            helper.setTo(email);
            helper.setSubject("SkillSync Password Reset Link");
            helper.setText(emailContent, true);
            mailSender.send(message);
            return "please check your email to reset your password.";
        } catch (MessagingException e) {
            return  e.getMessage();
        }
    }

}
