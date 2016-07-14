package me.test;

import java.io.File;
import net.sourceforge.tess4j.*;


// sudo apt-get install tesseract-ocr tesseract-ocr-eng tesseract-ocr-chi-sim
// mvn exec:java  -Dexec.mainClass=me.test.TesseractExample
public class TesseractExample {

    public static void main(String[] args) {
        File imageFile = new File("/home/zll/Desktop/tt.png");
        ITesseract instance = new Tesseract(); // JNA Interface Mapping
        instance.setDatapath("/usr/share/tesseract-ocr");
        // ITesseract instance = new Tesseract1(); // JNA Direct Mapping

        try {
            String result = instance.doOCR(imageFile);
            System.out.println(result);
        } catch (TesseractException e) {
            System.err.println(e.getMessage());
        }
    }

}
