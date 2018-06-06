package com.blchina.common.util.image;

import com.blchina.common.util.string.StringUtil;
import org.apache.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.image.*;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;

public class ImagesUtil {

    private static Logger log = Logger.getLogger(ImagesUtil.class);
    //几种常见的图片格式
    public final static String IMAGE_TYPE_GIF         = "gif";    // 图形交换格式
    public final static String IMAGE_TYPE_JPG         = "jpg";    // 联合照片专家组
    public final static String IMAGE_TYPE_JPEG        = "jpeg";   // 联合照片专家组
    public final static String IMAGE_TYPE_BMP         = "bmp";    // 英文Bitmap（位图）的简写，它是Windows操作系统中的标准图像文件格式
    public final static String IMAGE_TYPE_PNG         = "png";    // 可移植网络图形
    public final static String IMAGE_TYPE_PSD         = "psd";    // Photoshop的专用格式Photoshop

    //水印位置
    public final static int WATERMARK_CENTER          = 1;        //水印位置，中央
    public final static int WATERMARK_LEFT_TOP        = 2;        //水印位置，左上角
    public final static int WATERMARK_LEFT_BOTTOM     = 3;        //水印位置，左下角
    public final static int WATERMARK_RIGHT_TOP       = 4;        //水印位置，右上角
    public final static int WATERMARK_RIGHT_BOTTOM    = 5;        //水印位置，右下角
    public final static int WATERMARK_DEFAULTOFFSET   = 10;       //水印位置默认偏移量，如左上角10,10

