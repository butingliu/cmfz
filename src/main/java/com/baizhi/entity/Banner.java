package com.baizhi.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import com.alibaba.excel.converters.string.StringImageConverter;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;
import tk.mybatis.mapper.annotation.KeySql;
import tk.mybatis.mapper.code.ORDER;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Data

@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)

@HeadRowHeight(50)
@ContentRowHeight(100)
@ColumnWidth(100 / 8)
public class Banner implements Serializable {
    @Id
    @KeySql(sql = "select uuid()",order = ORDER.BEFORE)
    @ExcelIgnore
    private String id;
    @ExcelProperty("名字")
    private String name;
    //@ExcelProperty(converter = StringImageConverter.class)
    @ExcelIgnore
    private String pic;
    @ExcelProperty("编辑")
    private String edit;
    @ExcelIgnore
    private String uris;
    @ExcelProperty("状态")
    private String status;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "create_date")
    @ExcelProperty("日期")
    private Date create_date;
}
