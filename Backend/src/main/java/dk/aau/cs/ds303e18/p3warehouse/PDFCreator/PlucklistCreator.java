package dk.aau.cs.ds303e18.p3warehouse.PDFCreator;

import java.util.*;

// The class for easy creation of pluck lists in .pdf, with the option of password-securing the document
public class PlucklistCreator extends SecurePDFCreator {
    public PlucklistCreator(String FilePath, String FileName, int xOffset){
        super(FilePath, FileName, xOffset);
    }

    // @Param info: OrderID;CustomerName;OrderDate
    // @Param translation: Pluck-list;OrderID;CustomerName;OrderDate
    public PlucklistCreator wirteInfo(String info, String translation){
        LinkedList<String> generalInfo = new LinkedList<>();
        String splitInfo[] = info.split(";");
        String splitTranslation[] = translation.split(";");
        int i = 1;

        writeBoldLineXYSize(splitTranslation[0] + ": " + splitInfo[0], X_OFFSET, 750, 16);
        makeLine('_', 60, 750);

        for (String s: splitInfo){
            generalInfo.add(splitTranslation[i] + ": " + s);
            i++;
        }

        writeLines(generalInfo, 725, true);
        return this;
    }

    // @Param products: ProductID;Description
    // @Param translation: ProductID;Description;Amount;Packed
    public PlucklistCreator writeOrderInfo(LinkedList<String> products, String translation){
        ArrayList<LinkedList<String>> productInfo = new ArrayList<>();
        String productSplit[];
        String translate[] = translation.split(";");

        // Write titles on the line
        makeLine('_', 60, 670);
        writeBoldLineXYSize(translate[0], X_OFFSET, 670, 11);
        writeBoldLineXYSize(translate[1], 170, 670, 11);
        writeBoldLineXYSize(translate[2], 400, 670, 11);
        writeBoldLineXYSize(translate[3], 470, 670, 11);

        // Split products up
        for (int i = 0; i < 4; i++) {productInfo.add(new LinkedList<>());}
        for (String s: products){
            productSplit = s.split(";");
            productInfo.get(0).add(productSplit[0]);
            productInfo.get(1).add(productSplit[1]);
            productInfo.get(2).add(productSplit[2]);
            productInfo.get(3).add("[   ]");
        }

        // Print product info
        writeLinesXYFontSize(productInfo.get(0), X_OFFSET, 655, 11);
        writeLinesXYFontSize(productInfo.get(1), 170, 655, 11);
        writeLinesXYFontSize(productInfo.get(2), 400, 655, 11);
        writeLinesXYFontSize(productInfo.get(3), 470, 655, 11);
        return this;
    }
}
