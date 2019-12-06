package com.baizhi.service;

import com.baizhi.entity.MapVo;
import com.baizhi.entity.User;

import java.util.List;

public interface UserService {
    //查询全部
    List<User> queryAllUser();

    //分页查询
    List<User> queryUserByPage(Integer page, Integer rows);

    //查询一个
    User queryOneUser(String id);

    //添加
    void insertUser(User user);

    //删除
    void removeUser(String id);

    //更新----4.补充个人信息接口
    void updateUser(User user);

    Integer queryUserBySexAndDay(String se, Integer da);

    List<MapVo> queryUserBySexAndAddress();


    //1.登陆接口
    User queryUserByPhoneAndPwd(String phone, String pwd);

    //3.注册接口
    User registInterface(String phone);


}
