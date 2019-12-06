package com.baizhi.controller;

import com.baizhi.entity.MapVo;
import com.baizhi.entity.User;
import com.baizhi.service.UserService;
import com.baizhi.util.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;
    @RequestMapping("showAllUser")
    public Map showAllUser(Integer page,Integer rows){
        Map map = new HashMap();
        List<User> list = userService.queryAllUser();
        List<User> list1 = userService.queryUserByPage(page, rows);
        int records = list.size();
        int total=records%rows==0?records/rows:records/rows+1;
        map.put("records",records);
        map.put("total",total);
        map.put("rows",list1);
        return map;
    }
    @RequestMapping("usercrut")
    public Map userCrut(String oper,User user){
        Map map = new HashMap();
        if(oper.equals("add")){
            String s = UUID.randomUUID().toString().replace("-", "");
            user.setId(s);
            userService.insertUser(user);
            map.put("id",s);
            map.put("tishi","addOk");
        }
        if(oper.equals("del")){
            System.out.println("--------------------------------------------------");
            userService.removeUser(user.getId());
        }
        if(oper.equals("edit")){
            User user1 = userService.queryOneUser(user.getId());
            if(user.getAvator().equals("")||user.getAvator().equals(null)){
                user.setAvator(user1.getAvator());
            }
            userService.updateUser(user);
            map.put("id",user1.getId());
            map.put("tishi","editOk");
        }
        return  map;
    }
    @RequestMapping("upload")
    public void uploadUser(MultipartFile avator, HttpServletRequest request, HttpSession session,String st,String id){
        User user = new User();
        user.setId(id);
        if(st.equals("addOk")){
            String uri = HttpUtil.getHttpUrl(avator, request, session, "/back/userImg/");
            user.setAvator(uri);
        }
        if(st.equals("editOk")){
            String filename = avator.getOriginalFilename();
            if(filename.equals("")||filename.equals(null)){
                System.out.println("^_^  ^_^  ^_^  ^_^  ^_^  ^_^  ^_^  ^_^  ^_^");
                return ;
            }else{
                String ur2 = HttpUtil.getHttpUrl(avator, request, session, "/back/userImg/");
                user.setAvator(ur2);
            }
        }
        userService.updateUser(user);
    }
    @RequestMapping("xushi")
    public Map xushi(){
        HashMap map = new HashMap();
        Integer day1x = userService.queryUserBySexAndDay("男", 1);
        Integer day1y = userService.queryUserBySexAndDay("女", 1);
        Integer day7x = userService.queryUserBySexAndDay("男", 7);
        Integer day7y = userService.queryUserBySexAndDay("女", 7);
        Integer day30x = userService.queryUserBySexAndDay("男", 30);
        Integer day30y = userService.queryUserBySexAndDay("女", 30);
        Integer day365x = userService.queryUserBySexAndDay("男", 365);
        Integer day365y = userService.queryUserBySexAndDay("女", 365);
        ArrayList<Integer> list = new ArrayList<>();
        list.add(day1x);
        list.add(day7x);
        list.add(day30x);
        list.add(day365x);
        ArrayList<Object> list1 = new ArrayList<>();
        list1.add(day1y);
        list1.add(day7y);
        list1.add(day30y);
        list1.add(day365y);
       map.put("man",list);
       map.put("woman",list1);
        return map;
    }
    @RequestMapping("ditu")
    public List<MapVo> ditu(){
        return userService.queryUserBySexAndAddress();
    }
}
