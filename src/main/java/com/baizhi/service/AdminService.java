package com.baizhi.service;

import com.baizhi.entity.Admin;

public interface AdminService {
    //查询指定管理员
    Admin queryAdminByName(String name);
}
