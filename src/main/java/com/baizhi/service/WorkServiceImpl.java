package com.baizhi.service;

import com.baizhi.dao.WorkDao;
import com.baizhi.entity.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkServiceImpl implements WorkService {
    @Autowired
    private WorkDao workDao;

    @Override
    public List<Work> queryAllWorkByUser(String uid) {
        return workDao.selectByExample(new Work().setUser_id(uid));
    }

    @Override
    public void insertWork(Work work) {
        workDao.insert(work);
    }

    @Override
    public void removeWork(String id) {
        workDao.deleteByPrimaryKey(new Work().setId(id));
    }
}
