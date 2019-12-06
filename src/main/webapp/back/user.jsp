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
            $('#lunbos').jqGrid({
                styleUI:"Bootstrap",
                url:"${pageContext.request.contextPath}/user/showAllUser",
                datatype:"json",
                mtype:"post",
                colNames:["编号","手机号","状态","姓名","昵称","性别","签名","头像","地址","创建时间","离开时间"],
                colModel:[
                    {name:"id",hidden:true},
                    {name:"phnoe",align:"center",editable:true,editrules:{required:true}},
                    {name:"status",align:"center",editable:true,
                        formatter:function (data) {
                            if (data=="有"){
                                return "展示";
                            }else return "冻结";
                        },edittype:"select",editoptions:{value:"有:展示;无:冻结"}
                    },
                    {name:"realname",align:"center",editable:true,editrules:{required:true}},
                    {name:"nick",align:"center",editable:true,},
                    {name:"sex",align:"center",editable:true,
                        formatter:function (data) {
                            if (data=="1"){
                                return "男";
                            }else return "女";
                        },edittype:"select",editoptions:{value:"1:男;2:女"}
                    },
                    {name:"signature",align:"center",editable:true,editrules:{required:true}},
                    {name:"avator",align:"center",editable:true,edittype:"file",
                        editoptions: {enctype: "multipart/from-data"},
                        formatter: function(cellValue,option,cellObject){
                        return "<img style='height: 80px;width: 180px' src='"+cellValue+"'>"
                        },
                    },
                    {name:"address",align:"center",editable:true,editrules:{required:true}},
                    {name:"create_date",align:"center",editable:true,editrules:{required:true}},
                    {name:"exit_date",align:"center",editable:true,editrules:{required:true}},
                ],
                pager:"#pager",
                    rowNum:2,//这个代表每页显示记录数
                    rowList:[2,4,10,20],//生成可以指定显示每页展示多少条下拉列表
                    viewrecords:true,//显示总记录数
                    // caption:"用户列表",//表格标题
                    //cellEdit:true,//开启单元格编辑功能
                    editurl:"${pageContext.request.contextPath}/user/usercrut",//开启编辑时执行编辑操作的url路径  添加  修改
                    autowidth:true,
                    // toolbar:[true,'top'],
                    multiselect:true,
                /*gridComplete:function(){ //给表格加入一个完成事件
                    //添加的工具栏
                    $("#t_lunbos").empty().append("<button class='btn btn-primary' onclick='saveRow();'>添加</button>&nbsp;&nbsp;<button class='btn btn-primary'>嘿嘿</button>")
                },*/

            }).navGrid("#pager",
                {add:true,edit:true,del:true,search:false,refresh:false,
                edittext: "编辑",
                    addtext:"添加",
                    deltext:"删除"
                }, //开启编辑操作
                {closeAfterEdit:true,height:300,width:600,editCaption:"编辑用户信息",
                    afterSubmit:function (response,postData) {
                        var id = response.responseJSON.id;
                        //后台传来的map中的data属性,存放了运行成功标识 200或其他的
                        var code = response.responseJSON.tishi;

                            $.ajaxFileUpload( {
                                url : "${pageContext.request.contextPath}/user/upload",//用于文件上传的服务器端请求地址
                                fileElementId : 'avator',    //文件上传空间的id属性  <input type="file" id="file" name="file" />
                                // dataType : 'json',       //返回值类型 一般设置为json
                                type:'POST',
                                data:{id:id,st:code},
                                success : function() {
                                    $("#lunbos").trigger("reloadGrid");
                                }
                            });
                            return postData;

                    }
                },//对编辑面板的配置对象
                {closeAfterAdd:true,height:500,width:600,addCaption:"用户添加",
                    afterSubmit:function (response,postData) {
                        var id = response.responseJSON.id;
                        //后台传来的map中的data属性,存放了运行成功标识 200或其他的
                        var code = response.responseJSON.tishi;

                            $.ajaxFileUpload( {
                                url : "${pageContext.request.contextPath}/user/upload",//用于文件上传的服务器端请求地址
                                fileElementId : 'avator',    //文件上传空间的id属性  <input type="file" id="file" name="file" />
                                // dataType : 'json',       //返回值类型 一般设置为json
                                type:'POST',
                                data:{id:id,st:code},
                                success : function() {
                                    $("#lunbos").trigger("reloadGrid");
                                }
                            });
                        return postData;

                    }
                },//对添加面板的配置对象
               /* {},//对删除时的配置对象
                {
                    sopt:['eq','ne','cn']//配置搜索条件如何
                }*/
            );
        });
        //删除一条记录
       /* function delRow(id){
            if(window.confirm("确定要删除吗?")){
                /!*$("#empllist").trigger('reloadGrid');*!/
                //发送ajax请求删除
                var oper="del";
                $.post("${pageContext.request.contextPath}/crut/heihei",{id:id,oper:oper},function(){
                    //刷新表格
                    $("#lunbos").trigger('reloadGrid');//刷新表格
                });
            }

        }*/
        //修改一条记录
        /*function editRow(id){
            console.log(id);
            $("#lunbos").jqGrid('editGridRow', id, {
                height : 300,
                reloadAfterSubmit : false,
                closeAfterEdit:true,
            });
        }*/

        //保存一条记录
       /* function saveRow(){
            //参数1:方法名  参数2......:方法参数
            $("#lunbos").jqGrid('editGridRow', "new", {
                height : 500,
                reloadAfterSubmit : true,
                closeAfterAdd:true
            });

        }*/
    </script>

<div class="col-sm-12">
    <div class="page-header">
        <h2>用户管理</h2>
    </div>
        <ul class="nav nav-tabs">
            <li role="presentation" class="active"><a href="#">用户列表信息</a></li>
            <li role="presentation"><a href="#">注册趋势图</a></li>
            <li role="presentation"><a href="#">地理分布图</a></li>
        </ul>
<table id="lunbos"></table>
<div id="pager" style="height: 50px"></div>
</div>