    public static void main(String[] args) {
        try {
            String path = "f:/11438440895facd9b0o.jpg";
            thumbnail_w_h(path, 570, 427, path);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    /*
    * 按照百分比压缩
    * */
    public static void  compressImageByPercent(String srcPath,  String newPath, double percent){
        BufferedImage bi = null;
        try {
            bi = ImageIO.read(new File(srcPath));
            double srcWidth = bi.getWidth(); // 源图宽度
            double srcHeight = bi.getHeight(); // 源图高度
            thumbnail(srcPath, (int) (srcWidth*percent), (int) (srcHeight*percent), newPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

     /**
     * 按照 宽高 比例压缩
     *
     * @param width
     * @param height
     * @param
     * @throws java.io.IOException
     */
    public static void thumbnail_w_h(String srcPath, int width, int height,
                                     String newPath) {
        log.info("按照宽高比例压缩");
        BufferedImage bi = null;
        try {
            bi = ImageIO.read(new File(srcPath));
            double srcWidth = bi.getWidth(); // 源图宽度
            double srcHeight = bi.getHeight(); // 源图高度

            double scale = 1;

            if (width > 0) {
                scale = width / srcWidth;
            }
            if (height > 0) {
                scale = height / srcHeight;
            }
            if (width > 0 && height > 0) {
                scale = height / srcHeight < width / srcWidth ? height / srcHeight
                        : width / srcWidth;
            }

            thumbnail(srcPath, (int) (srcWidth * scale), (int) (srcHeight * scale), newPath);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    /**
     * 按照固定宽高原图压缩
     *
     * @param width
     * @param height
     * @throws java.io.IOException
     */
    public static void thumbnail(String srcPath, int width, int height,
                                 String newPath) {
        BufferedImage BI = null;
        try {
            BI = ImageIO.read(new File(srcPath));
            Image image = BI.getScaledInstance(width, height, Image.SCALE_SMOOTH);

            BufferedImage tag = new BufferedImage(width, height,
                    BufferedImage.TYPE_INT_RGB);
            Graphics g = tag.getGraphics();
            g.setColor(Color.RED);
            g.drawImage(image, 0, 0, null); // 绘制处理后的图
            g.dispose();
            ImageIO.write(tag, "JPEG", new FileOutputStream(newPath));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    /**
     * 缩放图像（按比例缩放）
     * @param sourceSrc 源图像文件地址
     * @param outSrc 缩放后的图像地址
     * @param scale 缩放比例
     */
//    public final static void scale(String sourceSrc, String outSrc, float scale, float quality) {
//
//        try {
//
//            sourceSrc = StringUtil.clearPath(sourceSrc);
//            outSrc = StringUtil.clearPath(outSrc);
//
//            File sourceFile = new File(sourceSrc);
//            File outFile = new File(outSrc);
//            ImageIcon sourceII = new ImageIcon(sourceFile.getCanonicalPath());
//            Image sourceImage = sourceII.getImage();
//            Image outImage = null;
//
//            int width = sourceImage.getWidth(null);
//            int height = sourceImage.getHeight(null);
//            width = (int) MathUtil.mul(width, scale);
//            height = (int) MathUtil.mul(height, scale);
//
//            outImage = sourceImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
//            Image temp = new ImageIcon(outImage).getImage();
//            BufferedImage bi = new BufferedImage(temp.getWidth(null), temp.getHeight(null), BufferedImage.TYPE_INT_RGB);
//
//            Graphics2D g = bi.createGraphics();
//            g.setColor(Color.white);
//            g.fillRect(0, 0, temp.getWidth(null), temp.getHeight(null));
//            g.drawImage(temp, 0, 0, null);
//            g.dispose();
//
//            FileOutputStream fos = new FileOutputStream(outFile);
//
////            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(fos);
////            JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(bi);
////            param.setQuality(quality, true);
////            encoder.setJPEGEncodeParam(param);
////            encoder.encode(bi);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }


    /**
     * 缩放图像（按高度和宽度缩放）
     * @param sourceSrc 源图像文件地址
     * @param outSrc 缩放后的图像地址
     * @param width 缩放后的宽度
     * @param height 缩放后的高度
     * @param quality 缩放图片质量0~1
     * @param fillSide 比例不对时是否需要补白：true为补白; false为不补白;
     */
//    public final static void scaleByFixed(String sourceSrc, String outSrc, int width, int height, float quality, boolean fillSide) {
//
//        try {
//
//            sourceSrc = StringUtil.clearPath(sourceSrc);
//            outSrc = StringUtil.clearPath(outSrc);
//
//            if (quality > 1) quality = 1;
//
//            File sourceFile = new File(sourceSrc);
//            File outFile = new File(outSrc);
//            ImageIcon sourceII = new ImageIcon(sourceFile.getCanonicalPath());
//            Image sourceImage = sourceII.getImage();
//            Image outImage = null;
//
//            int iWidth = sourceImage.getWidth(null);
//            int iHeight = sourceImage.getHeight(null);
//
//            if (iHeight >= height || iWidth >= width) {
//                if (iHeight > iWidth) {
//                    outImage = sourceImage.getScaledInstance((height * iWidth) / iHeight, height, Image.SCALE_SMOOTH);
//                } else {
//                    outImage = sourceImage.getScaledInstance(width, (width * iHeight)/ iWidth, Image.SCALE_SMOOTH);
//                }
//            }else {
//                outImage = sourceImage.getScaledInstance(iWidth, iHeight, Image.SCALE_SMOOTH);
//            }
//            Image temp = new ImageIcon(outImage).getImage();
//            BufferedImage bi = null;
//
//            if (fillSide) { //补白
//                bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
//                Graphics2D g = bi.createGraphics();
//                g.setColor(Color.white);
//                g.fillRect(0, 0, width, height);
//                if (width == temp.getWidth(null))
//                    g.drawImage(temp, 0, (height - temp.getHeight(null)) / 2, temp.getWidth(null), temp.getHeight(null), Color.white, null);
//                else
//                    g.drawImage(temp, (width - temp.getWidth(null)) / 2, 0, temp.getWidth(null), temp.getHeight(null), Color.white, null);
//                g.dispose();
//            }else {
//                bi = new BufferedImage(temp.getWidth(null), temp.getHeight(null), BufferedImage.TYPE_INT_RGB);
//                Graphics2D g = bi.createGraphics();
//                g.setColor(Color.white);
//                g.fillRect(0, 0, temp.getWidth(null), temp.getHeight(null));
//                g.drawImage(temp, 0, 0, null);
//                g.dispose();
//            }
//
//            FileOutputStream fos = new FileOutputStream(outFile);
//
////            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(fos);
////            JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(bi);
////            param.setQuality(quality, true);
////            encoder.setJPEGEncodeParam(param);
////            encoder.encode(bi);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }


    /**
     * 图像切割(按指定起点坐标和宽高切割)
     * @param sourceSrc 源图像地址
     * @param outSrc 切片后的图像地址
     * @param x 目标切片起点坐标X
     * @param y 目标切片起点坐标Y
     * @param width 目标切片宽度
     * @param height 目标切片高度
     */
    public final static void cut(String sourceSrc, String outSrc, int x, int y, int width, int height) {

        try {

            sourceSrc = StringUtil.clearPath(sourceSrc);
            outSrc = StringUtil.clearPath(outSrc);

            // 读取源图像
            BufferedImage bi = ImageIO.read(new File(sourceSrc));
            int srcWidth = bi.getHeight(); // 源图宽度
            int srcHeight = bi.getWidth(); // 源图高度
            if (srcWidth > 0 && srcHeight > 0) {
                Image image = bi.getScaledInstance(srcWidth, srcHeight, Image.SCALE_DEFAULT);
                // 四个参数分别为图像起点坐标和宽高
                // 即: CropImageFilter(int x,int y,int width,int height)
                ImageFilter cropFilter = new CropImageFilter(x, y, width, height);
                Image img = Toolkit.getDefaultToolkit().createImage( new FilteredImageSource(image.getSource(), cropFilter));
                BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
                Graphics g = tag.getGraphics();
                g.drawImage(img, 0, 0, width, height, null); // 绘制切割后的图
                g.dispose();
                // 输出为文件
                ImageIO.write(tag, IMAGE_TYPE_JPEG, new File(outSrc));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 图像类型转换：GIF->JPG、GIF->PNG、PNG->JPG、PNG->GIF(X)、BMP->PNG
     * @param formatName 包含格式非正式名称的 String：如JPG、JPEG、GIF等
     * @param sourceSrc 源图像地址
     * @param outSrc 目标图像地址
     */
    public final static void convert(String sourceSrc, String outSrc, String formatName) {

        try {

            sourceSrc = StringUtil.clearPath(sourceSrc);
            outSrc = StringUtil.clearPath(outSrc);

            File file = new File(sourceSrc);
            file.canRead();
            file.canWrite();

            BufferedImage bi = ImageIO.read(file);
            ImageIO.write(bi, formatName, new File(outSrc));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 彩色转为黑白
     * @param sourceSrc 源图像地址
     * @param outSrc 目标图像地址
     */
    public final static void gray(String sourceSrc, String outSrc) {

        try {

            BufferedImage bi = ImageIO.read(new File(sourceSrc));
            ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);
            ColorConvertOp op = new ColorConvertOp(cs, null);
            bi = op.filter(bi, null);
            ImageIO.write(bi, IMAGE_TYPE_JPEG, new File(outSrc));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 给图片添加文字水印
     * @param sourceSrc 源图像地址
     * @param outSrc 目标图像地址
     * @param watermarkText 水印文字
     * @param fontName 水印的字体名称
     * @param fontStyle 水印的字体样式
     * @param color 水印的字体颜色
     * @param fontSize 水印的字体大小
     * @param location 水印位置
     * @param x 水印位置x方向修正值
     * @param y 水印位置y方向修正值
     * @param alpha 透明度：alpha 必须是范围 [0.0, 1.0] 之内（包含边界值）的一个浮点数字
     */
    public final static void pressText(String sourceSrc, String outSrc, String watermarkText, String fontName, int fontStyle, Color color, int fontSize, int location, int x, int y, float alpha) {

        try {

            sourceSrc = StringUtil.clearPath(sourceSrc);
            outSrc = StringUtil.clearPath(outSrc);

            File file = new File(sourceSrc);
            Image image = ImageIO.read(file);
            int width = image.getWidth(null);
            int height = image.getHeight(null);

            BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = bi.createGraphics();
            g.drawImage(image, 0, 0, width, height, null);

            g.setColor(color);
            g.setFont(new Font(fontName, fontStyle, fontSize));
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));

            // 绘制水印文字
            int mWidth = StringUtil.getLength(watermarkText) * fontSize;
            int mHeight = fontSize;
            int offset_x = 0;
            int offset_y = 0;
            switch (location){
                case WATERMARK_LEFT_TOP:
                    offset_x = WATERMARK_DEFAULTOFFSET; offset_y = WATERMARK_DEFAULTOFFSET;
                    break;
                case WATERMARK_LEFT_BOTTOM :
                    offset_x = WATERMARK_DEFAULTOFFSET; offset_y = height - mHeight - WATERMARK_DEFAULTOFFSET;
                    break;
                case WATERMARK_RIGHT_TOP :
                    offset_x = width - mWidth - WATERMARK_DEFAULTOFFSET; offset_y = WATERMARK_DEFAULTOFFSET;
                    break;
                case WATERMARK_RIGHT_BOTTOM :
                    offset_x = width - mWidth - WATERMARK_DEFAULTOFFSET; offset_y = height - mHeight - WATERMARK_DEFAULTOFFSET;
                    break;
                case WATERMARK_CENTER :
                default:
                    offset_x = (width - mWidth) / 2; offset_y = (height - mHeight) / 2;
                    break;
            }

            g.drawString(watermarkText, offset_x + x, offset_y + y);
            g.dispose();

            ImageIO.write(bi, IMAGE_TYPE_JPEG, new File(outSrc));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 给图片添加图片水印
     * @param sourceSrc 源图像地址
     * @param outSrc 目标图像地址
     * @param watermarkSrc 水印图片
     * @param location 水印位置
     * @param alpha 透明度：alpha 必须是范围 [0.0, 1.0] 之内（包含边界值）的一个浮点数字
     */
    public final static void pressImage(String sourceSrc, String outSrc, String watermarkSrc, int location, float alpha) {

        try {

            sourceSrc = StringUtil.clearPath(sourceSrc);
            outSrc = StringUtil.clearPath(outSrc);
            watermarkSrc = StringUtil.clearPath(watermarkSrc);

            File file = new File(sourceSrc);
            Image image = ImageIO.read(file);
            int width = image.getWidth(null);
            int height = image.getHeight(null);

            BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = bi.createGraphics();
            g.drawImage(image, 0, 0, width, height, null);
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));

            // 水印文件
            Image markImage = ImageIO.read(new File(watermarkSrc));
            int mWidth = markImage.getWidth(null);
            int mHeight = markImage.getHeight(null);
            int offset_x = 0;
            int offset_y = 0;
            switch (location){
                case WATERMARK_LEFT_TOP:
                    offset_x = WATERMARK_DEFAULTOFFSET; offset_y = WATERMARK_DEFAULTOFFSET;
                    break;
                case WATERMARK_LEFT_BOTTOM :
                    offset_x = WATERMARK_DEFAULTOFFSET; offset_y = height - mHeight - WATERMARK_DEFAULTOFFSET;
                    break;
                case WATERMARK_RIGHT_TOP :
                    offset_x = width - mWidth - WATERMARK_DEFAULTOFFSET; offset_y = WATERMARK_DEFAULTOFFSET;
                    break;
                case WATERMARK_RIGHT_BOTTOM :
                    offset_x = width - mWidth - WATERMARK_DEFAULTOFFSET; offset_y = height - mHeight - WATERMARK_DEFAULTOFFSET;
                    break;
                case WATERMARK_CENTER :
                default:
                    offset_x = (width - mWidth) / 2; offset_y = (height - mHeight) / 2;
                    break;
            }

            g.drawImage(markImage, offset_x, offset_y, mWidth, mHeight, null);
            g.dispose();

            ImageIO.write(bi, IMAGE_TYPE_JPEG, new File(outSrc));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 下载网络图片并保存到本地服务器
     * @param fileUrl   网络图片的完整Url地址
     * @param outSrc    要保存的本地完整地址
     * @return
     * @throws Exception
     */
    public static void download(String fileUrl, String outSrc) throws Exception {

        try {

            fileUrl = StringUtil.clearPath(fileUrl);
            outSrc = StringUtil.clearPath(outSrc);

            URL url = new URL(fileUrl);

            URLConnection con = url.openConnection();
            con.setConnectTimeout(5*1000);
            InputStream is = con.getInputStream();

            int len;                    //读取到的数据长度
            byte[] bs = new byte[1024]; //1K的数据缓冲

            File sf = new File(outSrc);
            if (!sf.getParentFile().exists()) {
                sf.getParentFile().mkdirs();
            }
            OutputStream os = new FileOutputStream(outSrc);

            //开始读取
            while ((len = is.read(bs)) != -1) {
                os.write(bs, 0, len);
            }
            os.close();
            is.close();

        } catch (Exception ex) {

            System.out.println("下载图片失败,ex:" + ex.getMessage());
        }
    }


    /**
     * 根据文件名 获取文件的后缀名
     * @param fileUrl
     * @return
     */
    public static String getExtension(String fileUrl){
        return fileUrl.substring(fileUrl.lastIndexOf("."), fileUrl.length());
    }


    /**
     * 程序入口：用于测试
     * @param args
     */
//    public static void main(String[] args) {
        // 1-缩放图像：
        // 方法一：按比例缩放
        //ImageUtil.scale("/Users/nowind/Documents/dev/#test/images/01/a_00.jpg", "/Users/nowind/Documents/dev/#test/images/01/a_01.jpg", 0.5, 0.85);
        // 方法二：按高度和宽度缩放
        //ImageUtil.scaleByFixed("/Users/nowind/Documents/dev/#test/images/01/a_00.jpg", "/Users/nowind/Documents/dev/#test/images/01/a_02.jpg", 200, 200, 1, false);
        //ImageUtil.scaleByFixed("/Users/nowind/Documents/dev/#test/images/01/a_00.jpg", "/Users/nowind/Documents/dev/#test/images/01/a_03.jpg", 200, 200, 1, true);


//        // 2-切割图像：
//        // 方法一：按指定起点坐标和宽高切割
//        ImageUtil.cut("e:/abc.jpg", "e:/abc_cut.jpg", 0, 0, 400, 400 );


//        // 3-图像类型转换：
//        ImageUtil.convert("e:/abc.jpg", "GIF", "e:/abc_convert.gif");


//        // 4-彩色转黑白：
//        ImageUtil.gray("/Users/nowind/Documents/dev/#test/images/01/a_00.jpg", "/Users/nowind/Documents/dev/#test/images/01/a_gray.jpg");


//        // 5-给图片添加文字水印：
//        // 方法一：
//        ImageUtil.pressText("/Users/nowind/Documents/dev/#test/images/01/a_00.jpg", "/Users/nowind/Documents/dev/#test/images/01/a_c.jpg","我是黑小伙", "microsoft yahei", 600, java.awt.Color.getColor("blue"), 34,WATERMARK_CENTER, 0, 20, 0.5f);
//        ImageUtil.pressText("/Users/nowind/Documents/dev/#test/images/01/a_00.jpg", "/Users/nowind/Documents/dev/#test/images/01/a_lt.jpg","我是黑小伙", "microsoft yahei", 600, java.awt.Color.getColor("blue"), 34, WATERMARK_LEFT_TOP, 0, 20, 0.5f);
//        ImageUtil.pressText("/Users/nowind/Documents/dev/#test/images/01/a_00.jpg", "/Users/nowind/Documents/dev/#test/images/01/a_lb.jpg","我是黑小伙", "microsoft yahei", 600, java.awt.Color.getColor("blue"), 34, WATERMARK_LEFT_BOTTOM, 0, 20, 0.5f);
//        ImageUtil.pressText("/Users/nowind/Documents/dev/#test/images/01/a_00.jpg", "/Users/nowind/Documents/dev/#test/images/01/a_rt.jpg","我是黑小伙", "microsoft yahei", 600, java.awt.Color.getColor("blue"), 34, WATERMARK_RIGHT_TOP, 0, 20, 0.5f);
//        ImageUtil.pressText("/Users/nowind/Documents/dev/#test/images/01/a_00.jpg", "/Users/nowind/Documents/dev/#test/images/01/a_rb.jpg","我是黑小伙", "microsoft yahei", 600, java.awt.Color.getColor("blue"), 34, WATERMARK_RIGHT_BOTTOM, 0, 20, 0.5f);

//        // 6-给图片添加图片水印：
//        ImageUtil.pressImage("/Users/nowind/Documents/dev/#test/images/01/a_00.jpg", "/Users/nowind/Documents/dev/#test/images/01/a_c.jpg","/Users/nowind/Documents/dev/#test/images/01/i-watermark.png", WATERMARK_CENTER, 0.25f);
//        ImageUtil.pressImage("/Users/nowind/Documents/dev/#test/images/01/a_00.jpg", "/Users/nowind/Documents/dev/#test/images/01/a_lt.jpg","/Users/nowind/Documents/dev/#test/images/01/i-watermark.png", WATERMARK_LEFT_TOP, 0.25f);
//        ImageUtil.pressImage("/Users/nowind/Documents/dev/#test/images/01/a_00.jpg", "/Users/nowind/Documents/dev/#test/images/01/a_lb.jpg","/Users/nowind/Documents/dev/#test/images/01/i-watermark.png", WATERMARK_LEFT_BOTTOM, 0.25f);
//        ImageUtil.pressImage("/Users/nowind/Documents/dev/#test/images/01/a_00.jpg", "/Users/nowind/Documents/dev/#test/images/01/a_rt.jpg","/Users/nowind/Documents/dev/#test/images/01/i-watermark.png", WATERMARK_RIGHT_TOP, 0.25f);
//        ImageUtil.pressImage("/Users/nowind/Documents/dev/#test/images/01/a_00.jpg", "/Users/nowind/Documents/dev/#test/images/01/a_rb.jpg","/Users/nowind/Documents/dev/#test/images/01/i-watermark.png", WATERMARK_RIGHT_BOTTOM, 0.25f);
//    }
}
