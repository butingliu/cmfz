package com.baizhi.service;

import com.baizhi.dao.ChapDao;
import com.baizhi.entity.Chap;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ChapServiceImpl implements ChapService {
    @Autowired
    private ChapDao chapDao;

    @Override
    public List<Chap> queryChapByPage(String fid, Integer page, Integer rows) {
        return chapDao.selectByRowBounds(new Chap().setAl_id(fid), new RowBounds((page - 1) * rows, rows));
    }

    @Override
    public void insertChap(Chap chap) {
        chapDao.insert(chap);
    }

    @Override
    public void removeChap(String id) {
        chapDao.deleteByPrimaryKey(new Chap().setId(id));
    }

    @Override
    public void updateChap(Chap chap) {
        chapDao.updateByPrimaryKeySelective(chap);
    }

    @Override
    public Chap queryOneChap(String id) {
        return chapDao.selectOne(new Chap().setId(id));
    }

    @Override
    public List<Chap> queryAllChapByFid(String fid) {
        return chapDao.select(new Chap().setAl_id(fid));
    }
}
