package com.baizhi.controller;

import com.baizhi.entity.*;
import com.baizhi.service.*;
import com.baizhi.util.RandomCode;
import com.baizhi.util.SmsSample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("front")
public class FrontController {
    @Autowired
    private BannerService bannerService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private AlbumService albumService;
    @Autowired
    private ChapService chapService;
    @Autowired
    private UserService userService;
    @Autowired
    private WorkService workService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private GurnService gurnService;
    @Autowired
    private JishuService jishuService;

    @RequestMapping("login")
    public Map login(String phone, String pwd) {
        //1.登陆接口

        HashMap map = new HashMap<>();
        try {

            User user = userService.queryUserByPhoneAndPwd(phone, pwd);
            if (user == null) {
                map.put("status", "200");
                map.put("user", user);
                map.put("message", "用户为空");
            } else {
                map.put("status", "200");
                map.put("user", user);
                map.put("message", "用户正确");
            }
            return map;
        } catch (Exception e) {
            map.put("message", e.getStackTrace());
            map.put("status", "-200");
            return map;
        }

    }

    @RequestMapping("yanzheng")
    public Map yanzheng(String phone) {
        //2.发送验证码
        Map map = new HashMap<>();
        try {
            String code = new RandomCode().getRandomCode();
            stringRedisTemplate.setStringSerializer(new StringRedisSerializer());
            stringRedisTemplate.setKeySerializer(new StringRedisSerializer());
            stringRedisTemplate.opsForValue().set(phone, code, 60, TimeUnit.SECONDS);
            new SmsSample().qq(phone, code);
            map.put("status", "200");
            return map;
        } catch (Exception e) {
            map.put("status", "-200");
            map.put("message", e.getMessage());
            return map;
        }

    }

    @RequestMapping("zhuce")
    public Map redist(String code, String phone) {
        //3.注册接口
        HashMap map = new HashMap<>();
        try {
            String s = stringRedisTemplate.opsForValue().get(phone);
            if (code.equals(s)) {
                /*String id = UUID.randomUUID().toString().replace("-", "");
                User user = new User();
                user.setId(id).setPhnoe(phone);
                userService.insertUser(user);*/
                map.put("status", "200");
                return map;
            } else {
                map.put("status", "-200");
                return map;
            }
        } catch (Exception e) {
            map.put("status", "-200");
            map.put("message", e.getMessage());
            return map;
        }

    }

    @RequestMapping("buchongUser")
    public Map buchongUser(User user) {
        //4.补充个人信息接口
        HashMap map = new HashMap<>();
        try {
            String id = UUID.randomUUID().toString().replace("-", "");
            user.setId(id);
            userService.insertUser(user);
            map.put("user", user);
            map.put("status", "200");
            return map;
        } catch (Exception e) {
            map.put("status", "-200");
            map.put("message", e.getMessage());
            return map;
        }

    }

    @RequestMapping("showIndex")
    public Map showIndex(String uid, String type, String sub_type) {
        //首页展示--5.一级页面展示接口
        HashMap map = new HashMap<>();
        try {
            map.put("status", "200");
            if (type.equals("all")) {
                List<Banner> banners = bannerService.queryBannerByStatus("1");
                List<Article> articles = articleService.queryAllArticle();
                List<Article> articles1 = new ArrayList<>();
                for (int i = 0; i < 3; i++) {
                    articles1.add(articles.get(i));
                }
                List<Album> albums = albumService.queryAllAlbum();
                ArrayList<Album> albums1 = new ArrayList<>();
                for (int i = 0; i < 6; i++) {
                    albums1.add(albums.get(i));
                }
                map.put("head", banners);
                map.put("articles", articles1);
                map.put("albums", albums1);
                return map;
            } else if (type.equals("wen")) {
                List<Article> articles = articleService.queryAllArticle();
                List<Article> articles1 = new ArrayList<>();
                for (int i = 0; i < articles.size(); i++) {
                    articles1.add(articles.get(i));
                }
                map.put("articles", articles1);
                return map;
            } else if (type.equals("si")) {
                List<Album> albums = albumService.queryAllAlbum();
                ArrayList<Album> albums1 = new ArrayList<>();
                for (int i = 0; i < albums.size(); i++) {
                    albums1.add(albums.get(i));
                }
                map.put("albums", albums1);
            }
            if (sub_type.equals("ssjy")) {
                Set<String> list = stringRedisTemplate.opsForSet().members(uid);
                ArrayList<Article> articles1 = new ArrayList<>();
                for (String s : list) {
                    List<Article> articles = articleService.queryArticleByFid(s);
                    articles1.addAll(articles);
                }
                map.put("articles", articles1);
                return map;
            }
            List<Article> articles = articleService.queryAllArticle();
            map.put("articles", articles);
            return map;
        } catch (Exception e) {
            map.put("status", "-200");
            map.put("message", e.getMessage());
            return map;
        }
    }

    @RequestMapping("showArticle")
    public Map showArticle(String id) {
        //6--文章详情接口
        HashMap map = new HashMap<>();
        try {
            Article article = articleService.queryOneArticle(id);
            map.put("article", article);
            map.put("status", "200");
            return map;
        } catch (Exception e) {
            map.put("status", "-200");
            map.put("message", e.getMessage());
            return map;
        }

    }

    @RequestMapping("showAlbum")
    public Map showAlbum(String id, String uid) {
        //7--专辑详情接口
        HashMap map = new HashMap<>();
        try {
            Album album = albumService.queryOneAlbum(id);
            List<Chap> chaps = chapService.queryAllChapByFid(id);
            map.put("album", album);
            map.put("chaps", chaps);
            map.put("status", "200");
            return map;
        } catch (Exception e) {
            map.put("status", "-200");
            map.put("message", e.getMessage());
            return map;
        }

    }

