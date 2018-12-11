package dk.aau.cs.ds303e18.p3warehouse;

import dk.aau.cs.ds303e18.p3warehouse.MailService.OrderInfoMail;

public class MailTest {
    public static void main(String args[]){
        OrderInfoMail mailSender = new OrderInfoMail("A Java program");
        mailSender.sendOrderMsg("TEST order", "mathiasgam@gmail.com");
    }
}
