package dk.aau.cs.ds303e18.p3warehouse.PDFCreator;

import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.IOException;
import java.util.*;

// The class for easy creation of delivery notes in .pdf, with the option of password-securing the document
public class DeliveryNoteCreator extends SecurePDFCreator {

    // Note X_OFFSET is hardcoded to 50
    public DeliveryNoteCreator(String FilePath, String FileName, int xOffset){
        super(FilePath, FileName, xOffset);
        X_OFFSET = 50;
        makeLine('_', 60, 720);
    }

    // @Param formattedInfo: Company;Recipient;Address;Zip City;Country
    // @Param deliveryTo: Translation of "delivered to"
    public DeliveryNoteCreator writeDeliveryInfo(String formattedInfo, String deliveryTo){
        LinkedList<String> deliveryInfo = new LinkedList<>();

        String unpackedInfo[] = formattedInfo.split(";");

        try {
            cs.setFont(PDType1Font.HELVETICA, 8);
            writeLine(deliveryTo, 703, false);
            cs.setFont(PDType1Font.HELVETICA_BOLD, 10);
            cs.setLeading(10 + 2f);
        } catch (IOException e) {System.out.print("ERROR: " + e);}

        for (String s: unpackedInfo){deliveryInfo.add(s);}
        writeLines(deliveryInfo, 690, false);
        return this;
    }

    // @Param formattedInfo: DeliveryNoteID;OrderID;Date;CustomerID;CaseTitle;ReferencePerson
    // @Param translation: Translation of "Delivery-note;Order number;Date;CustomerID;Case;Reference
    public DeliveryNoteCreator writeDNoteInfo(String formattedInfo, String translation){
        LinkedList<String> info = new LinkedList<>();
        int i = 0;

        String unpackedInfo[] = formattedInfo.split(";");
        String unpackedTranslation[] = translation.split(";");

        for (String s: unpackedInfo) {
            info.add(unpackedTranslation[i] + ": " + s);
            i++;
        }

        // Write first line in bold, but rest in normal
        try {
            cs.beginText();
            cs.newLineAtOffset(340, 700);
            cs.setLeading(10 + 2f);
            cs.setFont(PDType1Font.HELVETICA_BOLD, 11);
            cs.showText(info.get(0));
            cs.newLine();
            info.remove(0);

            cs.setFont(PDType1Font.HELVETICA, 10);
            for (String s: info){
                cs.showText(s);
                cs.newLine();
            }
            cs.endText();
        } catch (IOException e) {System.out.print("ERROR: " + e);}
        return this;
    }

    // @Param products: ProductID;Description;Amount
    // @Param extraInfo: Title;REQN-number
    // @Param translation: Hereby follows;Title;REQN(Requisition-number);End-delivery;ProductID;Description;Amount
    public DeliveryNoteCreator writeProductInfo(LinkedList<String> products, String extraInfo, String translation){
        ArrayList<LinkedList<String>> productInfo = new ArrayList<>();
        String productSplit[];

        String extra[] = extraInfo.split(";");
        String translate[] = translation.split(";");

        /* -Write top info- */

        // Write hereby follows
        writeBoldLineXYSize(translate[0] + ":", 50,610,10);

        // Write title and REQN
        try{
            cs.beginText();
            cs.newLineAtOffset(50, 595);
            cs.setLeading(10 + 2f);
            cs.setFont(PDType1Font.HELVETICA, 10);
            cs.showText(translate[1] + ": " + extra[0]);
            cs.newLine();
            cs.showText(translate[2] + ": " + extra[1]);
            cs.endText();
        } catch (IOException e) {System.out.print("ERROR: " + e);}

        // Write end delivery
        writeBoldLineXYSize(translate[3], 340,590,11);

        // Write titles on the line
        makeLine('_', 60, 550);
        writeBoldLineXYSize(translate[4], 50, 550, 10);
        writeBoldLineXYSize(translate[5], 170, 550, 10);
        writeBoldLineXYSize(translate[6], 470, 550, 10);

        // Split products up
        for (int i = 0; i < 3; i++) {productInfo.add(new LinkedList<>());}
        for (String s: products){
            productSplit = s.split(";");
            productInfo.get(0).add(productSplit[0]);
            productInfo.get(1).add(productSplit[1]);
            productInfo.get(2).add(productSplit[2]);
        }

        // Print product info
        writeLinesXYFontSize(productInfo.get(0), 50, 535, 9);
        writeLinesXYFontSize(productInfo.get(1), 170, 535, 9);
        writeLinesXYFontSize(productInfo.get(2), 470, 535, 9);
        return this;
    }
}
