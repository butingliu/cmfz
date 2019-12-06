package com.baizhi;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.baizhi.dao.AdminDao;
import com.baizhi.dao.ArticleDao;
import com.baizhi.dao.BannerDao;
import com.baizhi.dao.UserDao;
import com.baizhi.entity.*;

import com.baizhi.service.*;
import org.apache.poi.ss.util.WorkbookUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public  class CmfzApplicationTests {
    @Autowired
    private AdminDao adminDao;
    @Autowired
    private BannerDao bannerDao;
    @Autowired
    private AlbumService albumService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private ArticleDao articleDao;
    @Autowired
    private GurnService gurnService;
    @Autowired
    private BannerService bannerService;
    @Autowired
    private UserService userService;

    @Test
    public void contextLoads() {
        List<Admin> admins = adminDao.selectAll();
        admins.forEach(a-> System.out.println(a));
    }
    @Test
    public void testselect(){
        Admin admin = adminDao.selectOne(new Admin().setUsername("admin"));
        System.out.println(admin);
    }
    @Test
    public void testbanner(){
        List<Banner> banners = bannerDao.queryAllBannerBypage(0, 1);
        //List<Banner> banners = bannerDao.selectAll();
        banners.forEach(a-> System.out.println(a));
    }
    @Test
    public void testAlbum(){
        /*List<Album> albums = albumService.queryAllAlbum();
        albums.forEach(a-> System.out.println(a));*/
        //List<Article> articles = articleService.queryArticleByFid("1");
        List<Article> select = articleDao.select(new Article().setSh_id("1"));
        select.forEach(a -> System.out.println(a));
    }
    @Test
    public void testArticle(){
        Album album = new Album();
        album.setId("3a08b928-8ee4-4459-b77a-38058a31f9af");
        album.setCover("1111112");
        System.out.println(album);
        albumService.updateAlbum(album);
    }
    @Test
    public void testGurn(){
        List<Gurn> gurns = gurnService.queryAllGurn();
        gurns.forEach(a -> System.out.println(a));
    }
    @Test
    public void testEasyPoi(){
        String fileName = "E:\\"+new Date().getTime()+"DemoData.xlsx";
      // String fileName=new Date().getTime()+"轮播图信息.xlsx";
        /*String fileName = "E:\\"+new Date().getTime()+"DemoData.xlsx";
        List<Banner> list = bannerService.queryAllBanner();
        ExcelWriter build = EasyExcel.write(fileName, Banner.class).build();
        // String : 页名称  Int : 第几页    可以同时指定
        WriteSheet sheet = EasyExcel.writerSheet("测试用").build();
        build.write(list,sheet);
        build.finish();*/
        ExcelWriter build = EasyExcel.write(fileName, DemoData.class).build();
        // String : 页名称  Int : 第几页    可以同时指定
        WriteSheet sheet = EasyExcel.writerSheet("测试用").build();
        build.write(data(),sheet);
        build.finish();
    }
    private List<DemoData> data(){
        DemoData demoData1 = new DemoData("Rxx", new Date(), 1.0, "Rxx");
        DemoData demoData2 = new DemoData("Rxx", new Date(), 1.0, "Rxx");
        DemoData demoData3 = new DemoData("Rxx", new Date(), 1.0, "Rxx");
        DemoData demoData4 = new DemoData("Rxx", new Date(), 1.0, "Rxx");
        List<DemoData> demoData = Arrays.asList(demoData1, demoData2, demoData3, demoData4);
        return demoData;
    }
    @Test
    public void qq(){
        String [] aa={"北京","天津","上海","重庆","河北","河南","云南","辽宁","湖南","安徽","山东","新疆",
                "江苏","浙江","江西","湖北","广西","甘肃","山西","陕西","吉林","福建","贵州","广东","青海","西藏",
                "四川","宁夏","海南","台湾","香港","澳门","内蒙古","黑龙江"};
        for (int i = 0; i < 500; i++) {
            Integer a = (int)(1+Math.random()*(33-1+1));
            User user = new User();
            String replace = UUID.randomUUID().toString().replace("", "");
            user.setId(replace).setSex("女").setAddress(aa[a]);
            user.setCreate_date(new Date());
            userService.insertUser(user);
        }
    }
    @Test
    public void ww(){
        Integer a = userService.queryUserBySexAndDay("男", 365);
        System.out.println(a);
    }
    @Test
    public void ee(){
        List<MapVo> mapVos = userService.queryUserBySexAndAddress();
        mapVos.forEach(a -> System.out.println(a));
    }
    @Test
    public void rr() throws MalformedURLException {
        String fileName = "C:\\Users\\SHUAI\\Desktop\\hqxm\\"+new Date().getTime()+"DemoData.xlsx";
        List<Banner> list = bannerService.queryAllBanner();
        ArrayList<Banner1> ba = new ArrayList<>();
        for (Banner b : list) {
           /* String pic = b.getPic();
            URL url=new URL(pic);*/
            Banner1 banner1 = new Banner1();
            banner1.setId(b.getId()).setName(b.getName()).setPic(b.getPic()).setCreate_date(b.getCreate_date()).setEdit(b.getEdit()).setStatus(b.getStatus());
            banner1.setUrl(new URL(b.getPic()));
            //b.setPic(new URL(pic));
            // b.setPic("F:\\Third\\work\\cmfz\\src\\main\\webapp\\back\\banner\\1575289590276_lol娑娜大胸白丝4k壁纸_彼岸图网.jpg");
            ba.add(banner1);
        }
        ExcelWriter build = EasyExcel.write(fileName, Banner1.class).build();
        // String : 页名称  Int : 第几页    可以同时指定
        WriteSheet sheet = EasyExcel.writerSheet("测试用").build();
        build.write(ba,sheet);
        build.finish();
    }
    @Test
    public void yy(){
        userService.removeUser("40ed818b-473e-49f8-9a0b-bc0f7cd510ae");
    }
    }



