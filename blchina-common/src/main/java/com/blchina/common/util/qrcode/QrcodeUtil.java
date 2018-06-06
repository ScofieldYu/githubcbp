package com.blchina.common.util.qrcode;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import javax.servlet.http.HttpServletResponse;
import java.util.Hashtable;

public class QrcodeUtil {

    public final static int WIDTH = 215;
    public final static int HEIGHT = 215;

    public static void createQrcode(String regUrl,HttpServletResponse response) throws WriterException {
        //二维码的图片格式
        String format = "jpg";
        Hashtable hints = new Hashtable();
        //内容所使用编码
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        BitMatrix bitMatrix = new MultiFormatWriter().encode(regUrl,
                BarcodeFormat.QR_CODE, WIDTH, HEIGHT, hints);
        MatrixToImageWriter.toWrite(bitMatrix, format, response);
    }
}
