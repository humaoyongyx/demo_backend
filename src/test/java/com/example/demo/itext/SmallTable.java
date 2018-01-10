package com.example.demo.itext;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
 
/**
 * @author Bruno Lowagie (iText Software)
 */
public class SmallTable {
    public static final String DEST = "D:/test/pdf/test/small_table.pdf";
    public static final String FONT = "C:\\Windows\\Fonts\\msyh.ttf";
 
    public static void main(String[] args) throws IOException, DocumentException {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new SmallTable().createPdf(DEST);
    }
    public void createPdf(String dest) throws IOException, DocumentException {
        Rectangle small = new Rectangle(400,200);
       // Font smallfont = new Font(FontFamily.HELVETICA, 10);
        Font smallfont = FontFactory.getFont(FONT, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Document document = new Document(small, 5, 5, 5, 5);
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(dest));
        document.open();
        PdfPTable table = new PdfPTable(3);
        table.setTotalWidth(new float[]{ 160, 120,100});
        table.setLockedWidth(true);
        PdfContentByte cb = writer.getDirectContent();
        // first row
        PdfPCell cell = new PdfPCell(new Phrase("Some text here 是否"));
        cell.setFixedHeight(30);
        cell.setBorderWidth(1);
        cell.setBorderColor(BaseColor.GREEN);
       // cell.setColspan(2);
        table.addCell(cell);
        // second row
        cell = new PdfPCell(new Phrase("Some more textsdfs撒地方", smallfont));
        cell.setFixedHeight(30);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);
        Barcode128 code128 = new Barcode128();
        code128.setCode("14785236987541");
        code128.setCodeType(Barcode128.CODE128);
        Image code128Image = code128.createImageWithBarcode(cb, null, null);
        cell = new PdfPCell(code128Image, true);
        cell.setBorderWidth(1);
        cell.setBorderColor(BaseColor.RED);
        cell.setFixedHeight(30);
        table.addCell(cell);
        // third row
        cell = new PdfPCell(new Phrase("and something else here", smallfont));
        cell.setBorderWidth(1);
        cell.setBorderColor(BaseColor.BLUE);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(cell);

        // third row
        cell = new PdfPCell(new Phrase("and something else here", smallfont));
        cell.setBorderWidth(1);
        cell.setBorderColor(BaseColor.BLUE);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(cell);
        document.add(table);
        document.close();
    }
}