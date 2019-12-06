package com.baizhi.service;

import com.baizhi.entity.Chap;

import java.util.List;

public interface ChapService {
    //根据fid，分页查询数据
    List<Chap> queryChapByPage(String fid, Integer page, Integer rows);

    //根据fid，查询总数据
    List<Chap> queryAllChapByFid(String fid);

    //添加
    void insertChap(Chap chap);

    //删除
    void removeChap(String id);

    //更新
    void updateChap(Chap chap);

    //查询指定数据
    Chap queryOneChap(String id);

    //void queryAllChapByFid(String );
}
