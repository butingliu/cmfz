package com.baizhi.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import lombok.Data;

import java.net.URL;
@Data
@HeadRowHeight(50)
@ContentRowHeight(100)
@ColumnWidth(100 / 8)
public class Banner1 extends Banner {
    @ExcelProperty("封面")
    private URL url;
}
