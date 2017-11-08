package com.example.sugardemo;

import com.orm.SugarRecord;
import com.orm.dsl.Ignore;

/**
 * Created by wangchm on 2017/11/3 0003.
 * 偏好标签
 */

public class UserTag extends SugarRecord {
    private int tagId;
    private String tag;
    @Ignore
    private boolean isSel;

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
