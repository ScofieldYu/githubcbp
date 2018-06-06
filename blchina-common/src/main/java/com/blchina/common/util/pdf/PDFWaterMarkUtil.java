package com.blchina.common.util.pdf;

import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

public class PDFWaterMarkUtil {

    public static void main(String[] args) {
//        waterMark("f://1.pdf", "f://2.jpg", "f://6.pdf", "");
    }

    public static void waterMark(String inputFile,String outputFile, String waterMarkName, List<PDFWater> waterMarkList) {
        System.out.println("inputFile = " + inputFile + ";outputFile = " + outputFile);
        try {

//            File f2 = new File(outputFile);
//            if (!f2.exists()) {
//                f2.createNewFile();
//            }
            int index = outputFile.lastIndexOf("/");
            String fold = outputFile.substring(0,index);
            File dir = new File(fold);
            if (!dir.exists() || !dir.isDirectory()) {
                dir.mkdirs();
            }
            File f2 = new File(outputFile);
            if (!f2.exists()) {
                f2.createNewFile();
            }
            PdfReader reader = new PdfReader(inputFile);
            PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(
                    outputFile));

            int total = reader.getNumberOfPages() + 1;
            int size = waterMarkList.size();
            for (int i = 1; i < total; i++) {
                if (size >= i) {
                    PDFWater pdfWater = waterMarkList.get(i-1);
                    int pageNum = pdfWater.getPageNum();


                    // 图片位置

                    PdfContentByte under;
                    int j = waterMarkName.length();
                    char c = 0;
                    int rise = 0;
                    if (i == pageNum) {
                        under = stamper.getUnderContent(i);
                        for (PdfXY pdfXY : pdfWater.getList()) {
                            String imageFile = pdfXY.getPath();
                            File f1 = new File(imageFile);
                            if (!f1.exists()) {
                                f1.createNewFile();
                            }
                            float x = pdfXY.getX();
                            float y = pdfXY.getY();
                            float signWidth = pdfXY.getWidth();
                            float signHeight = pdfXY.getHeight();
                            System.out.println("i = " + i + ";imageFile = " + imageFile + ";x = " + x + ";y = " + y + ";signWidth = " + signWidth + ";signHeight = " + signHeight);
                            Image image = Image.getInstance(imageFile);
                            image.setAbsolutePosition(x, y);
                            System.out.println("success");
                            rise = 400;

//                    under.beginText();

                            if (j >= 15) {
                                under.setTextMatrix(200, 120);
                                for (int k = 0; k < j; k++) {
                                    under.setTextRise(rise);
                                    c = waterMarkName.charAt(k);
                                    under.showText(c + "");
                                }
                            } else {
                                under.setTextMatrix(240, 100);
                                for (int k = 0; k < j; k++) {
                                    under.setTextRise(rise);
                                    c = waterMarkName.charAt(k);
                                    under.showText(c + "");
                                    rise -= 18;

                                }
                            }

                            // 添加水印文字
//                under.endText();

                            // 添加水印图片
                            under.addImage(image, signWidth, 0, 0, signHeight, x, y);
                            // 画个圈
//                under.ellipse(250, 450, 350, 550);
                            under.setLineWidth(1f);
                        }

                    }
                }
            }



//            Image image = Image.getInstance(imageFile);
//
//            // 图片位置
//            image.setAbsolutePosition(x, y);
//            PdfContentByte under;
//            int j = waterMarkName.length();
//            char c = 0;
//            int rise = 0;
//            for (int i = 1; i < total; i++) {
//                if (i == pageNum) {
//                    System.out.println("success");
//                    rise = 400;
//                    under = stamper.getUnderContent(i);
////                    under.beginText();
//
//                    if (j >= 15) {
//                        under.setTextMatrix(200, 120);
//                        for (int k = 0; k < j; k++) {
//                            under.setTextRise(rise);
//                            c = waterMarkName.charAt(k);
//                            under.showText(c + "");
//                        }
//                    } else {
//                        under.setTextMatrix(240, 100);
//                        for (int k = 0; k < j; k++) {
//                            under.setTextRise(rise);
//                            c = waterMarkName.charAt(k);
//                            under.showText(c + "");
//                            rise -= 18;
//
//                        }
//                    }
//
//                    // 添加水印文字
////                under.endText();
//
//                    // 添加水印图片
//                    under.addImage(image, signWidth, 0, 0, signHeight, x, y);
//                    // 画个圈
////                under.ellipse(250, 450, 350, 550);
//                    under.setLineWidth(1f);
//                }
//            }
            stamper.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




}
