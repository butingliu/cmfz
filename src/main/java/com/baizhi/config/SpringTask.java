package com.baizhi.config;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.baizhi.entity.Banner;
import com.baizhi.entity.Banner1;
import com.baizhi.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class SpringTask {
    @Autowired
    private BannerService bannerService;
    @Scheduled(cron = "0 0 0 ? * SUN")
    public void down1() throws Exception {
        System.out.println(new Date());
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
}
