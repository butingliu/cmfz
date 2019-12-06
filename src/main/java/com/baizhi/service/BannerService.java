package com.baizhi.service;

import com.baizhi.entity.Banner;

import java.util.List;

public interface BannerService {
    //查询全部
    List<Banner> queryAllBanner();

    //模糊分页查询
    List<Banner> queryBannerByRows(String searchField, String searchString, String searchOper, Integer page, Integer rows);

    //模糊条数
    List<Banner> queryBannerCount(String searchField, String searchString, String searchOper);

    //删除
    void deleteBanner(String id);

    //修改
    void modifyBanner(Banner banner);

    //添加
    void addBanner(Banner banner);

    List<Banner> queryBannerByPage(Integer page, Integer rows);

    //单条查询
    Banner selectBanner(String id);

    //删除数组
    void deleteBanner(String[] as);

    //查询展示轮播图
    List<Banner> queryBannerByStatus(String status);
}
