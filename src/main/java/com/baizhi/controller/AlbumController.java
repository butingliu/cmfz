package com.baizhi.controller;

import com.baizhi.entity.Album;
import com.baizhi.entity.Article;
import com.baizhi.entity.Banner;
import com.baizhi.service.AlbumService;
import com.baizhi.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("album")
public class AlbumController {
    @Autowired
    private AlbumService albumService;
    @Autowired
    private ArticleService articleService;
    @RequestMapping("showAlbum")
    public Map showAlbum(Integer page,Integer rows){
        Map map = new HashMap<>();
        List<Album> a1 = albumService.queryAllAlbum();
        for (Album a : a1) {
            System.out.println("----------------------------");
            a.setCover(null);
            System.out.println(a);
            List<Article> b1 = articleService.queryArticleByFid(a.getId());
            System.out.println("数量："+b1.size());
            a.setJishu(b1.size()-1);
            System.out.println("@@@@@@@@@"+a);
            System.out.println("----------------------------");
            albumService.updateAlbum(a);
        }

        Integer records = a1.size();
        Integer total=records%rows==0? records/rows:records/rows+1;
        List<Album> a4 = albumService.queryAlbumByPage(page, rows);
        map.put("records",records);
        map.put("total",total);
        map.put("rows",a4);
        return map;
    }
    @RequestMapping("albumcrut")
    public Map albumcrut(Album album,String oper) {
        Map<String, String> map = new HashMap<>();
        if (oper.equals("del")) {
            //bannerService.deleteBanner(banner.getId());
            albumService.removeAlbum(album.getId());
            map.put("status", "delOk");
            return map;
        } else if (oper.equals("add")) {
            String x = UUID.randomUUID().toString();
            album.setId(x);
            albumService.insertAlbum(album);
            map.put("status", "addOk");
            map.put("id", x);
            return map;
        } else {
            Album album1 = albumService.queryOneAlbum(album.getId());
            if (album.getCover().equals("") || album.getCover().equals(null)) {
                album.setCover(album1.getCover());
            }
            albumService.updateAlbum(album);
            map.put("status", "editOK");
            map.put("id", album.getId());
            return map;
        }
    }
    @RequestMapping("upload")
    public void uploadAlbum(MultipartFile cover, String id, HttpSession session, HttpServletRequest request) throws UnknownHostException {
        String realPath = session.getServletContext().getRealPath("/back/mp3");
        File file = new File(realPath);
        if(!file.exists()){
            file.mkdirs();
        }
        // 防止重名操作
        String originalFilename = cover.getOriginalFilename();
        originalFilename = new Date().getTime()+"_"+originalFilename;
        try {
            cover.transferTo(new File(realPath,originalFilename));
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 相对路径 : ../XX/XX/XX.jpg
        // 网络路径 : http://IP:端口/项目名/文件存放位置
        String http = request.getScheme();
        String localHost = InetAddress.getLocalHost().toString();
        int serverPort = request.getServerPort();
        String contextPath = request.getContextPath();
        String uri = http+"://"+localHost.split("/")[1]+":"+serverPort+contextPath+"/back/mp3/"+originalFilename;
        Album album = new Album();
        album.setId(id);
        album.setCover(uri);
        System.out.println("test:"+album);
        albumService.updateAlbum(album);
        //albumService.updateAlbum2(album);

    }
    @RequestMapping("upload1")
    public void upload1(MultipartFile cover, String id, HttpSession session, HttpServletRequest request) throws IOException {
        int a=2;
        String oldname = cover.getOriginalFilename();
        if(cover.getOriginalFilename().equals("")||cover.getOriginalFilename().equals(null)){
            a=1;
        }
        if(a==2){
            String realPath = session.getServletContext().getRealPath("/back/mp3");
            File file = new File(realPath);
            if(!file.exists()){
                file.mkdirs();
            }
            // 防止重名操作
            String originalFilename = cover.getOriginalFilename();
            originalFilename = new Date().getTime()+"_"+originalFilename;
            try {
                cover.transferTo(new File(realPath,originalFilename));
            } catch (IOException e) {
                e.printStackTrace();
            }
            // 相对路径 : ../XX/XX/XX.jpg
            // 网络路径 : http://IP:端口/项目名/文件存放位置
            String http = request.getScheme();
            String localHost = InetAddress.getLocalHost().toString();
            int serverPort = request.getServerPort();
            String contextPath = request.getContextPath();
            String uri = http+"://"+localHost.split("/")[1]+":"+serverPort+contextPath+"/back/mp3/"+originalFilename;
            Album album = new Album();
            album.setId(id);
            album.setCover(uri);
            System.out.println(album);
            albumService.updateAlbum(album);
            //albumService.updateAlbum2(album);
        }

    }
}
