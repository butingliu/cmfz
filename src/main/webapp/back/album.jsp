<%--
  Created by IntelliJ IDEA.
  User: SHUAI
  Date: 2019/11/11
  Time: 20:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" language="java" %>


    <script>
        $(function(){
            $("#altable").jqGrid(
                {
                    url : "${pageContext.request.contextPath}/album/showAlbum",
                    datatype : "json",
                    height : 500,
                    colNames : [ 'id', '标题', '级别', '作者', '播音员','专辑简介', '章节数','发行时间','插图' ],
                    colModel : [
                        {name : 'id',hidden: true},
                        {name : 'title',align:"center",editable:true,editrules:{required:true}},
                        {name : 'level',align:"center",editable:true,editrules:{
                            required:true,number:true,minValue:1,maxValue:5}},
                        {name : 'author',align:"center",editable:true,editrules:{required:true}},
                        {name : 'transmit',align:"center",editable:true,editrules:{required:true}},
                        {name : 'edit',align:"center",editable:true,editrules:{required:true}},
                        {name : 'jishu',align:"center",editable:false},
                        {name : 'pub_date',align:"center",editable:true,editrules:{required:true}},
                        {name : 'cover',align:"center",formatter:function (data) {
                                return "<img style='width: 100%' src='"+data+"'/>"
                            },editable:true,edittype:"file",editoptions:{enctype:"multipart/form-data"}}
                    ],
                    rowNum : 2,
                    rowList : [ 2, 4, 6, 8 ],
                    pager : '#pager',
                    sortname : 'id',
                    viewrecords : true,
                    editurl:"${pageContext.request.contextPath}/album/albumcrut",
                    sortorder : "desc",
                    multiselect : false,
                    subGrid : true,

                    autowidth: true,
                    styleUI: 'Bootstrap',
                    subGridRowExpanded : function(subgrid_id, row_id) {
                        addTable(subgrid_id, row_id);

                    },
                    subGridRowColapsed : function(subgrid_id, row_id) {
                        // this function is called before removing the data
                        //var subgrid_table_id;
                        //subgrid_table_id = subgrid_id+"_t";
                        //jQuery("#"+subgrid_table_id).remove();
                    }
                });
            jQuery("#altable").jqGrid('navGrid', '#pager', {
                add : true,
                edit : true,
                del : true,
                addtext: "添加",
                edittext: "编辑",
                deltext: "删除"
            },{
                    closeAfterEdit:true,
                    // frm ---> 表单对象
                    beforeShowForm:function (frm) {
                        //frm.find("#url").attr("disabled", true);
                    },
                    afterSubmit:function (response) {
                        var id = response.responseJSON.id;
                        //后台传来的map中的data属性,存放了运行成功标识 200或其他的
                        var code = response.responseJSON.status;
                        if (code == 'editOK'){
                            $.ajaxFileUpload( {
                                url : "${pageContext.request.contextPath}/album/upload1",//用于文件上传的服务器端请求地址
                                fileElementId : 'cover',    //文件上传空间的id属性  <input type="file" id="file" name="file" />
                                // dataType : 'json',       //返回值类型 一般设置为json
                                type:'POST',
                                data:{id:id},
                                success : function() {
                                    $("#altable").trigger("reloadGrid");
                                }
                            });
                            return "true";
                        }
                    }
                },
                {
                    closeAfterAdd:true,
                    afterSubmit:function (response,postData) {
                        var bannerID = response.responseJSON.id;
                        $.ajaxFileUpload({
                            url:"${pageContext.request.contextPath}/album/upload",
                            datatype:"json",
                            type:"post",
                            data:{id:bannerID},
                            // 指定的上传input框的id
                            fileElementId:"cover",
                            success:function (data) {
                                // 输出上传成功
                                alert("上传成功")
                                // jqgrid重新载入
                                $("#altable").trigger("reloadGrid");
                            }
                        })
                        return postData;
                    }
                },
            );
        });
        function addTable(subgrid_id, row_id){
            // 声明子表格|工具栏id
            var subgridTable = subgrid_id + "table";
            var subgridPage = subgrid_id + "page";
            // 根据下方空间id 创建表格及工具栏
            $("#"+subgrid_id).html("<table id='"+subgridTable+"'></table><div style='height: 50px' id='"+subgridPage+"'></div>")
            // 子表格JqGrid声明
            $("#"+subgridTable).jqGrid({
                url : "${pageContext.request.contextPath}/chap/showChap?al_id="+row_id,
                datatype : "json",
                colNames : [ 'id', '标题','大小','时长','上传时间','音频','操作' ],
                colModel : [
                    {name : "id",  align: "center",hidden: true},
                    {name : "name",align: "center",editable:true,editrules:{required:true}},
                    {name : "bigs",align: "center"},
                    {name : "times",align: "center"},
                    {name : "create_date",align: "center",editable:true},
                    {name : "pic",align: "center",editable:true,edittype:"file",editoptions:{enctype:"multipart/form-data"},
                        formatter:function (value,option,rows) {
                            let names = value.split("_");
                            return names[names.length-1];
                        }

                    },
                    {name:"audio",align:'center',width:300 ,editable: false,edittype:'file',
                        formatter:function (value,option,rows) {

                            return "<audio controls loop preload='auto'>\n" +
                                "<source src='"+rows.pic+"' type='audio/mpeg'>\n"+
                                "<source src='"+rows.pic+"' type='audio/ogg'>\n"+
                                "</audio>";
                        }}
                ],
                rowNum : 5,
                pager : "#"+subgridPage,
                sortname : 'num',
                sortorder : "asc",
                height : '100%',
                editurl:"${pageContext.request.contextPath}/chap/chapcrut?fid="+row_id,
                styleUI:"Bootstrap",

                autowidth:true
            });
            $("#" + subgridTable).jqGrid('navGrid',
                "#" + subgridPage, {
                    edit : true,
                    add : true,
                    del : true,
                    addtext: "添加",
                    edittext: "编辑",
                    deltext: "删除"
                },
                {
                    closeAfterEdit:true,
                    // frm ---> 表单对象
                    beforeShowForm:function (frm) {
                        //frm.find("#url").attr("disabled", true);
                    },
                    afterSubmit:function (response) {
                        var id = response.responseJSON.id;
                        //后台传来的map中的data属性,存放了运行成功标识 200或其他的
                        var code = response.responseJSON.status;
                        if (code == 'editOK'){
                            $.ajaxFileUpload( {
                                url : "${pageContext.request.contextPath}/chap/upload1",//用于文件上传的服务器端请求地址
                                fileElementId : 'pic',    //文件上传空间的id属性  <input type="file" id="file" name="file" />
                                // dataType : 'json',       //返回值类型 一般设置为json
                                type:'POST',
                                data:{id:id},
                                success : function() {
                                    $("#altable").trigger("reloadGrid");
                                }
                            });
                            return "true";
                        }
                    }
                },
                {
                    closeAfterAdd:true,
                    afterSubmit:function (response,postData) {
                        var bannerID = response.responseJSON.id;
                        $.ajaxFileUpload({
                            url:"${pageContext.request.contextPath}/chap/upload",
                            datatype:"json",
                            type:"post",
                            data:{id:bannerID},
                            // 指定的上传input框的id
                            fileElementId:"pic",
                            success:function (data) {
                                // 输出上传成功
                                alert("上传成功")
                                // jqgrid重新载入
                                $("#altable").trigger("reloadGrid");
                            }
                        })
                        return postData;
                    }
                },
                );
        }


    </script>

<div class="col-sm-12">
    <div class="page-header">
        <h2>专辑章节管理</h2>
    </div>
        <ul class="nav nav-tabs">
            <li role="presentation" class="active"><a href="#">专辑章节信息</a></li>
        </ul>
<table id="altable"></table>
<div id="pager" style="height: 50px"></div>
</div>
