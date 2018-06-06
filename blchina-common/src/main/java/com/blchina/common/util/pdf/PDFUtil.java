package com.blchina.common.util.pdf;

import com.blchina.common.util.date.DateUtil;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;
import com.sun.pdfview.PDFFile;
import com.sun.pdfview.PDFPage;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PDFUtil {

    /*
    *  PDF转化成图片:按固定高度、宽度缩放
    */
    public static Map<String, String> pdfToImage(String pdfPath, String savePath, int imgWidth, int imgHeight, String prefixImgName){
        Map map = new HashMap<String, String>();
        try {
            // load a pdf from a byte buffer
            File file = new File(pdfPath);
            RandomAccessFile raf = new RandomAccessFile(file, "r");
            FileChannel channel = raf.getChannel();
            ByteBuffer buf = channel.map(FileChannel.MapMode.READ_ONLY, 0, channel.size());
            PDFFile pdfFile = new PDFFile(buf);
            int pageNum = pdfFile.getNumPages();
            System.out.println("PDF文件的总页数为：" + pageNum);
            for (int i = 1; i <= pageNum; i++) {
                System.out.println("PDF文件第" + i + "页转化图片开始");
                // draw the first page to an image
                PDFPage page = pdfFile.getPage(i);

                // get the width and height for the doc at the default zoom
                Rectangle rect = new Rectangle(0, 0, (int) page.getBBox().getWidth(), (int) page.getBBox().getHeight());

                // generate the image
                Image img = page.getImage(
                        imgWidth, // width
                        imgHeight, // height
                        rect, // clip rect
                        null, // null for the ImageObserver
                        true, // fill background with white
                        true // block until drawing is done
                );

                BufferedImage tag = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_RGB);
                tag.getGraphics().drawImage(img, 0, 0, imgWidth, imgHeight, null);
                String imgName = prefixImgName + "_" + i + ".png";
                String srcPath = savePath + "\\" + imgName;
                System.out.println("保存图片名称为 ：  " + imgName);
                System.out.println("成功保存图片到 ：  " + srcPath);
                FileOutputStream out = new FileOutputStream(srcPath); // 输出到文件流
                ImageIO.write(tag, "JPEG", out);
//                JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
//                encoder.encode(tag); // JPEG编码
                out.close();
                map.put(i, srcPath);
                System.out.println("PDF文件第" + i + "页转化图片结束");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    /*
   *  PDF转化成图片:按宽度比等比例缩放
   */
//    public static List<String> pdfToImageByWidthScale(String pdfPath, String savePath, int imgWidth, String prefixImgName, String preImageUrl){
//        List<String> list = new ArrayList<String>();
//        try {
//            File file = new File(pdfPath);
//            RandomAccessFile raf = new RandomAccessFile(file, "r");
//            FileChannel channel = raf.getChannel();
//            ByteBuffer buf = channel.map(FileChannel.MapMode.READ_ONLY, 0, channel.size());
//
//            PDFFile pdfFile = new PDFFile(buf);
//            int pageNum = pdfFile.getNumPages();
//            System.out.println("PDF文件的总页数为：" + pageNum);
//            for (int i = 1; i <= pageNum; i++) {
//
//                System.out.println("PDF文件第" + i + "页转化图片开始");
//                // draw the first page to an image
//                PDFPage page = pdfFile.getPage(i);
//
//                // get the width and height for the doc at the default zoom
//                Rectangle rect = new Rectangle(0, 0, (int) page.getBBox().getWidth(), (int) page.getBBox().getHeight());
//                double scale = imgWidth/rect.getWidth();
//                System.out.println("缩放比例为: " + scale);
//                int imgHeight = (int)(scale*rect.getHeight());
//                System.out.println("缩放后的宽高分别为：width = " + imgWidth + ", height = " + imgHeight);
//                // generate the image
//                Image img = page.getImage(
//                        imgWidth, // width
//                        imgHeight, // height
//                        rect, // clip rect
//                        null, // null for the ImageObserver
//                        true, // fill background with white
//                        true // block until drawing is done
//                );
//
//                BufferedImage tag = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_RGB);
//                tag.getGraphics().drawImage(img, 0, 0, imgWidth, imgHeight, null);
//                String imgName = prefixImgName + "-" + i + "_" + DateUtil.formatDateToStringNewDefault() + ".png";
//                String path = savePath + prefixImgName;
//                File folder = new File(path);
//                if (!folder.exists() || !folder.isDirectory()) {
//                    folder.mkdirs();
//                }
//                String srcPath = path + "/" + imgName;
//                File f = new File(srcPath);
//                if (!f.exists()) {
//                    f.createNewFile();
//                }
//                System.out.println("保存图片名称为 ：  " + imgName);
//                System.out.println("成功保存图片到 ：  " + srcPath);
//                FileOutputStream out = new FileOutputStream(srcPath); // 输出到文件流
//                ImageIO.write(tag, "JPEG", out);
////                JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
////                encoder.encode(tag); // JPEG编码
//                out.close();
//                list.add(preImageUrl + prefixImgName + "/" + imgName);
//                System.out.println("PDF文件第" + i + "页转化图片结束");
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return list;
//    }
    public static java.util.List<String> pdfToImageByWidthScale(String pdfPath, String savePath, int imgWidth, String prefixImgName, String preImageUrl){
        java.util.List<String> list = new ArrayList<String>();
        try {
            File file = new File(pdfPath);
            PDDocument doc = PDDocument.load(file);
            PDFRenderer renderer = new PDFRenderer(doc);
            int pageCount = doc.getNumberOfPages();
            System.out.println("PDF文件的总页数为：" + pageCount);
            for (int i = 0; i < pageCount; i++) {
                BufferedImage image = renderer.renderImageWithDPI(i, 256, ImageType.RGB);
                System.out.println("PDF文件第" + (i+1) + "页转化图片开始");
                System.out.println("PDF文件宽度为： width = " + image.getWidth() + ";高度为： height = " + image.getHeight());
                double scale = imgWidth/(double)(image.getWidth());
                System.out.println("缩放比例为: " + scale);
                int imgHeight = (int)(scale*image.getHeight());

                System.out.println("缩放后的宽高分别为：width = " + imgWidth + ", height = " + imgHeight);

                BufferedImage srcImage = resize(image, imgWidth, imgHeight);
                String imgName = prefixImgName + "-" + (i+1) + "_" + DateUtil.formatDateToStringNewDefault() + ".png";
                String path = savePath + prefixImgName;
                File folder = new File(path);
                if (!folder.exists() || !folder.isDirectory()) {
                    folder.mkdirs();
                }
                String srcPath = path + "/" + imgName;
                File f = new File(srcPath);
                if (!f.exists()) {
                    f.createNewFile();
                }
                ImageIO.write(srcImage, "PNG", f);
                System.out.println("保存图片名称为 ：  " + imgName);
                System.out.println("成功保存图片到 ：  " + srcPath);
                list.add(preImageUrl + prefixImgName + "/" + imgName);
                System.out.println("PDF文件第" + (i+1) + "页转化图片结束");
            }
            doc.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    /*
   *  PDF转化成图片:按pdf尺寸
   */
    public static java.util.List<String> pdfToImageByOriginalSize(String pdfPath, String savePath, String prefixImgName, String preImageUrl){
        java.util.List<String> list = new ArrayList<String>();
        try {
            File file = new File(pdfPath);
            PDDocument doc = PDDocument.load(file);
            PDFRenderer renderer = new PDFRenderer(doc);
            int pageCount = doc.getNumberOfPages();
            for (int i = 0; i < pageCount; i++) {
                System.out.println("PDF文件第" + (i+1) + "页转化图片开始");
                BufferedImage image = renderer.renderImageWithDPI(i, 256, ImageType.RGB);
                int imgWidth = image.getWidth();
                int imgHeight = image.getHeight();
                System.out.println("图片高度为："+ imgHeight + "；图片宽度为：" + imgWidth);
                BufferedImage srcImage = resize(image, imgWidth, imgHeight);
                String imgName = prefixImgName + "_" + DateUtil.formatDateToStringNewDefault() + ".png";
                String path = savePath + prefixImgName;
                File folder = new File(path);
                if (!folder.exists() || !folder.isDirectory()) {
                    folder.mkdirs();
                }
                String srcPath = path + "/" + imgName;
                File f = new File(srcPath);
                if (!f.exists()) {
                    f.createNewFile();
                }
                ImageIO.write(srcImage, "PNG", f);
                System.out.println("保存图片名称为 ：  " + imgName);
                System.out.println("成功保存图片到 ：  " + srcPath);
                list.add(preImageUrl + prefixImgName + "/" + imgName);
                System.out.println("PDF文件第" + (i+1) + "页转化图片结束");
            }
            doc.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
//    public static List<String> pdfToImageByOriginalSize(String pdfPath, String savePath, String prefixImgName, String preImageUrl){
//        List<String> list = new ArrayList<String>();
//        try {
//            // load a pdf from a byte buffer
//            File file = new File(pdfPath);
//            RandomAccessFile raf = new RandomAccessFile(file, "r");
//            FileChannel channel = raf.getChannel();
//            ByteBuffer buf = channel.map(FileChannel.MapMode.READ_ONLY, 0, channel.size());
//            PDFFile pdfFile = new PDFFile(buf);
//            int pageNum = pdfFile.getNumPages();
//            System.out.println("PDF文件的总页数为：" + pageNum);
//            for (int i = 1; i <= pageNum; i++) {
//
//                System.out.println("PDF文件第" + i + "页转化图片开始");
//                // draw the first page to an image
//                PDFPage page = pdfFile.getPage(i);
//
//                // get the width and height for the doc at the default zoom
//                Rectangle rect = new Rectangle(0, 0, (int) page.getBBox().getWidth(), (int) page.getBBox().getHeight());
//                int imgWidth = (int)rect.getWidth()*3;
//                int imgHeight = (int)rect.getHeight()*3;
//                System.out.println("图片高度为："+ imgHeight + "；图片宽度为：" + imgWidth);
//                // generate the image
//                Image img = page.getImage(
//                        imgWidth, // width
//                        imgHeight, // height
//                        rect, // clip rect
//                        null, // null for the ImageObserver
//                        true, // fill background with white
//                        true // block until drawing is done
//                );
//
//                BufferedImage tag = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_RGB);
//                tag.getGraphics().drawImage(img, 0, 0, imgWidth, imgHeight, null);
//                String imgName = prefixImgName + "_" + DateUtil.formatDateToStringNewDefault() + ".png";
//                String path = savePath + prefixImgName;
//                File folder = new File(path);
//                if (!folder.exists() || !folder.isDirectory()) {
//                    folder.mkdirs();
//                }
//                String srcPath = path + "/" + imgName;
//                File f = new File(srcPath);
//                if (!f.exists()) {
//                    f.createNewFile();
//                }
//                System.out.println("保存图片名称为 ：  " + imgName);
//                System.out.println("成功保存图片到 ：  " + srcPath);
//                FileOutputStream out = new FileOutputStream(srcPath); // 输出到文件流
//                ImageIO.write(tag, "JPEG", out);
//                out.close();
//                list.add(preImageUrl + prefixImgName + "/" + imgName);
//                System.out.println("PDF文件第" + i + "页转化图片结束");
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return list;
//    }
    public static java.util.List<String> pdfToImageByOriginalSize1(String pdfPath, String savePath, String prefixImgName, String preImageUrl){
        java.util.List<String> list = new ArrayList<String>();
        try {
            File file = new File(pdfPath);
            PDDocument doc = PDDocument.load(file);
            PDFRenderer renderer = new PDFRenderer(doc);
            int pageCount = doc.getNumberOfPages();
            for (int i = 1; i <= pageCount; i++) {

                System.out.println("PDF文件第" + i + "页转化图片开始");
                BufferedImage image = renderer.renderImageWithDPI(i, 256, ImageType.RGB);
                int imgWidth = image.getWidth()*3;
                int imgHeight = image.getHeight()*3;
                System.out.println("图片高度为："+ imgHeight + "；图片宽度为：" + imgWidth);
                BufferedImage srcImage = resize(image, imgWidth, imgHeight);
                String imgName = prefixImgName + "_" + DateUtil.formatDateToStringNewDefault() + ".png";
                String path = savePath + prefixImgName;
                File folder = new File(path);
                if (!folder.exists() || !folder.isDirectory()) {
                    folder.mkdirs();
                }
                String srcPath = path + "/" + imgName;
                File f = new File(srcPath);
                if (!f.exists()) {
                    f.createNewFile();
                }
                ImageIO.write(srcImage, "PNG", f);
                System.out.println("保存图片名称为 ：  " + imgName);
                System.out.println("成功保存图片到 ：  " + srcPath);
                list.add(preImageUrl + prefixImgName + "/" + imgName);
                System.out.println("PDF文件第" + i + "页转化图片结束");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    private static BufferedImage resize(BufferedImage source, int targetW, int targetH) {
        int type = source.getType();
        BufferedImage target = null;
        double sx = (double) targetW / source.getWidth();
        double sy = (double) targetH / source.getHeight();
        if (sx > sy) {
            sx = sy;
            targetW = (int) (sx * source.getWidth());
        } else {
            sy = sx;
            targetH = (int) (sy * source.getHeight());
        }
        if (type == BufferedImage.TYPE_CUSTOM) {
            ColorModel cm = source.getColorModel();
            WritableRaster raster = cm.createCompatibleWritableRaster(targetW, targetH);
            boolean alphaPremultiplied = cm.isAlphaPremultiplied();
            target = new BufferedImage(cm, raster, alphaPremultiplied, null);
        } else {
            target = new BufferedImage(targetW, targetH, type);
        }
        Graphics2D g = target.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.drawRenderedImage(source, AffineTransform.getScaleInstance(sx, sy));
        g.dispose();
        return target;
    }
    /*
    *   图片转化成PDF：按图片大小
    * */
    public static void imageToPDF(String imagePath, String pdfPath){
        try {
            BufferedImage img = ImageIO.read(new File(imagePath));
            FileOutputStream fos = new FileOutputStream(pdfPath);
            File f = new File(pdfPath);
            if (!f.exists()) {
                f.createNewFile();
            }
            Document doc = new Document(null, 0, 0, 0, 0);
            doc.setPageSize(new com.itextpdf.text.Rectangle(img.getWidth(), img.getHeight()));
            com.itextpdf.text.Image image = com.itextpdf.text.Image.getInstance(imagePath);
            PdfWriter.getInstance(doc, fos);
            doc.open();
            doc.add(image);
            doc.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    public static List<Integer> getPdfWidthAndHeight(String pdfPath, int pageNum){
        List<Integer> list = new ArrayList<Integer>();
        try{
            PdfReader pdfReader = new PdfReader(pdfPath);
            System.out.println("总页数 ：" + pdfReader.getNumberOfPages());
            com.itextpdf.text.Rectangle rectangle =  pdfReader.getPageSize(pageNum);
            list.add((int)rectangle.getWidth());
            list.add((int)rectangle.getHeight());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    public static void main(String[] args){
//        imageToPDF("f://1.png","f://0801.pdf");
//        PDFUtil.pdfToImage("f://1.pdf", "f:", 1488, 2105, "test");
//        List<Integer> list = PDFUtil.getPdfWidthAndHeight("f://1.pdf");
//        for (Integer i : list) {
//            System.out.println(i);
//        }
//        float width = (float)list.get(0)/1488;
//        float height = (float)list.get(1)/2105;
//        System.out.println(width  + "---" + height);
//        System.out.println(width*1180  + "---" + height*1750);
//        PDFWaterMarkUtil.waterMark("f:\\1.pdf", "f:\\sign_new.png", width*1180, 841 - height*1750 - height*128, "f:\\sign_pdf_new.pdf", "");
//        PDFUtil.pdfToImageByWidthScale("f://1.pdf", "f:", 1480, "0701");
    }
}
