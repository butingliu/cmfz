package com.baizhi.controller;

import com.baizhi.entity.Gurn;
import com.baizhi.service.GurnService;
import com.baizhi.util.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("gurn")
public class GurnController {
    @Autowired
    private GurnService gurnService;

    @RequestMapping("showGurn")
    public Map showGurn(Integer page, Integer rows) {
        Map map = new HashMap<>();
        List<Gurn> list = gurnService.queryAllGurn();
        List<Gurn> list1 = gurnService.queryGurnByPage(page, rows);
        Integer records = list.size();
        Integer total = records % rows == 0 ? records / rows : records / rows + 1;
        map.put("records", records);
        map.put("total", total);
        map.put("rows", list1);
        return map;
    }

    @RequestMapping("gurncrut")
    public Map<String, String> gurncrut(String oper, Gurn gurn) {
        Map<String, String> map = new HashMap<>();
        if (oper.equals("del")) {
            gurnService.removeGurn(gurn.getId());
            map.put("status", "delOk");
            return map;
        } else if (oper.equals("add")) {
            String x = UUID.randomUUID().toString();
            gurn.setId(x);
            gurnService.insertGurn(gurn);
            map.put("status", "addOk");
            map.put("id", x);
            return map;
        } else {
            Gurn gurn1 = gurnService.queryOneGurn(gurn.getId());
            if (gurn.getAvator().equals("") || gurn.getAvator().equals(null)) {
                gurn.setAvator(gurn1.getAvator());
            }
            gurnService.updateGurn(gurn);
            map.put("status", "editOK");
            map.put("id", gurn.getId());
            return map;
        }

    }

    @RequestMapping("upload")
    public void uploadGurn(MultipartFile avator, String id, HttpSession session, HttpServletRequest request) {
        String uri = HttpUtil.getHttpUrl(avator, request, request.getSession(), "/back/tea/");
        Gurn gurn = new Gurn();
        gurn.setId(id);
        gurn.setAvator(uri);
        gurnService.updateGurn(gurn);
    }

    @RequestMapping("upload1")
    public void uploadGurn1(MultipartFile avator, String id, HttpSession session, HttpServletRequest request) {
        int a = 2;
        String s = avator.getOriginalFilename();
        if (s.equals("") || s.equals(null)) {
            a = 1;
        }
        if (a == 2) {
            String uri = HttpUtil.getHttpUrl(avator, request, request.getSession(), "/back/tea/");
            Gurn gurn = new Gurn();
            gurn.setId(id);
            gurn.setAvator(uri);
            gurnService.updateGurn(gurn);
        }
    }

}
