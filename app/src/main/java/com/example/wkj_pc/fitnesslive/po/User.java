package com.example.wkj_pc.fitnesslive.po;

import org.litepal.crud.DataSupport;

/**
 * Created by wkj_pc on 2017/6/13.
 */

public class User extends DataSupport{
    private int uid;
    private String account;
    private String name;
    private String password;
    private String gender;
    private String nickname;//昵称
    private String email;
    private String idcard;
    private String phonenum;
    private Integer role;       //1:main admin 2:personal admin 3: user;
    private String amatar;
    private Integer age;
    private String token;
    private Boolean islive;
    private String personalsign;
    private Integer fansnum;//粉丝数量
    private Integer grade;  //用户积分
    private Integer attentionnum;  //我的关注数量
    private String livebigpic;  //直播大图
    private String createtime;
    public String getPersonalsign() {
        return personalsign;
    }

    public void setPersonalsign(String personalsign) {
        this.personalsign = personalsign;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getLivebigpic() {
        return livebigpic;
    }

    public void setLivebigpic(String livebigpic) {
        this.livebigpic = livebigpic;
    }

    public Integer getAttentionnum() {
        return attentionnum;
    }
    public Boolean getIslive() {
        return islive;
    }

    public void setIslive(Boolean islive) {
        this.islive = islive;
    }

    public void setAttentionnum(Integer attentionnum) {
        this.attentionnum = attentionnum;
    }

    public Integer getFansnum() {
        return fansnum;
    }

    public void setFansnum(Integer fansnum) {
        this.fansnum = fansnum;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public User(){}

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getPhonenum() {
        return phonenum;
    }

    public void setPhonenum(String phonenum) {
        this.phonenum = phonenum;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public String getAmatar() {
        return amatar;
    }

    public void setAmatar(String amatar) {
        this.amatar = amatar;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
