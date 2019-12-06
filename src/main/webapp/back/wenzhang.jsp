<%--
  Created by IntelliJ IDEA.
  User: SHUAI
  Date: 2019/11/11
  Time: 20:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" language="java" %>
<script src="${pageContext.request.contextPath}/kindeditor/kindeditor-all-min.js"></script>
<script src="${pageContext.request.contextPath}/kindeditor/lang/zh-CN.js"></script>
<script>

    $(function () {
        $('#myModal2').on('hide.bs.modal', function () {
            //document.getElementById("form12").reset();
            $('#form12')[0].reset();
            KindEditor.html("#editor_id", "");
            $("select").each(function (i, n) {
                $(this).find("option").eq(0).attr("selected", 'selected');
            });

        });


        $('#gruntable').jqGrid({
            styleUI: "Bootstrap",
            url: "${pageContext.request.contextPath}/article/showArticle",
            datatype: "json",
            mtype: "post",
            colNames: ["编号", "标题", "上传时间", "封皮", "上师编号", "内容", "操作"],
            colModel: [
                {name: "id", align: "center", hidden: true},
                {name: "title", align: "center", editable: true, editrules: {required: true}},
                {name: "pub_date", align: "center", editable: true, editrules: {required: true}},
                {
                    name: 'cover', align: "center", formatter: function (data) {
                        return "<img style='width: 80px;height: 80px' src='" + data + "'/>"
                    }, editable: true, edittype: "file", editoptions: {enctype: "multipart/form-data"}
                },
                {name: "sh_id", align: "center", editable: true, editrules: {required: true}},
                {name: "content", align: "center", hidden: true},

                {
                    name: "options",
                    formatter: function (cellvalue, options, rowObject) {
                        var id = rowObject.id;
                        return `<button class="btn btn-success"  onclick="editRow('` + id + `');">修改</button>&nbsp;&nbsp;<button class='btn btn-danger' onclick="delRow('` + id + `');">删除</button>`;

                    }
                }
            ],
            pager: "#pager",
            rowNum: 2,//这个代表每页显示记录数
            rowList: [2, 4, 10, 20],//生成可以指定显示每页展示多少条下拉列表
            viewrecords: true,//显示总记录数
            // caption:"用户列表",//表格标题
            //cellEdit:true,//开启单元格编辑功能
            editurl: "${pageContext.request.contextPath}/article/articlecrut",//开启编辑时执行编辑操作的url路径  添加  修改
            autowidth: true,
            // toolbar:[true,'top'],
            multiselect: true,
            /*gridComplete:function(){ //给表格加入一个完成事件
                //添加的工具栏
                $("#t_lunbos").empty().append("<button class='btn btn-primary' onclick='saveRow();'>添加</button>&nbsp;&nbsp;<button class='btn btn-primary'>嘿嘿</button>")
            },*/

        }).navGrid("#pager",
            {
                add: false, edit: false, del: false, search: false, refresh: false,
                edittext: "编辑",
                addtext: "添加",
                deltext: "删除"
            }, //开启编辑操作
            {
                closeAfterEdit: true, height: 300, width: 600, editCaption: "编辑用户信息", reloadAfterSubmit: true,
                afterSubmit: function (response) {
                    var id = response.responseJSON.id;
                    //后台传来的map中的data属性,存放了运行成功标识 200或其他的
                    var code = response.responseJSON.status;
                    if (code == 'editOK') {
                        $.ajaxFileUpload({
                            url: "${pageContext.request.contextPath}/article/upload1",//用于文件上传的服务器端请求地址
                            fileElementId: 'cover',    //文件上传空间的id属性  <input type="file" id="file" name="file" />
                            // dataType : 'json',       //返回值类型 一般设置为json
                            type: 'POST',
                            data: {id: id},
                            success: function () {
                                $("#gruntable").trigger("reloadGrid");
                            }
                        });
                        return "true";
                    }
                }
            },//对编辑面板的配置对象
            {
                closeAfterAdd: true, height: 500, width: 600, addCaption: "用户添加",

                afterSubmit: function (response, postData) {
                    var id = response.responseJSON.id;
                    //后台传来的map中的data属性,存放了运行成功标识 200或其他的

                    $.ajaxFileUpload({

                        url: "${pageContext.request.contextPath}/article/upload",//用于文件上传的服务器端请求地址
                        fileElementId: 'cover1',    //文件上传空间的id属性  <input type="file" id="file" name="file" />
                        // dataType : 'json',       //返回值类型 一般设置为json
                        type: 'POST',
                        data: {title: $('#title').val(), sh_id: $('#gurn_id').val(), content: $('#content').val()},
                        success: function () {
                            $("#gruntable").trigger("reloadGrid");
                        }
                    });
                    return postData;

                }
            },//对添加面板的配置对象

        );
    });

    function editRow(id) {
        var data = $('#gruntable').jqGrid('getRowData', id);
        var s1 = data.sh_id;
        $('#myModal2').modal("show");
        //$("#gurn_id").empty();
        $.get("${pageContext.request.contextPath}/article/showgurnlist", function (data) {
            var option = "<option value=''>无名氏创作</option>";
            data.forEach(function (guru) {
                option += "<option value='" + guru.id + "'>" + guru.nick + "</option>"
                if (guru.id == s1) {
                    option += "<option selected value='" + guru.id + "'>" + guru.nick + "</option>"
                }
            });
            $("#gurn_id").html(option);
        }, "json");

        $('#title').val(data.title);
        $('#id').val(data.id);
        KindEditor.html("#editor_id", data.content);
        KindEditor.ready(function (K) {
            window.editor = K.create('#editor_id', {
                width: '100%',
                // 1. 指定图片上传路径
                uploadJson: "${pageContext.request.contextPath}/article/uploadImg",
                allowFileManager: true,
                fileManagerJson: "${pageContext.request.contextPath}/article/showAllImgs",
                afterBlur: function () {
                    this.sync();
                }
            });
        });
    }

    function delRow(id) {
        $.get("${pageContext.request.contextPath}/article/removeArticle", {id: id}, function (data) {

        }, "json");
        $("#gruntable").trigger("reloadGrid");
        alert("删除成功");
    }

    $('#tijiao').click(function () {
        $.ajaxFileUpload({
            url: "${pageContext.request.contextPath}/article/upload",//用于文件上传的服务器端请求地址
            fileElementId: 'cover1',    //文件上传空间的id属性  <input type="file" id="file" name="file" />
            dataType: 'json',       //返回值类型 一般设置为json
            type: 'POST',
            data: {
                id: $('#id').val(),
                title: $('#title').val(),
                sh_id: $('#gurn_id').val(),
                content: $('#editor_id').val()
            },
            success: function () {
                $("#gruntable").trigger("reloadGrid");
            }
        });

    });


</script>

<div class="col-sm-12">
    <div class="page-header">
        <h2>文章管理</h2>
    </div>
    <ul class="nav nav-tabs">
        <li role="presentation" class="active"><a href="#">文章列表</a></li>
        <li role="presentation"><a href="#" data-toggle="modal" data-target="#myModal2">添加文章</a></li>
    </ul>
    <table id="gruntable"></table>
    <div id="pager" style="height: 50px"></div>
</div>
