package com.example.wkj_pc.fitnesslive.po;


/**
 * Created by wkj_pc on 2017/8/7.
 */
/**
 * 直播间传递的消息实体类
 */
public class LiveChattingMessage {
    private int mid;
    private String from;
    private String to;
    private String content;
    private String time;
    private int fansnumber;
    private int intent;     //意图    1：代表聊天  2代表粉丝   3代表当前在线人数
    private boolean result; //处理返回结果

    public int getFansnumber() {
        return fansnumber;
    }

    public void setFansnumber(int fansnumber) {
        this.fansnumber = fansnumber;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public int getMid() {
        return mid;
    }

    public void setMid(int mid) {
        this.mid = mid;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getIntent() {
        return intent;
    }

    public void setIntent(int intent) {
        this.intent = intent;
    }
}
