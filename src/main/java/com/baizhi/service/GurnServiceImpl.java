package com.baizhi.service;

import com.baizhi.dao.GurnDao;
import com.baizhi.entity.Gurn;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class GurnServiceImpl implements GurnService {
    @Autowired
    private GurnDao gurnDao;

    @Override
    public List<Gurn> queryAllGurn() {
        return gurnDao.selectAll();
    }

    @Override
    public List<Gurn> queryGurnByPage(Integer page, Integer rows) {
        return gurnDao.selectByRowBounds(new Gurn(),new RowBounds((page-1)*rows,rows));
    }

    @Override
    public Gurn queryOneGurn(String id) {
        return gurnDao.selectOne(new Gurn().setId(id));
    }

    @Override
    public void insertGurn(Gurn gurn) {
        gurnDao.insert(gurn);
    }

    @Override
    public void removeGurn(String id) {
        gurnDao.deleteByPrimaryKey(new Gurn().setId(id));
    }

    @Override
    public void updateGurn(Gurn gurn) {
        gurnDao.updateByPrimaryKeySelective(gurn);
    }
}
