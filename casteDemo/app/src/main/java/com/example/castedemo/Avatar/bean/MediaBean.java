package com.example.castedemo.Avatar.bean;

/**
 * Created by Administrator on 2017/10/24 0024.
 */

public class MediaBean {
    private String path;
    private int size;
    private String displayName;
    private String sex;

    public MediaBean(){}
    public MediaBean(String path, int size, String displayName) {
        this.setPath(path);
        this.setSize(size);
        this.setDisplayName(displayName);
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
