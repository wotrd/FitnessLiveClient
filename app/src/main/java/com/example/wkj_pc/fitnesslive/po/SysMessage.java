package com.example.wkj_pc.fitnesslive.po;

/**
 * Created by wotrd on 2017/12/16.
 */

import org.litepal.crud.DataSupport;

/**
 * 系统推送消息
 */
public class SysMessage extends DataSupport{

    private int smid;
    private String title;
    private String content;
    private String from;
    private String to;
    private int intent;     //意图    1：代表聊天  2代表粉丝   3代表当前在线人数
    private int isRead;
    private String owner;   //查看是全部还是个人
    private String time;
    private String result;
    private int uid;

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getSmid() {
        return this.smid;
    }

    public void setSmid(int smid) {
        this.smid = smid;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFrom() {
        return this.from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return this.to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public int getIntent() {
        return this.intent;
    }

    public void setIntent(int intent) {
        this.intent = intent;
    }

    public int getIsRead() {
        return this.isRead;
    }

    public void setIsRead(int isRead) {
        this.isRead = isRead;
    }

    public String getOwner() {
        return this.owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getTime() {
        return this.time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String  getResult() {
        return this.result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public int getUid() {
        return this.uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }
}
