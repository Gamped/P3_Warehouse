package dk.aau.cs.ds303e18.p3warehouse.MailService;

public class OrderInfoMail extends SendMail {
    private String yourName;

    public OrderInfoMail(String YourName){

        super();
        yourName = YourName;
    }

    public void sendOrderMsg(String orderTitle, String mailTo){

        sendEMail("Your order has been sent",
                "Hi, \n\n"
                        + "We send you this mail to notify you that your order: " + orderTitle + " has now been sent. \n\n"
                        + "Best regards, \n"
                        + yourName,
                mailTo);
    }
}