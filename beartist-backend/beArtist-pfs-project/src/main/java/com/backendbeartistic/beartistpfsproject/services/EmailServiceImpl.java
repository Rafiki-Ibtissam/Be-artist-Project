package com.backendbeartistic.beartistpfsproject.services;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
@Service
public class EmailServiceImpl implements EmailService {

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public String sendEmail(MultipartFile[] file, String to, String[] cc, String subject, String body) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(fromEmail);
            mimeMessageHelper.setTo(to);
            if (cc != null && cc.length > 0) {
                mimeMessageHelper.setCc(cc);
            }
            mimeMessageHelper.setText(body, true);

            // Check if files are provided before trying to attach them
            if (file != null) {
                for (MultipartFile f : file) {
                    mimeMessageHelper.addAttachment(f.getOriginalFilename(), new ByteArrayResource(f.getBytes()));
                }
            }

            javaMailSender.send(mimeMessage);
            return "Mail sent successfully!";
        } catch (Exception e) {
            throw new RuntimeException("Failed to send email: " + e.getMessage(), e);
        }
    }
}
