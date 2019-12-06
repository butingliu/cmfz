package com.baizhi.dao;

import com.baizhi.entity.Banner;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.additional.idlist.DeleteByIdListMapper;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
public interface BannerDao extends Mapper<Banner>, DeleteByIdListMapper<Banner,String> {
    //模糊分页查询数据
    List<Banner> queryBannerByRows(
            @Param("searchField") String searchField,
            @Param("searchString") String searchString,
            @Param("searchOper") String searchOper,
            @Param("page") Integer page,
            @Param("rows") Integer rows);
    List<Banner> queryAllBannerBypage(
            @Param("page") Integer page,
            @Param("rows") Integer rows
    );
    //模糊分页查询总条数
    List<Banner> queryBannerCount(
            @Param("searchField") String searchField,
            @Param("searchString") String searchString,
            @Param("searchOper") String searchOper);

}
