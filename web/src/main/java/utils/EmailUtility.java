package utils;

import java.io.IOException;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class EmailUtility  {

    public void sendEmail() throws IOException, MessagingException{

    try
        {
        Properties props=new Properties();
        props.put("mail.smtp.auth",true);
        props.put("mail.smtp.starttls.enable",true);
        props.put("mail.smtp.host","smtp.gmail.com");
        props.put("mail.smtp.port","587");

        Session session=Session.getInstance(props, new javax.mail.Authenticator(){ protected PasswordAuthentication getPasswordAuthentication(){
        return new PasswordAuthentication(Constants.appDetails.get("emailID"),Constants.appDetails.get("emailPwd"));
        }});
        Message message=new MimeMessage(session);
        message.setFrom(new InternetAddress(Constants.appDetails.get("emailID")));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(Constants.appDetails.get("emailRecipient")));
        message.setSubject("Automation Testing"+Constants.testExeTime);
        message.setText("PFA");
        MimeBodyPart messageBodyPart=new MimeBodyPart();
        messageBodyPart.attachFile(Constants.emailfiletoattach);
        Multipart multipart=new MimeMultipart("mixed");
        multipart.addBodyPart(messageBodyPart);
        message.setContent(multipart);
        Transport.send(message);

        }
        catch(MessagingException e)
        {
        throw(e);
        }
        }
}
