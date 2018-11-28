package dk.aau.cs.ds303e18.p3warehouse.PDFCreator;

import java.util.LinkedList;
import java.util.concurrent.ThreadLocalRandom;

public class PDFCreatorTestUse {
    private DeliveryNoteCreator dNoteCreator = new DeliveryNoteCreator(System.getProperty("user.home") + "/Desktop/",
            "TestDocument_d", 50);
    private PlucklistCreator pListCreator = new PlucklistCreator(System.getProperty("user.home") + "/Desktop/",
            "TestDocument_p", 50);

    public static void main(String[] args) throws InterruptedException{
        new PDFCreatorTestUse().makeDeliveryNoteFile();
        System.out.print("Print 1 done \n");
        Thread.sleep(500);
        new PDFCreatorTestUse().makePluckListFile();
    }

    // This is the test case for delivery note
    private void makeDeliveryNoteFile(){
        LinkedList<String> lines = randomProducts(10);

        // creator.protectDocument("123"); // Super secure password (luckily only used for test :)
        dNoteCreator .writeDeliveryInfo( "Great company;A random person;SuperStreet 456A;9200 Aalborg;Denmark",
                "Leveres til:")
                .writeDNoteInfo("56468;1856;1.1.2556;9483464486;Buying some stuff;That guy over there",
                        "FØLGESEDDEL;Ordernr.;Dato;Kundenr.;Sag;Reference")
                .writeProductInfo(lines,"Some delivery;88888888", "Hermed følger;Titel;Rekv.nr.;" +
                        "S L U T L E V E R I N G;Varenr.;Beskrivelse;Antal")
                .setAuthor("JavaToPdf")
                .setTitle("A test of this program")
                .setSubject("Testing the output of a delivery note")
                .saveFile();
    }


    // This is the test case for pluck list note
    private void makePluckListFile(){
        LinkedList<String> lines = randomProducts(10);

        // creator.protectDocument("123"); // Super secure password (luckily only used for test :)
        pListCreator.wirteInfo("4688423;The old guy on the street;19/01/2038",
                "Pakkeliste;Order nummer;Kunde;Order dato")
                .writeOrderInfo(lines,"Varenr.;Beskrivelse;Antal;Pakket?")
                .setAuthor("JavaToPdf")
                .setTitle("A test of this program")
                .setSubject("Testing the output of a delivery note")
                .saveFile();
    }

    private LinkedList<String> randomProducts(int Amount){
        LinkedList<String> lines = new LinkedList<>();
        int ID, amount;
        String productName;

        for (int i = 0; i < 15; i++){
            ID = ThreadLocalRandom.current().nextInt(0, 90000);
            amount = ThreadLocalRandom.current().nextInt(0, 999);
            productName = "";
            for (int j = 0; j < 10; j++){
                productName +=  (char)(ThreadLocalRandom.current().nextInt(0, 26) + 'A');
            }
            lines.add(ID + ";" + productName + ";" + amount);
        }
        return lines;
    }
}