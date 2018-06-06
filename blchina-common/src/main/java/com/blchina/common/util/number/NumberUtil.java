package com.blchina.common.util.number;

import com.blchina.common.pattern.PatternUtil;
import com.blchina.common.util.math.MathUtil;
import com.blchina.common.util.string.StringUtil;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Random;

public class NumberUtil {

    public static String getRandomSixNumbers() {
        String[] array = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            sb.append(array[new Random().nextInt(10)]);
        }

        return sb.toString();
    }

    public static String getRandomSixNumbersFirstNoZero() {
        String[] arrayNoZero = {"1", "2", "3", "4", "5", "6", "7", "8", "9"};
        String[] array = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
        StringBuilder sb = new StringBuilder();
        sb.append(arrayNoZero[new Random().nextInt(9)]);
        for (int i = 0; i < 5; i++) {
            sb.append(array[new Random().nextInt(10)]);
        }

        return sb.toString();
    }

    public static boolean isNumber(String num) {
        if (StringUtil.isNullorEmpty(num)) return false;
        String regrex = "^\\d{" + num.length() + "}$";
        return PatternUtil.isMatch(num, regrex);
    }

    public static int getFloatIntValue(float value, int value2) {
        BigDecimal d1 = new BigDecimal(value);
        BigDecimal d2 = new BigDecimal(value2);
        return d1.multiply(d2).intValue();
    }

    /**
     * 截取方式取两位小数
     * @param number
     * @return float类型
     */
    public static float floatScale(float number) {

        return floatScale(number, 2);
    }

    /**
     * 截取方式取小数
     * @param number
     * @param scale 位数
     * @return float类型
     */
    public static float floatScale(float number, int scale) {

        BigDecimal b = new BigDecimal((double)(number));

        return b.setScale(scale, BigDecimal.ROUND_DOWN).floatValue();   //直接删除多余的小数位
    }

    /**
     * 比较两位float类型数据是否相等
     * @param number1
     * @param number2
     * @return
     */
    public static boolean floatEqual(float number1, float number2) {

        float diff = 0.01F;
        return Math.abs(number2 - number1) < diff;
    }

    /**
     * 判断number1是否大于number2
     * @param number1
     * @param number2
     * @return
     */
    public static boolean floatGreater(float number1, float number2) {

        float diff = 0.01F;
        return number1 - number2 > diff;
    }

    /**
     * 计算折扣
     * @param number1
     * @param number2
     * @return 返回如9、9.3
     */
    public static String countDiscount(int number1, int number2) {
        double discount = MathUtil.div((double) number1, (double) number2, 2)*10;
        //String dis = String.valueOf(discount);
        DecimalFormat df = new DecimalFormat("#0.0");
        String dis = df.format(discount);
        return dis.endsWith(".0") ? dis.substring(0, dis.indexOf(".")) : dis;
    }

    /**
     * 将单位为分的金额转化为元单位的金额
     * @param moneyOfFen
     * @return
     */
    public static String convertMoneyToYuan(int moneyOfFen) {
        DecimalFormat df = new DecimalFormat("#,##0.00");
        double db = (double)moneyOfFen / 100;
        return df.format(db);
    }

    /**
     * 将单位为分的金额转化为元单位的金额
     * @param moneyOfFen
     * @return
     */
    public static String convertMoneyToYuan2(int moneyOfFen) {
        DecimalFormat df = new DecimalFormat("###0.00");
        double db = (double)moneyOfFen / 100;
        return df.format(db);
    }

    /**
     * 将单位为元的金额转化为分单位的金额
     * @param moneyOfYuan
     * @return
     */
    public static int convertMoneyToFen(double moneyOfYuan) {
        return convertMoneyToFen(Double.toString(moneyOfYuan));
    }

    public static int convertMoneyToFen(String moneyOfYuan) {
        return MathUtil.mul(moneyOfYuan, 100);
    }

    /**
     * 两数相除并得到百分比
     * @return 格式如 51.12%
     */
    public static String divAndToPercent(int num1, int num2) {
        DecimalFormat pf = new DecimalFormat("#.00%");
        double ret = MathUtil.div(num1, num2, 5);
        String per = pf.format(ret);
        return per;
    }

    //region 为小于10的数值，在前补零，如9->09
    public static String zerofillNumberForPrefix(int number){
        return number >= 10 ? String.valueOf(number) : ("0" + String.valueOf(number));
    }


    /**
     * 计算页数
     * @param lineCount
     * @param numPerPage
     * @return
     */
    public static int getPageCount(int lineCount, int numPerPage) {

        Integer pageCount = (Integer)(lineCount / numPerPage);
        pageCount = lineCount % numPerPage == 0 ? pageCount : pageCount + 1 ;

        return pageCount;
    }

    /********** Test **********/


    public static void main(String[] args) {
//        System.out.println(convertMoneyToYuan(1000322));
//        System.out.println(convertMoneyToFen(67.21));

//        String a = "10.013";
//        int b = 100;
//        BigDecimal d1 = new BigDecimal(a);
//        BigDecimal d2 = new BigDecimal(b);
//        BigDecimal d3 = d1.multiply(d2);
//        System.out.println(d3);
//        System.out.println(d3.longValue());
//        System.out.println(d3.intValue());
//        System.out.println(new BigDecimal("4.015").multiply(new BigDecimal(100)));

//        float fl = NumberUtil.floatScale(35*10/100F);

//        System.out.print(fl);

        String per = countDiscount(1,3);
        System.out.println(per);
    }


}
