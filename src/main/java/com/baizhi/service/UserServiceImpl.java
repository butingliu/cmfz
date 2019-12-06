package com.baizhi.service;

import com.baizhi.annotation.GoEasyAnnotation;
import com.baizhi.dao.UserDao;
import com.baizhi.entity.MapVo;
import com.baizhi.entity.User;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public List<User> queryAllUser() {
        return userDao.selectAll();
    }

    @Override
    public List<User> queryUserByPage(Integer page, Integer rows) {
        return userDao.selectByRowBounds(new User(),new RowBounds((page-1)*rows,rows));
    }

    @Override
    public User queryOneUser(String id) {
        return userDao.selectOne(new User().setId(id));
    }

    @Override
    @GoEasyAnnotation
    public void insertUser(User user) {
        userDao.insert(user);
    }

    @Override
    @GoEasyAnnotation
    public void removeUser(String id) {

        userDao.deleteByPrimaryKey(id);
    }

    @Override
    @GoEasyAnnotation
    public void updateUser(User user) {
        userDao.updateByPrimaryKeySelective(user);

    }

    @Override
    public Integer queryUserBySexAndDay(String se, Integer da) {
        return userDao.selectUserBySexAndDay(se,da);
    }

    @Override
    public List<MapVo> queryUserBySexAndAddress() {
        return userDao.selectUserBySexAndAddress();
    }

    @Override
    public User queryUserByPhoneAndPwd(String phone, String pwd) {
        return userDao.selectOne(new User().setPhnoe(phone).setPwd(pwd));
    }

    @Override
    public User registInterface(String phone) {
        User user = new User();
        String id = UUID.randomUUID().toString().replace("-", "");
        user.setId(id).setPhnoe(phone);
        userDao.insert(user);
        return user;
    }
}
