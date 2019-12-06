package com.baizhi.service;

import com.baizhi.dao.JishuDao;
import com.baizhi.entity.Jishu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JishuServiceImpl implements JishuService {
    @Autowired
    private JishuDao jishuDao;

    @Override
    public void removeJishu(String wid) {
        jishuDao.delete(new Jishu().setWork_id(wid));
    }

    @Override
    public List<Jishu> queryJishuByUidAndWid(String uid, String wid) {
        return jishuDao.select(new Jishu().setWork_id(wid).setUser_id(uid));
    }

    @Override
    public void insertJishu(Jishu jishu) {
        jishuDao.insert(jishu);
    }

    @Override
    public void removeJishuById(String id) {
        jishuDao.delete(new Jishu().setId(id));
    }

    @Override
    public void updateJishu(Jishu jishu) {
        jishuDao.updateByPrimaryKeySelective(jishu);
    }
}
