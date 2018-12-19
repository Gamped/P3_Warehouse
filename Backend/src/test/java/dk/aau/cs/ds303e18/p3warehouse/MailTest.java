package dk.aau.cs.ds303e18.p3warehouse;

import dk.aau.cs.ds303e18.p3warehouse.MailService.OrderInfoMail;

public class MailTest {

    public static void main(String args[]){

        // NOTE: You need a file called "mail.txt" on your desktop with the following: [gmail address];[password]
        // The gmail account needs to allow "less secure apps"
        // NOTE: You need to change the email to your own, in order to recieve a mail
        OrderInfoMail mailSender = new OrderInfoMail("A Java program");
        mailSender.sendOrderMsg("TEST order", "mgampe17@student.aau.dk");
    }
}
