package com.azure.auth.service.configuration;

import com.sendgrid.*;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

@Service
public class EmailWithAttachment {

    @Value("${sendgrid.api.key}")
    private String sendGridApiKey;

    public String sendEmailWithDynamicImageAttachment(String toEmail, String subject, String body, String imageUrl) {
        Email from = new Email("swamesha1234@gmail.com");
        Email to = new Email(toEmail);
        Content content = new Content("text/plain", body);
        Mail mail = new Mail(from, subject, to, content);

        if (imageUrl != null && !imageUrl.isEmpty()) {
            try {
                URL url = new URL(imageUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();

                if (connection.getResponseCode() == 200) {
                    byte[] fileData = connection.getInputStream().readAllBytes();
                    String encodedFile = Base64.getEncoder().encodeToString(fileData);

                    String contentType = connection.getContentType();
                    String fileExtension = contentType.split("/")[1];
                    String filename = "image." + fileExtension;

                    Attachments attachments = new Attachments();
                    attachments.setContent(encodedFile);
                    attachments.setType(contentType);
                    attachments.setFilename(filename);
                    attachments.setDisposition("attachment");

                    mail.addAttachments(attachments);
                } else {
                    return "Failed to fetch the image. HTTP Status: " + connection.getResponseCode();
                }
            } catch (IOException e) {
                e.printStackTrace();
                return "Error fetching image: " + e.getMessage();
            }
        }

        SendGrid sg = new SendGrid(sendGridApiKey);
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);

            System.out.println("Status Code: " + response.getStatusCode());
            return "Email sent successfully";
        } catch (IOException ex) {
            ex.printStackTrace();
            return "Error while sending email: " + ex.getMessage();
        }
    }

}

