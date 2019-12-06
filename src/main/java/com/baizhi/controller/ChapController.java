package com.baizhi.controller;

import com.baizhi.entity.Chap;
import com.baizhi.service.AlbumService;
import com.baizhi.service.ChapService;
import com.baizhi.util.AudioUtil;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.util.*;

@RestController
@RequestMapping("chap")
public class ChapController {
    @Autowired
    private ChapService chapService;
    @Autowired
    private AlbumService albumService;

    @RequestMapping("showChap")
    public Map showChap(String al_id, Integer page, Integer rows) {
        Map map = new HashMap<>();
        List<Chap> c1 = chapService.queryAllChapByFid(al_id);
        List<Chap> c2 = chapService.queryChapByPage(al_id, page, rows);
        Integer records = c1.size();
        Integer total = records % rows == 0 ? records / rows : records / rows + 1;
        map.put("records", records);
        map.put("total", total);
        map.put("rows", c2);
        return map;
    }

    @RequestMapping("chapcrut")
    public Map chapcrut(Chap chap, String oper, String fid) {
        Map<String, String> map = new HashMap<>();
        if (oper.equals("del")) {

            //bannerService.deleteBanner(banner.getId());
            chapService.removeChap(chap.getId());

            map.put("status", "delOk");
            return map;
        } else if (oper.equals("add")) {
            String x = UUID.randomUUID().toString();
            chap.setId(x);
            chap.setAl_id(fid);
            chapService.insertChap(chap);

            map.put("status", "addOk");
            map.put("id", x);
            return map;
        } else {
            Chap chap1 = chapService.queryOneChap(chap.getId());
            if (chap1.getPic().equals("") || chap1.getPic().equals(null)) {
                chap.setPic(chap1.getPic());
            }
            chapService.updateChap(chap);
            map.put("status", "editOK");
            map.put("id", chap.getId());
            return map;
        }
    }

    @RequestMapping("upload")
    public void uploadAlbum(MultipartFile pic, String id, HttpSession session, HttpServletRequest request) throws IOException, TagException, ReadOnlyFileException, CannotReadException, InvalidAudioFrameException {


        String realPath = session.getServletContext().getRealPath("/back/mp3");
        File file = new File(realPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        // 防止重名操作
        String originalFilename = pic.getOriginalFilename();
        originalFilename = new Date().getTime() + "_" + originalFilename;
        try {
            pic.transferTo(new File(realPath, originalFilename));
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 相对路径 : ../XX/XX/XX.jpg
        // 网络路径 : http://IP:端口/项目名/文件存放位置
        String http = request.getScheme();
        String localHost = InetAddress.getLocalHost().toString();
        int serverPort = request.getServerPort();
        String contextPath = request.getContextPath();
        String uri = http + "://" + localHost.split("/")[1] + ":" + serverPort + contextPath + "/back/mp3/" + originalFilename;
        Chap chap = new Chap();
        File file1 = new File(realPath, originalFilename);
        Float m = AudioUtil.getMp3Duration(file1);
        String a1 = "";
        if (m > 60 && m < 3600) {
            Float v = (m / 60);
            int i = v.intValue();
            Float v1 = m % 60;
            int i1 = v1.intValue();
            a1 = i + "分钟" + i1 + "秒";
        }
        if (m <= 60) {
            a1 = m + "秒";
        }
        Long size = pic.getSize();
        Float f = size.floatValue();


        Float d = f / 1024 / 1024;
        String format = String.format("%.2f", d);
        d = Float.parseFloat(format);
        chap.setBigs(d + "MB");
        chap.setTimes(a1);
        chap.setId(id);
        chap.setPic(uri);
        chapService.updateChap(chap);


        //albumService.updateAlbum2(album);

    }

    @RequestMapping("upload1")
    public void upload1(MultipartFile pic, String id, HttpSession session, HttpServletRequest request) throws IOException {
        int a = 2;
        String oldname = pic.getOriginalFilename();
        if (pic.getOriginalFilename().equals("") || pic.getOriginalFilename().equals(null)) {
            a = 1;
        }
        if (a == 2) {
            String realPath = session.getServletContext().getRealPath("/back/mp3");
            File file = new File(realPath);
            if (!file.exists()) {
                file.mkdirs();
            }
            // 防止重名操作
            String originalFilename = pic.getOriginalFilename();
            originalFilename = new Date().getTime() + "_" + originalFilename;
            try {
                pic.transferTo(new File(realPath, originalFilename));
            } catch (IOException e) {
                e.printStackTrace();
            }
            // 相对路径 : ../XX/XX/XX.jpg
            // 网络路径 : http://IP:端口/项目名/文件存放位置
            String http = request.getScheme();
            String localHost = InetAddress.getLocalHost().toString();
            int serverPort = request.getServerPort();
            String contextPath = request.getContextPath();
            String uri = http + "://" + localHost.split("/")[1] + ":" + serverPort + contextPath + "/back/mp3/" + originalFilename;
            Chap chap = new Chap();

            chap.setId(id);
            chap.setPic(uri);
            chapService.updateChap(chap);
        }

    }
}
