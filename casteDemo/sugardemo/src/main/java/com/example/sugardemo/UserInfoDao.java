package com.example.sugardemo;

import java.util.List;

/**
 * Created by Administrator on 2017/10/20 0020.
 */

public class UserInfoDao {
    /*
    * 获取所有家庭成员信息
    * */
    public List<UserInfo> getAllUsers(){
        List<UserInfo> userInfos = UserInfo.listAll(UserInfo.class);
        return userInfos;
    }

    /*
    * 添加成员信息
    * */
    public long addUser(UserInfo info){
        long id = -1;
        id = info.save();
        return id;
    }

    /*
    * 根据ID查询成员信息
    * */
    public UserInfo getUserInfo(int userId){
        return UserInfo.findById(UserInfo.class,userId);
    }

    /*
    * 根据昵称查询
    * */
    public List<UserInfo> getUser(String nickName){
        List<UserInfo> userInfos = UserInfo.find(UserInfo.class,"userName=?",nickName);
        if(userInfos != null && userInfos.size() != 0){
            return userInfos;
        }else{
            return null;
        }

    }

    /*
    * 删除成员
    * */
    public void delUser(long userId){
        UserInfo userInfo = UserInfo.findById(UserInfo.class,userId);
        if(userInfo != null){
            userInfo.delete();
        }
    }

    /*
    * 更新成员信息
    * */
    public long updateUserInfo(UserInfo info){
        UserInfo info1 = UserInfo.findById(UserInfo.class,info.getId());
        if(info1 != null){
            info.save();
        }
        return -1;
    }
}
