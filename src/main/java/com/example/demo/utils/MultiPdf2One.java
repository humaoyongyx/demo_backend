package com.example.demo.utils;




import org.apache.pdfbox.multipdf.PDFMergerUtility;

import java.io.File;
import java.io.IOException;

public class MultiPdf2One {
    /**
     * 
     * @param folder
     * @return
     * @throws IOException
     */
    private static String[] getFiles(String folder) throws IOException {
        File _folder = new File(folder);
        String[] filesInFolder;

        if (_folder.isDirectory()) {
            filesInFolder = _folder.list();
            return filesInFolder;
        } else {
            throw new IOException("Path is not a directory");
        }
    }

    public static void main(String[] args) throws Exception {
        PDFMergerUtility mergePdf = new PDFMergerUtility();
        // String folder = System.getProperty("user.dir")+"\\pdfs";
        String folder = "D:\\test\\pdf";
        System.out.println(folder);
        String destinationFileName = "D:\\test\\pdf\\mergedPdf.pdf";
        String[] filesInFolder = getFiles(folder);
        for (int i = 0; i < filesInFolder.length; i++)
            mergePdf.addSource(folder + File.separator + filesInFolder[i]);
        mergePdf.setDestinationFileName(destinationFileName);
        mergePdf.mergeDocuments();
        System.out.print("done");
    }
}