    @RequestMapping("showWork")
    public Map showWork(String uid) {
        //8--展示功课
        Map map = new HashMap<>();
        try {
            List<Work> option = workService.queryAllWorkByUser(uid);
            map.put("status", "200");
            map.put("works", option);
            return map;
        } catch (Exception e) {
            map.put("status", "-200");
            map.put("message", e.getMessage());
            return map;
        }

    }

    @RequestMapping("insertWork")
    public Map insertWork(Work work, String uid) {
        //9--添加功课
        Map map = new HashMap<>();
        try {
            String id = UUID.randomUUID().toString().replace("-", "");
            work.setId(id);
            workService.insertWork(work);
            List<Work> option = workService.queryAllWorkByUser(uid);
            String status = "200";
            map.put("status", status);
            map.put("works", option);
            return map;
        } catch (Exception e) {
            map.put("status", "-200");
            map.put("message", e.getMessage());
            return map;
        }
    }

    @RequestMapping("removeWork")
    public Map removeWork(String id, String uid) {
        //10.删除功课
        Map map = new HashMap<>();
        try {
            jishuService.removeJishu(id);
            workService.removeWork(id);
            List<Work> option = workService.queryAllWorkByUser(uid);
            String status = "200";
            map.put("status", status);
            map.put("works", option);
            return map;
        } catch (Exception e) {
            map.put("status", "-200");
            map.put("message", e.getMessage());
            return map;
        }

    }

    @RequestMapping("showjishus")
    public Map showjishus(String id, String uid) {
        //11.展示计数器
        HashMap map = new HashMap<>();
        try {
            List<Jishu> counters = jishuService.queryJishuByUidAndWid(uid, id);
            map.put("counters", counters);
            map.put("status", "200");
            return map;
        } catch (Exception e) {
            map.put("status", "-200");
            map.put("message", e.getMessage());
            return map;
        }
    }

    @RequestMapping("insertJishu")
    public Map insertJishu(String uid, String name, String wid) {
        //12.添加计数器
        Map map = new HashMap<>();
        try {
            String id = UUID.randomUUID().toString().replace("-", "");
            Jishu jishu = new Jishu();
            jishu.setId(id);
            jishu.setName(name);
            jishuService.insertJishu(jishu);
            List<Jishu> counters = jishuService.queryJishuByUidAndWid(uid, wid);
            map.put("status", "200");
            map.put("counters", counters);
            return map;
        } catch (Exception e) {
            map.put("status", "-200");
            map.put("message", e.getMessage());
            return map;
        }
    }

    @RequestMapping("removeJishu")
    public Map removeJishu(String uid, String id, String wid) {
        //13.删除计数器
        HashMap map = new HashMap<>();
        try {
            jishuService.removeJishuById(id);
            List<Jishu> counters = jishuService.queryJishuByUidAndWid(uid, wid);
            map.put("counters", counters);
            map.put("status", "200");
            return map;
        } catch (Exception e) {
            map.put("status", "-200");
            map.put("message", e.getMessage());
            return map;
        }
    }

    @RequestMapping("updateJishu")
    public Map updateJishu(String uid, String id, Integer count, String wid) {
        //14.更新计数器
        HashMap map = new HashMap<>();
        try {
            Jishu jishu = new Jishu();
            jishu.setId(id).setCount(count);
            jishuService.updateJishu(jishu);
            List<Jishu> counters = jishuService.queryJishuByUidAndWid(uid, wid);
            map.put("status", "200");
            map.put("counters", counters);
            return map;
        } catch (Exception e) {
            map.put("status", "-200");
            map.put("message", e.getMessage());
            return map;
        }
    }

    @RequestMapping("updateUser1")
    public Map updateUser1(User user) {
        //15.修改个人信息
        Map map = new HashMap<>();
        try {
            String status = "200";
            userService.updateUser(user);
            map.put("status", status);
            map.put("user", user);
            return map;
        } catch (Exception e) {
            map.put("status", "-200");
            map.put("message", e.getMessage());
            return map;
        }

    }

    @RequestMapping("zhanshishangshi")
    public Map zhanshishangshi(String uid) {
        //17.展示上师列表
        Map map = new HashMap<>();
        try {
            List<Gurn> list = gurnService.queryAllGurn();
            map.put("list", list);
            map.put("status", "200");
            return map;
        } catch (Exception e) {
            map.put("status", "-200");
            map.put("message", e.getMessage());
            return map;
        }

    }

    @RequestMapping("guanzhuGurn")
    public Map guanzhiGurn(String uid, String id) {
        //18.添加关注上师
        Map map = new HashMap<>();
        try {
            stringRedisTemplate.setStringSerializer(new StringRedisSerializer());
            stringRedisTemplate.setKeySerializer(new StringRedisSerializer());
            stringRedisTemplate.opsForSet().add(uid, id);
            /*Set<String> list = stringRedisTemplate.opsForSet().members(uid);
            ArrayList<Gurn> gurns = new ArrayList<>();
            for (String s : list) {
                Gurn gurn = gurnService.queryOneGurn(s);
                gurns.add(gurn);
            }*/
            List<Gurn> list = gurnService.queryAllGurn();
            map.put("list", list);
            map.put("status", "200");
            return map;
        } catch (Exception e) {
            map.put("status", "-200");
            map.put("message", e.getMessage());
            return map;
        }


    }

   /* @RequestMapping("showJishu")
    public List<>*/


}
