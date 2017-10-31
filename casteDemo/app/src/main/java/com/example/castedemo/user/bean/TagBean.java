package com.example.castedemo.user.bean;

/**
 * Created by wangchm on 2017/10/28 0028.
 */

public class TagBean {
    private int tagId;
    private String tag;
    private boolean isSel;

    public TagBean() {
    }

    public int getTagId() {
        return tagId;
    }

    public void setTagId(int tagId) {
        this.tagId = tagId;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public boolean isSel() {
        return isSel;
    }

    public void setSel(boolean sel) {
        isSel = sel;
    }
}
