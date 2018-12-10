package dk.aau.cs.ds303e18.p3warehouse.MailService;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.util.Date;
import java.util.Properties;

public abstract class SendMail {
    private final String name = "4N Mailhouse";
    private String emailID, password;
    private Session session = null;
    private boolean loginError = false;

    public SendMail(){
        // Setup basic connection
        Properties props = System.getProperties();

        getLogin();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", true);
        props.put("mail.smtp.starttls.enable", "true");

        Authenticator auth = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(emailID, password);
            }
        };

        session = Session.getDefaultInstance(props, auth);
    }

    // To not post login-credentials to github ;)
    private void getLogin(){
        try {
            String str;
            String[] strSplit;
            File loginInfo = new File(System.getProperty("user.home") + "/Desktop/mail.txt");

            if (!loginInfo.exists()){
                System.out.print("Login credential file does not exist, so we can not send mails :( \n");
                loginError = true;
                return;
            }

            BufferedReader br = new BufferedReader(new FileReader(loginInfo));

            str = br.readLine();
            strSplit = str.split(";");

            emailID = strSplit[0];
            password = strSplit[1];
        } catch (IOException e) {e.printStackTrace();}
    }

    public void sendEMail(String subject, String body, String toEmail) {
        if (!loginError){
            try {
                MimeMessage mm = new MimeMessage(session);

                // Setup email
                mm.addHeader("Content-type", "text/HTML; charset=UTF-8");
                mm.addHeader("format", "flowed");
                mm.addHeader("Content-Transfer-Encoding", "8bit");
                mm.setFrom(new InternetAddress(emailID, name));
                mm.setReplyTo(InternetAddress.parse(emailID, false));
                mm.setSentDate(new Date());
                mm.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));

                // Setup email content
                mm.setSubject(subject, "UTF-8");
                mm.setText(body, "UTF-8");

                // Send mail
                System.out.print("Trying to send mail \n");
                Transport.send(mm);
                System.out.print("Mail sent \n");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.print("Mail NOT sent, since no login :( \n");
        }
    }
}
