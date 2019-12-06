package com.baizhi.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.baizhi.entity.Banner;
import com.baizhi.entity.Banner1;
import com.baizhi.service.BannerService;
import com.baizhi.util.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("banner")
public class BannerController {
    //private String fileName=
    @Autowired
    private BannerService bannerService;

    @RequestMapping("showbanner")
    public Map<String, Object> showBanner(String searchField, String searchString, String searchOper, Integer page, Integer records, Integer rows, Boolean _search) {
        /*if(_search){
            List<Banner> list = bannerService.queryBannerByRows(searchField, searchString, searchOper, (page - 1) * rows, rows);
            List<Banner> banners = bannerService.queryBannerCount(searchField, searchString, searchOper);
            Integer sq = banners.size();
            Integer a =0;
            if(sq%rows==0){
                a = sq/rows;
            }else {
                a =sq/rows+1;
            }
            Map<String,Object> map = new HashMap<String, Object>();
            map.put("records",sq);
            map.put("total",a);
            map.put("rows",list);
            return map;
        }else{*/
        List<Banner> banners = bannerService.queryAllBanner();
        Integer sq = banners.size();
        Integer a = 0;
        if (sq % rows == 0) {
            a = sq / rows;
        } else {
            a = sq / rows + 1;
        }
        List<Banner> b1 = bannerService.queryBannerByPage((page - 1) * rows, rows);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("records", sq);
        map.put("total", a);
        map.put("rows", b1);
        return map;

    }

    @RequestMapping("crut")
    public Map<String, String> crutBanner(String oper, Banner banner, String[] id) {
        Map<String, String> map = new HashMap<>();
        if (oper.equals("del")) {
            //bannerService.deleteBanner(banner.getId());
            bannerService.deleteBanner(id);
            map.put("status", "delOk");
            return map;
        } else if (oper.equals("add")) {
            String x = UUID.randomUUID().toString();
            banner.setId(x);
            bannerService.addBanner(banner);
            map.put("status", "addOk");
            map.put("id", x);
            return map;
        } else {
            System.out.println(banner);
            Banner banner1 = bannerService.selectBanner(banner.getId());
            if (banner.getPic().equals("") || banner.getPic().equals(null)) {
                banner.setPic(banner1.getPic());
            }
            bannerService.modifyBanner(banner);
            System.out.println("2:" + banner);
            map.put("status", "editOK");
            map.put("id", banner.getId());
            return map;
        }

    }

    @RequestMapping("upload")
    public void upload(MultipartFile pic, String id, HttpSession session, HttpServletRequest request) throws IOException {
        String oldname = pic.getOriginalFilename();
        String extend = oldname.substring(oldname.indexOf("."));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmsSS");
        String format = sdf.format(new Date());
        String newName = format + extend;
        File file = new File(session.getServletContext().getRealPath("/back/banner"), newName);
        //pic.transferTo(file);
        String uri = HttpUtil.getHttpUrl(pic, request, request.getSession(), "/back/banner/");
        Banner banner = new Banner();
        banner.setId(id);
        banner.setPic(uri);
        bannerService.modifyBanner(banner);
    }

    @RequestMapping("upload1")
    public void upload1(MultipartFile pic, String id, HttpSession session, HttpServletRequest request) throws IOException {
        int a = 2;
        String oldname = pic.getOriginalFilename();
        if (pic.getOriginalFilename().equals("") || pic.getOriginalFilename().equals(null)) {
            a = 1;
        }
        if (a == 2) {
            String extend = oldname.substring(oldname.indexOf("."));
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmsSS");
            String format = sdf.format(new Date());
            String newName = format + extend;
            File file = new File(session.getServletContext().getRealPath("/back/banner"), newName);
            //pic.transferTo(file);
            String uri = HttpUtil.getHttpUrl(pic, request, request.getSession(), "/back/banner/");
            Banner banner = new Banner();
            banner.setId(id);
            banner.setPic(uri);
            bannerService.modifyBanner(banner);
        }

    }

    @RequestMapping("downloadRowData")
    public void downloadRowData(HttpServletResponse response) throws Exception {
        /*List<Banner> list = bannerService.queryAllBanner();
        Workbook workbook = new HSSFWorkbook();
        String[] a={"标题","状态","描述","创建时间"};
        Sheet sheet = workbook.createSheet("轮播图信息");
        Row row = sheet.createRow(0);
        Font font = workbook.createFont();
        font.setBold(true);
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setFont(font);
        for (int i = 0; i < a.length; i++) {
            row.createCell(i).setCellValue(a[i]);
        }
        DataFormat dataFormat = workbook.createDataFormat();
        short format = dataFormat.getFormat("yyyy-MM-dd");
        CellStyle cellStyle1 = workbook.createCellStyle();
        cellStyle.setDataFormat(format);
        for (int i = 0; i < list.size(); i++) {
            Row row1 = sheet.createRow(i + 1);
            row1.createCell(0).setCellValue(list.get(i).getName());
            row1.createCell(1).setCellValue(list.get(i).getStatus());
            row1.createCell(2).setCellValue(list.get(i).getEdit());
            Cell cell = row1.createCell(3);
            cell.setCellStyle(cellStyle);
            cell.setCellValue(list.get(i).getCreate_date());
        }

        String fileName = "轮播图数据.xls";
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-disposition", "attachment;filename= "+ fileName);
        ServletOutputStream out = response.getOutputStream();
//        workbook.write(new FileOutputStream(new File("F://轮播图.xls")));
        workbook.write(out);
        out.close();
        workbook.close();*/

    }

    @RequestMapping("downloadRowData1")
    public void downloadRowData1(HttpServletResponse response) throws Exception {
        /*String fileName="E:\\"+new Date().getTime()+"轮播图信息.xlsx";

        //response.setContentType("application/vnd.ms-excel;charset=utf-8");
       // response.setHeader("Content-disposition", "attachment;filename= "+ fileName);

        ServletOutputStream out = response.getOutputStream();
        List<Banner> list = bannerService.queryAllBanner();
        ExcelWriter build = EasyExcel.write(fileName, Banner.class).build();
        WriteSheet sheet = EasyExcel.writerSheet("轮播图信息").build();
        build.write(list,sheet);

        build.finish();*/
//        String fileName=new Date().getTime()+"轮播图信息.xlsx";

//        ExcelWriter build = EasyExcel.write(fileName, Banner.class).build();

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
        OutputStream outputStream = response.getOutputStream();
        ExcelWriter write = EasyExcel.write(outputStream, Banner1.class).build();
        WriteSheet sheet = EasyExcel.writerSheet("轮播图信息").build();
        response.setHeader("Content-disposition", "attachment; filename=" + URLEncoder.encode("轮播图信息.xlsx", "UTF-8"));
        response.setContentType("application/msexcel;charset=UTF-8");//设置类型
        write.write(ba, sheet);
        write.finish();
        // ExcelWriter writer = new ExcelWriter(outputStream, ExcelTypeEnum.XLS, true);
//        writer.write(list, sheet);
//        writer.finish();
//
    }
}
