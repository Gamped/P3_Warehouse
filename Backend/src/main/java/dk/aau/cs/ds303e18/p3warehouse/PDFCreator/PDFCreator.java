package dk.aau.cs.ds303e18.p3warehouse.PDFCreator;

import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.IOException;
import java.util.*;

// Abstract class for creating .pdf files using Apache PDFBox
public abstract class PDFCreator {
    protected String filePath, fileName;
    protected PDDocument doc = new PDDocument();
    protected ArrayList<PDPage> pages = new ArrayList<>();
    protected PDDocumentInformation pdi = doc.getDocumentInformation();
    protected PDPageContentStream cs;
    protected int X_OFFSET;

    // @param FilePath: The location of the file
    // @param FileName: The name of the file (DO NOT! end with .pdf)
    PDFCreator(String FilePath, String FileName, int xOffset){
        filePath = FilePath;
        fileName = FileName;
        X_OFFSET = xOffset;

        pages.add(new PDPage(PDRectangle.A4));
        doc.addPage(pages.get(0));
        cs = setupCS();

        setCreationDay();
    }

    private PDPageContentStream setupCS(){
        try {
            return new PDPageContentStream(doc, pages.get(0));
        } catch (IOException e) {System.out.print("ERROR: " + e);}
        return null;
    }

    private void setCSFont(int fontSize){
        try {
            cs.setFont(PDType1Font.HELVETICA, fontSize);
            cs.setLeading(fontSize + 4f);
        } catch (IOException e) {System.out.print("ERROR: " + e);}
    }

    public PDFCreator setCreationDay(){
        Calendar date = new GregorianCalendar();
        pdi.setCreationDate(date);
        return this;
    }

    public PDFCreator setTitle(String Title){
        pdi.setTitle(Title);
        return this;
    }

    public PDFCreator setAuthor(String Author){
        pdi.setAuthor(Author);
        return this;
    }

    public PDFCreator setSubject(String Subject){
        pdi.setSubject(Subject);
        return this;
    }

    public PDFCreator writeLine(String text, int y, boolean defaultFont){
        try {
            cs.beginText();
            if (defaultFont){setCSFont(25);}
            cs.newLineAtOffset(X_OFFSET, y);
            cs.showText(text);
            cs.endText();
        } catch (IOException e) {System.out.print("ERROR: " + e);}
        return this;
    }

    public PDFCreator makeLine(char c, int amount, int y){
        String line = "";
        try{
            for (int i = 0; i < amount; i++){line += c;}
            cs.beginText();
            setCSFont(14);
            cs.newLineAtOffset(X_OFFSET, y);
            cs.showText(line);
            cs.endText();
        } catch (IOException e) {System.out.print("ERROR: " + e);}
        return this;
    }

    public PDFCreator writeLineXYSize(String text, int x, int y, int fontSize){
        try{
            cs.beginText();
            cs.newLineAtOffset(x, y);
            cs.setFont(PDType1Font.HELVETICA, fontSize);
            cs.showText(text);
            cs.endText();
        } catch (IOException e) {System.out.print("ERROR: " + e);}
        return this;
    }

    public PDFCreator writeBoldLineXYSize(String text, int x, int y, int fontSize){
        try{
            cs.beginText();
            cs.newLineAtOffset(x, y);
            cs.setFont(PDType1Font.HELVETICA_BOLD, fontSize);
            cs.showText(text);
            cs.endText();
        } catch (IOException e) {System.out.print("ERROR: " + e);}
        return this;
    }

    public PDFCreator writeLines(LinkedList<String> Lines, int yOffset, boolean defaultFont){
        try {
            cs.beginText();
            if (defaultFont){setCSFont(12);}
            cs.newLineAtOffset(X_OFFSET, yOffset);
            for (String s: Lines) {
                cs.showText(s);
                cs.newLine();
            }
            cs.endText();
        } catch (IOException e) {System.out.print("ERROR: " + e);}
        return this;
    }

    public PDFCreator writeLinesXYFontSize(LinkedList<String> Lines, int xOffset, int yOffset, int fontSize){
        try {
            cs.beginText();
            setCSFont(fontSize);
            cs.newLineAtOffset(xOffset, yOffset);
            for (String s: Lines) {
                cs.showText(s);
                cs.newLine();
            }
            cs.endText();
        } catch (IOException e) {System.out.print("ERROR: " + e);}
        return this;
    }

    private String exactFilePath(){return filePath + "/" + fileName + ".pdf";}

    // Note this HAS to be the last function called
    public void saveFile(){
        try {
            cs.close();
            doc.save(exactFilePath());
            doc.close();
        } catch (IOException e) {System.out.print("ERROR: " + e);}
    }
}