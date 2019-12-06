package com.baizhi.controller;

import com.baizhi.entity.Article;
import com.baizhi.entity.Gurn;
import com.baizhi.service.ArticleService;
import com.baizhi.service.GurnService;
import com.baizhi.util.HttpUtil;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;
    @Autowired
    private GurnService gurnService;

    @RequestMapping("showgurnlist")
    public List<Gurn> showGurnList() {
        return gurnService.queryAllGurn();
    }

    @RequestMapping("showArticle")
    public Map showArticle(Integer page, Integer rows) {
        Map map = new HashMap<>();
        List<Article> list = articleService.queryAllArticle();
        List<Article> list1 = articleService.queryArticleByPage(page, rows);
        Integer records = list.size();
        Integer total = records % rows == 0 ? records / rows : records / rows + 1;
        map.put("records", records);
        map.put("total", total);
        map.put("rows", list1);
        return map;
    }

    @RequestMapping("articlecrut")
    public Map<String, String> articlecrut(String oper, Article article) {
        Map<String, String> map = new HashMap<>();
        if (oper.equals("del")) {
            articleService.removeArticle(article.getId());
            map.put("status", "delOk");
            return map;
        } else if (oper.equals("add")) {
            String x = UUID.randomUUID().toString();
            article.setId(x);
            articleService.insertArticle(article);
            map.put("status", "addOk");
            map.put("id", x);
            return map;
        } else {
            Article article1 = articleService.queryOneArticle(article.getId());
            if (article.getCover().equals("") || article.getCover().equals(null)) {
                article.setCover(article1.getCover());
            }
            articleService.updateArticle(article);
            map.put("status", "editOK");
            map.put("id", article.getId());
            return map;
        }

    }

    @RequestMapping("removeArticle")
    public void removeArticle(String id) {
        articleService.removeArticle(id);
    }

    @RequestMapping("upload")
    public void uploadArticle(MultipartFile cover1, HttpSession session, HttpServletRequest request, Article article) {
        int a = 2;
        String id = article.getId();
        if (id.equals("") || id.equals(null)) {
            a = 1;
            String uri = HttpUtil.getHttpUrl(cover1, request, session, "/back/articleImg/");
            String s = UUID.randomUUID().toString().replace("-", "");
            article.setId(s);
            article.setCover(uri);
            Date date = new Date();
            article.setPub_date(date);
            articleService.insertArticle(article);
        }
        if (a == 2) {
            System.out.println(article);
            System.out.println(cover1.getOriginalFilename());
            if (cover1.getOriginalFilename().equals("") || cover1.getOriginalFilename().equals(null)) {
                articleService.updateArticle(article);
            } else {
                String ur2 = HttpUtil.getHttpUrl(cover1, request, session, "/back/articleImg/");
                article.setCover(ur2);
                articleService.updateArticle(article);
            }

        }


    }

    @RequestMapping("upload1")
    public void uploadArticle1(MultipartFile cover, String id, HttpSession session, HttpServletRequest request) {
        int a = 2;
        String s = cover.getOriginalFilename();
        if (s.equals("") || s.equals(null)) {
            a = 1;
        }
        if (a == 2) {
            String uri = HttpUtil.getHttpUrl(cover, request, request.getSession(), "/back/tea");
            Article article = new Article();
            article.setId(id);
            article.setCover(uri);
            articleService.updateArticle(article);
        }
    }

    @RequestMapping("uploadImg")
    public Map uploadImg(MultipartFile imgFile, HttpSession session, HttpServletRequest request) {
        HashMap hashMap = new HashMap();
        String dir = "/back/articleImg/";
        try {
            String httpUrl = HttpUtil.getHttpUrl(imgFile, request, session, dir);
            hashMap.put("error", 0);
            hashMap.put("url", httpUrl);
        } catch (Exception e) {
            hashMap.put("error", 1);
            hashMap.put("message", "上传错误");
            e.printStackTrace();
        }
        return hashMap;
    }

    @RequestMapping("showAllImgs")
    public Map showAllImgs(HttpSession session, HttpServletRequest request) {
        // 1. 获取文件夹绝对路径
        String realPath = session.getServletContext().getRealPath("/back/articleImg/");
        // 2. 准备返回的Json数据
        HashMap hashMap = new HashMap();
        ArrayList arrayList = new ArrayList();
        // 3. 获取目标文件夹
        File file = new File(realPath);
        File[] files = file.listFiles();
        // 4. 遍历文件夹中的文件
        for (File file1 : files) {
            // 5. 文件属性封装
            HashMap fileMap = new HashMap();
            fileMap.put("is_dir", false);
            fileMap.put("has_file", false);
            fileMap.put("filesize", file1.length());
            fileMap.put("is_photo", true);
            // 获取文件后缀 | 文件类型
            String extension = FilenameUtils.getExtension(file1.getName());
            fileMap.put("filetype", extension);
            fileMap.put("filename", file1.getName());
            // 获取文件上传时间 1. 截取时间戳 2. 创建格式转化对象 3. 格式类型转换
            String s = file1.getName().split("_")[0];
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String format = simpleDateFormat.format(new Date(Long.valueOf(s)));
            fileMap.put("datetime", format);
            arrayList.add(fileMap);
        }
        hashMap.put("file_list", arrayList);
        hashMap.put("total_count", arrayList.size());
        // 返回路径为 项目名 + 文件夹路径
        hashMap.put("current_url", request.getContextPath() + "/back/articleImg/");
        return hashMap;
    }

}
