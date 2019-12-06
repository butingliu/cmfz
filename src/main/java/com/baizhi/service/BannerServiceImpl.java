package com.baizhi.service;

import com.baizhi.annotation.LogAnnotation;
import com.baizhi.dao.BannerDao;
import com.baizhi.entity.Banner;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
@Transactional
public class BannerServiceImpl implements BannerService {
    @Autowired
    private BannerDao bannerDao;

    @Override
    public List<Banner> queryAllBanner() {
        return bannerDao.selectAll();
    }

    @Override
    public List<Banner> queryBannerByRows(String searchField, String searchString, String searchOper, Integer page, Integer rows) {
        return bannerDao.queryBannerByRows(searchField,searchString,searchOper,page,rows);
    }

    @Override
    public List<Banner> queryBannerCount(String searchField, String searchString, String searchOper) {
        return bannerDao.queryBannerCount(searchField,searchString,searchOper);
    }

    @Override
    public void deleteBanner(String id) {
        bannerDao.deleteByPrimaryKey(id);
    }

    @Override
    public void modifyBanner(Banner banner) {

        bannerDao.updateByPrimaryKeySelective(banner);
    }

    @Override
    public void addBanner(Banner banner) {
        bannerDao.insert(banner);
    }

    @Override
    @LogAnnotation("展示轮播图列表")
    public List<Banner> queryBannerByPage(Integer page, Integer rows) {
        return bannerDao.selectByRowBounds(new Banner(),new RowBounds(page,rows));
    }

    @Override
    @LogAnnotation("查询一个轮播图信息")
    public Banner selectBanner(String id) {
        Banner banner = new Banner();
        banner.setId(id);
        return bannerDao.selectOne(banner);
    }

    @Override
    public void deleteBanner(String[] as) {
        bannerDao.deleteByIdList(Arrays.asList(as));
    }

    @Override
    public List<Banner> queryBannerByStatus(String status) {
        List<Banner> list = bannerDao.select(new Banner().setStatus(status));
        return list;
    }
}
