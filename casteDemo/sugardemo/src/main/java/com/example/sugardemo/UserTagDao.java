package com.example.sugardemo;

import java.util.List;

/**
 * Created by wangchm on 2017/11/3 0003.
 * 偏好标签的操作类
 */

public class UserTagDao {
    /*
    * 获取所有标签
    * */
    public List<UserTag> getAllTags(){
        return UserTag.listAll(UserTag.class);
    }

    /*
    * 添加标签
    * */
    public long addTag(UserTag tag){
        long id = -1;
        id = tag.save();
        return id;
    }
}
