package com.baizhi.service;

import com.baizhi.dao.WorkDao;
import com.baizhi.entity.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

//再一次

@Service
public class WorkServiceImpl implements WorkService {
    @Autowired
    private WorkDao workDao;

    @Override
    public List<Work> queryAllWorkByUser(String uid) {
        Example example = new Example(Work.class);
        example.createCriteria().andEqualTo("user_id", uid).orEqualTo("ty", "0");
        return workDao.selectByExample(example);
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
