package com.example.wkj_pc.fitnesslive.tools;

/**
 * Created by wotrd on 2017/12/18.
 */

public class TimeUtils {
    /**
     * 根据秒数计算过去的时间，并显示
     */
    public static String showTime(Long time){
        long time1 = time / 1000;   //得到秒数
        if (time1<60){
            System.out.printf("多少秒"+time1);
            return time1+"秒前";
        }
        long m = time1 / 60;    //  得到分钟数
        if (m<60){
            System.out.println("-----多少分钟"+m);
            return m+"分钟前";
        }
        long h = m / 60;    //得到小时数
        if (h<24){
            System.out.println("----小时前"+h);
            return h+"小时前";
        }
        long d = h / 24; //得到天数
//        if (d<30){
            return d+"天前";
//        }
    }
}
