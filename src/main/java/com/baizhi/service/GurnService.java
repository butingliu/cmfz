package com.baizhi.service;

import com.baizhi.entity.Gurn;

import java.util.List;

public interface GurnService {
    //查询全部
    List<Gurn> queryAllGurn();

    //分页查询
    List<Gurn> queryGurnByPage(Integer page, Integer rows);

    //查询一个
    Gurn queryOneGurn(String id);

    //添加
    void insertGurn(Gurn gurn);

    //删除
    void removeGurn(String id);

    //修改
    void updateGurn(Gurn gurn);
}
