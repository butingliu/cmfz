package com.baizhi.service;


import com.baizhi.entity.Album;

import java.util.List;

public interface AlbumService {
    //展示全部专辑
    List<Album> queryAllAlbum();

    //添加专辑
    void insertAlbum(Album album);

    //删除专辑
    void removeAlbum(String id);

    //更新专辑
    void updateAlbum(Album album);

    //更新2
    void updateAlbum2(Album album);

    //分页查询数据
    List<Album> queryAlbumByPage(Integer page, Integer rows);

    //查询指定
    Album queryOneAlbum(String id);
}
