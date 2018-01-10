package com.example.demo.itext;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by issac.hu on 2018/1/10.
 */
public class Test {

    public static  final String templatePdfPath="D:\\test\\pdf\\test\\module.pdf";
    public static  final String dest="D:\\test\\pdf\\test\\modulex.pdf";

    public static void main(String[] args) throws IOException, DocumentException {
        removefirstPage(2);
         // fullfillFields();
    }

    public static void fullfillFields()throws IOException, DocumentException{
        PdfReader reader  = new PdfReader(templatePdfPath);

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        PdfStamper ps = new PdfStamper(reader, bos);

        /*使用中文字体 */
        BaseFont bf = BaseFont.createFont("C:\\Windows\\Fonts\\msyh.ttf",
                BaseFont.IDENTITY_H,BaseFont.EMBEDDED);
        ArrayList<BaseFont> fontList = new ArrayList<BaseFont>();
        fontList.add(bf);

        AcroFields s = ps.getAcroFields();
        s.setSubstitutionFonts(fontList);
        s.setField("text1","微软雅黑字体！");
        ps.setFormFlattening(true);
        ps.close();

        FileOutputStream fos = new FileOutputStream(dest);
        fos.write(bos.toByteArray());
        fos.close();
    }

    //移除pdf前几页
    public static void removefirstPage(int size) throws IOException, DocumentException {
        //Loading an existing document
        File file = new File(templatePdfPath);
        PDDocument doc = PDDocument.load(file);

        //Listing the number of existing pages
        System.out.print(doc.getNumberOfPages());
       // doc.setAllSecurityToBeRemoved(true);
        doc.setAllSecurityToBeRemoved(true);
        System.out.println(doc.isAllSecurityToBeRemoved());


        for(int i=0;i<size;i++){
            doc.removePage(0);
        }

        //Saving the document
        doc.save(dest);

        //Closing the document
        doc.close();

    }
}
