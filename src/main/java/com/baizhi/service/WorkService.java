package com.baizhi.service;

import com.baizhi.entity.Work;

import java.util.List;

public interface WorkService {
    //根据用户id查询对应功课集合
    List<Work> queryAllWorkByUser(String uid);
    //添加功课
    void insertWork(Work work);
    //void queryAllWorkByUser(String );
    //删除功课
    void removeWork(String id);
}
