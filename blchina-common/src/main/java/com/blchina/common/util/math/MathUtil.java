package com.blchina.common.util.math;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class MathUtil {

    private static final int DEF_DIV_SCALE = 10;

    /**
     * 获取平均值
     * @param list
     * @return
     */
    public static double getAverage(List<Double> list) {

        if (list== null || list.isEmpty()) return 0;

        double total = 0;
        for (int i = 0; i < list.size(); i++) {
            total += list.get(i);
        }
        double avg = total/list.size();
        return avg;
    }

    /**
     * 加法运算
     *
     * @param v1
     * @param v2
     * @return
     */
    public static double add(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2).doubleValue();
    }

    public static String add(String v1, String v2) {
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.add(b2).setScale(2, BigDecimal.ROUND_HALF_UP).toString();
    }

    /**
     * 减法运算
     * @param v1
     * @param v2
     * @return
     */
    public static double sub(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.subtract(b2).doubleValue();
    }

    public static double sub(String v1, String v2) {
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.subtract(b2).doubleValue();
    }

    /**
     * 乘法运算
     * @param v1
     * @param v2
     * @return
     */
    public static double mul(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.multiply(b2).doubleValue();
    }

    public static String mul(double v1, int v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Integer.toString(v2));
        return b1.multiply(b2).toString();
    }

    public static int mul(float v1, int v2) {
        BigDecimal b1 = new BigDecimal(Float.toString(v1));
        BigDecimal b2 = new BigDecimal(Integer.toString(v2));
        return b1.multiply(b2).intValue();
    }

    public static int mul(String v1, int v2) {
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(Integer.toString(v2));
        return b1.multiply(b2).intValue();
    }

    /**
     * 除法运算
     * @param v1
     * @param v2
     * @param scale
     * @return
     */
    public static double div(double v1, double v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static double div(int v1, int v2) {
        return div(Integer.toString(v1), Integer.toString(v2));
    }

    public static double div(int v1, int v2, int scale) {
        return div(Integer.toString(v1), Integer.toString(v2), scale);
    }

    public static double div(String v1, int v2) {
        return div(v1, Integer.toString(v2));
    }

    public static double div(String v1, String v2) {
        return div(v1, v2, 1);
    }

    public static double div(String v1, String v2, int scale) {
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }



    /**
     * 四舍五入运算
     * @param v
     * @param scale
     * @return
     */
    public static double round(double v, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b = new BigDecimal(Double.toString(v));
        BigDecimal one = new BigDecimal("1");
        return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static String movePointLeft(String value, int length) {
        BigDecimal d2 = new BigDecimal(value);
        return d2.movePointLeft(length).toString();
    }

    public static int movePointRight(String value, int length) {
        BigDecimal d2 = new BigDecimal(value);
        return d2.movePointRight(length).intValue();
    }

    public static String getPercentFormat(double v)
    {
        NumberFormat format = NumberFormat.getPercentInstance(Locale.CHINA);
        v = round(v,4);
        format.setMaximumFractionDigits(2);
        format.setMinimumFractionDigits(2);
        return format.format(v);
    }

    public static String getPercentFormat(double v,int v1)
    {
        NumberFormat format = NumberFormat.getPercentInstance(Locale.CHINA);
        v = round(v,v1+2);
        format.setMaximumFractionDigits(v1);
        format.setMinimumFractionDigits(v1);
        return format.format(v);
    }

    public static double add(double[] vs)
    {
        double v = 0;
        for(int i=0,n=vs.length;i<n;i++)
        {
            v = add(v,vs[i]);
        }
        return v;
    }

    public static String formatNormal(double v,int fraction)
    {
        StringBuffer sb = new StringBuffer("0.");
        DecimalFormat format = null;
        if(fraction == 0)
        {
            format = new DecimalFormat("#0");
        }else
        {
            for(int i=0;i<fraction;i++)
            {
                sb.append("0");
            }
            format = new DecimalFormat(sb.toString());
        }
        format.setGroupingUsed(false);
        return format.format(v);
    }

    public static double f(double x) {
        double f;
        f =Math.exp(-Math.pow(x, 2));
        return f;
    }
    //梯形法求积分
    public static double getDefiniteIntegralByTrapezium(double x0, double xn,int n) {
        double h = Math.abs(xn - x0) / n;
        double sum = 0;
        if(xn>0){
            for (double xi = 0; xi <= xn; xi = xi + h) {
                sum += (f(xi) + f(xi + h)) *( h / 2);
            }
        }else{
            for (double xi = xn; xi <= 0; xi = xi + h)  {
                sum += -(f(xi) + f(xi + h)) *( h / 2);
            }
        }

        return sum;
    }
    //矩形法求积分
    public static double integral(double x0, double xn ,int n) {
        double h = Math.abs(xn - x0) / 200;
        double sum = 0;
        if(xn>0){
            for (double xi = 0; xi <= xn; xi = xi + h) {

                sum += f(xi) * h;

            }
        }else{
            for (double xi = xn; xi <= 0; xi = xi + h) {

                sum += -(f(xi) * h);

            }
        }


        return sum;
    }




    public static void main(String[] args) {

        String x = add(40.071884 + "", 40.07122257354 + "");
        String y = add(116.313314 + "", 116.31884881 + "");
        System.out.println(x);
        System.out.println(y);
//        double x2 = mul(x, x);
//        double y2 = mul(y, y);

//        double z2 = add(x, y);
//        double z = Math.sqrt(z2);
//        System.out.println(z2);

    }
}
