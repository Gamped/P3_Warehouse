package dk.aau.cs.ds303e18.p3warehouse.MailService;

public class MailSendExample {
    public static void main(String args[]){
        OrderInfoMail mailSender = new OrderInfoMail("A Java program");
        mailSender.sendOrderMsg("Mathias", "TEST ORDER");
    }
}
