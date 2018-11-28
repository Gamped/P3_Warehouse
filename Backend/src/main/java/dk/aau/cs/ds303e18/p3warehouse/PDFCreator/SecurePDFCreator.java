package dk.aau.cs.ds303e18.p3warehouse.PDFCreator;

import org.apache.pdfbox.pdmodel.encryption.*;

import java.io.IOException;

// This extends the PDF creator with the option to secure documents with passwords
public abstract class SecurePDFCreator extends PDFCreator {
    private boolean hasBeenSecured = false;

    public SecurePDFCreator(String FilePath, String FileName, int xOffset){
        super(FilePath, FileName, xOffset);
    }

    public SecurePDFCreator protectDocument(String password){
        if (!hasBeenSecured) {
            AccessPermission ap = new AccessPermission();
            StandardProtectionPolicy spp = new StandardProtectionPolicy(password, password, ap);

            spp.setPreferAES(true);
            spp.setEncryptionKeyLength(128);
            try{
                doc.protect(spp);
            } catch (IOException e) {System.out.print("ERROR: " + e);}
            hasBeenSecured = true;
        }
        return this;
    }
}