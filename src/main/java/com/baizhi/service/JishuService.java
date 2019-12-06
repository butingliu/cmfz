package com.baizhi.service;

import com.baizhi.entity.Jishu;

import java.util.List;

public interface JishuService {
    //根据功课删除计数
    void removeJishu(String wid);
    //根据用户和功课展示计数器
    List<Jishu> queryJishuByUidAndWid(String uid,String wid);
    //添加计数器
    void insertJishu(Jishu jishu);
    //根据id删除计数
    void removeJishuById(String id);
    //更新计数器
    void updateJishu(Jishu jishu);
}
