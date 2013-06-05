package me.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.owasp.validator.html.AntiSamy;
import org.owasp.validator.html.CleanResults;
import org.owasp.validator.html.Policy;
import org.owasp.validator.html.PolicyException;
import org.owasp.validator.html.ScanException;

// https://www.owasp.org/index.php/XSS_Filter_Evasion_Cheat_Sheet
public class AntiSamyTest {

    public static void main(String[] args) throws PolicyException, IOException, ScanException {

        final File inputDir = new File("dirtyInput");
        final File outputDir = new File("cleanResult");
        InputStream prolicyIn = AntiSamyTest.class.getResourceAsStream("antisamy-ebay-1.4.4.xml");

        Policy policy = Policy.getInstance(prolicyIn);
        AntiSamy as = new AntiSamy();

        String[] dirtyFiles = inputDir.list(FileFilterUtils.and(
                FileFilterUtils.suffixFileFilter(".html"),
                FileFilterUtils.notFileFilter(FileFilterUtils.directoryFileFilter())
                ));
        for (String dirtyFile : dirtyFiles) {

            File inFile = new File(inputDir, dirtyFile);
            File outFile = new File(outputDir, dirtyFile.substring(0, dirtyFile.length() - 5)
                    + ".clean.html");

            System.out.println("Processing file :" + inFile.getAbsolutePath());

            String dirtyInput = IOUtils.toString(new FileInputStream(inFile));
            CleanResults cr = as.scan(dirtyInput, policy, AntiSamy.SAX);
            String cleanResult = cr.getCleanHTML();

            IOUtils.write(cleanResult,
                    new FileOutputStream(outFile));
        }
    }
}
