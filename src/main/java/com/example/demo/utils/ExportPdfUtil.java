package com.example.demo.utils;

import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

public class ExportPdfUtil<T>{
    /**
     * sheetName:导出的pdf文件名
     * headers:表格列头
     * columns:列
     * lists:数据源（数据集合）
     *
     */

    private final Logger logger=LoggerFactory.getLogger(ExportPdfUtil.class);
    public void exportPdf(String sheetName, String[] headers, String[] columns, List lists, HttpServletRequest request,
                          HttpServletResponse response, String realPath) throws Exception{
        logger.info("zsl==========开始导出pdf");
        Document document = new Document(PageSize.A2,50,50,50,50);
//      PdfWriter pw = PdfWriter.getInstance(document,new FileOutputStream("D:\\Test.pdf"));
        String fileName = sheetName + ".pdf";
        String destPath = "D:\\test\\pdf\\testx.pdf";
        logger.info("zsl==========pdfPath:" + destPath);
        mkDirs(destPath);
        String filePath = destPath + fileName;
        logger.info("zsl==========filePath:" + filePath);
        //必须紧跟在document创建的时候创建PdfWriter,否则导出无数据
        PdfWriter pw = PdfWriter.getInstance(document,new FileOutputStream(filePath));

        document.open();
        //中文字体显示，直接用下载的字体jar包也可以
//      BaseFont baseFont = BaseFont.createFont("STSongStd-Light", "UniGB-UCS2-H",false);
        //绝对路径
        String fontPath = realPath + "/WEB-INF/classes/font/SIMYOU.TTF";
        BaseFont baseFont = BaseFont.createFont(fontPath,BaseFont.IDENTITY_H,BaseFont.EMBEDDED);
        Font font = new Font(baseFont,10,Font.NORMAL);

        Paragraph p = new Paragraph("title",font);
        document.add(p);

        PdfPTable table = new PdfPTable(headers.length);
        table.setSpacingBefore(25);
        table.setSpacingAfter(25);
        //创建表头
        for (int i = 0; i < headers.length; i++) {
            PdfPCell cell = new PdfPCell(new Paragraph(headers[i],font));
            table.addCell(cell);
        }



        for (int i = 0; i < lists.size(); i++) {
            String value = "";
            if(null == lists.get(i)){
                value = "";
            }else{
                value = lists.get(i).toString();
            }
            table.addCell(new Paragraph(value,font));
        }


        document.add(table);
        document.close();

        pw.flush();
        pw.close();
        downloadPdf(filePath, response);
        logger.info("zsl==========导出pdf成功");
    }

    public static void mkDirs(String filepath){
        File fileDir = new File(filepath);
        if(!fileDir.exists()){
            fileDir.mkdirs();
        }
    }
    //下载
    public static void downloadPdf(String filepath, HttpServletResponse response)
            throws IOException {
        File file = new File(filepath);
        String fileName = file.getName();
        response.setContentType("application/pdf;charset=utf-8");
        response.addHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("GBK"), "ISO8859-1"));
        response.setCharacterEncoding("utf-8");
        InputStream fis = new BufferedInputStream(new FileInputStream(file));
        byte[] b = new byte[fis.available()];
        fis.read(b);
        response.getOutputStream().write(b);
        response.getOutputStream().flush();
        response.getOutputStream().close();
        response.flushBuffer();
        fis.close();
    }
}