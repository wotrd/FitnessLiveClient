package com.example.wkj_pc.fitnesslive.tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by wotrd on 2017/12/18.
 */

public class TimeUtils {
    /**
     * 根据秒数计算过去的时间，并显示
     * @param time
     */
    public static String showTime(long time){
//        SimpleDateFormat format=new SimpleDateFormat("yyyy/MM/dd-hh:MM:ss");
        long currentTime = new Date().getTime();
//        long recieveTime = 0;
//        try{
//            recieveTime = format.parse(time).getTime();
//        }catch (ParseException  e){
//            e.printStackTrace();
//        }
        long time1 = currentTime - time;
        System.out.println("------秒数="+time1);
        time1 = time1 / 1000;   //得到秒数
        if (time1<60){
//            System.out.printf("多少秒"+time1);
            return time1+"秒前";
        }
        long m = time1 / 60;    //  得到分钟数
        if (m<60){
//            System.out.println("-----多少分钟"+m);
            return m+"分钟前";
        }
        long h = m / 60;    //得到小时数
        if (h<24){
//            System.out.println("----小时前"+h);
            return h+"小时前";
        }
        long d = h / 24; //得到天数
//        if (d<30){
            return d+"天前";
//        }
    }
}
