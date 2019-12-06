<%@page pageEncoding="UTF-8" contentType="text/html; UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="../boot/css/bootstrap.min.css">
    <link rel="stylesheet" href="../boot/css/back.css">
    <link rel="stylesheet" href="../jqgrid/css/trirand/ui.jqgrid-bootstrap.css">
    <link rel="stylesheet" href="../jqgrid/css/jquery-ui.css">
    <script src="../boot/js/jquery-2.2.1.min.js"></script>
    <script src="../boot/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/boot/js/ajaxfileupload.js"></script>
    <script src="../jqgrid/js/trirand/src/jquery.jqGrid.js"></script>
    <script src="../jqgrid/js/trirand/i18n/grid.locale-cn.js"></script>
    <script src="../boot/js/ajaxfileupload.js"></script>
    <script src="${pageContext.request.contextPath}/kindeditor/kindeditor-all-min.js"></script>
    <script src="${pageContext.request.contextPath}/kindeditor/lang/zh-CN.js"></script>
    <script charset="UTF-8" src="${pageContext.request.contextPath}/echarts/echarts.min.js"></script>
    <script type="text/javascript" src="http://cdn.goeasy.io/goeasy-1.0.3.js"></script>
    <title>持名法舟后台管理系统</title>
    <script>
        //动态回传上师
        $.get("${pageContext.request.contextPath}/article/showgurnlist", function (data) {
            var option = "<option value=''>无名氏创作</option>";
            data.forEach(function (guru) {
                option += "<option value='" + guru.id + "'>" + guru.nick + "</option>"
                /*if (guru.id=="1"){
                    option += "<option selected value='"+guru.id+"'>"+guru.nick_name+"</option>"
                }*/
            });
            $("#gurn_id").html(option);
        }, "json");
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

    </script>
</head>


<body>
<nav class="navbar navbar-default" role="navigation">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" href="#">持名法舟后台管理系统</a>
        </div>
        <div>
            <!--向右对齐-->
            <ul class="nav navbar-nav navbar-right">
                <li><a href="">欢迎:${sessionScope.admin.username}</a></li>
                <li><a href="${pageContext.request.contextPath}/safe/safeExit">退出登录</a></li>
            </ul>
        </div>
    </div>
</nav>
<div class="container-fluid">
    <div class="row">
        <div class="col-xs-2">
            <div class="panel-group" id="accordion">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h4 class="panel-title">
                            <a data-toggle="collapse" data-parent="#accordion"
                               href="#collapseOne">
                                <h3>用户管理</h3>
                            </a>
                        </h4>
                    </div>
                    <div id="collapseOne" class="panel-collapse collapse">
                        <div class="panel-body">
                            <ul class="nav nav-pills nav-stacked">
                                <li>
                                    <a href="javascript:$('#content').load('${pageContext.request.contextPath}/back/user.jsp');"
                                       class="list-group-item">用户信息管理</a></li>
                                <li>
                                    <a href="javascript:$('#content').load('${pageContext.request.contextPath}/back/xushi.jsp');"
                                       class="list-group-item">用户注册趋势</a></li>
                                <li>
                                    <a href="javascript:$('#content').load('${pageContext.request.contextPath}/back/ditu.jsp');"
                                       class="list-group-item">用户注册分布</a></li>
                            </ul>
                        </div>
                    </div>
                </div>
                <div class="panel panel-success">
                    <div class="panel-heading">
                        <h4 class="panel-title">
                            <a data-toggle="collapse" data-parent="#accordion"
                               href="#collapseTwo">
                                <h3>轮播图管理</h3>
                            </a>
                        </h4>
                    </div>
                    <div id="collapseTwo" class="panel-collapse collapse">
                        <div class="panel-body">
                            <ul class="nav nav-pills nav-stacked">
                                <li>
                                    <a href="javascript:$('#content').load('${pageContext.request.contextPath}/back/lunbo.jsp');"
                                       class="list-group-item">轮播图信息</a></li>
                            </ul>
                        </div>
                    </div>
                </div>
                <div class="panel panel-info">
                    <div class="panel-heading">
                        <h4 class="panel-title">
                            <a data-toggle="collapse" data-parent="#accordion"
                               href="#collapseThree">
                                <h3>上师管理</h3>
                            </a>
                        </h4>
                    </div>
                    <div id="collapseThree" class="panel-collapse collapse">
                        <div class="panel-body">
                            <ul class="nav nav-pills nav-stacked">
                                <li>
                                    <a href="javascript:$('#content').load('${pageContext.request.contextPath}/back/shangshi.jsp');"
                                       class="list-group-item">上师信息</a></li>
                            </ul>
                        </div>
                    </div>
                </div>
                <div class="panel panel-warning">
                    <div class="panel-heading">
                        <h4 class="panel-title">
                            <a data-toggle="collapse" data-parent="#accordion"
                               href="#collapseFour">
                                <h3>文章管理</h3>
                            </a>
                        </h4>
                    </div>
                    <div id="collapseFour" class="panel-collapse collapse">
                        <div class="panel-body">
                            <ul class="nav nav-pills nav-stacked">
                                <li>
                                    <a href="javascript:$('#content').load('${pageContext.request.contextPath}/back/wenzhang.jsp');"
                                       class="list-group-item">文章信息</a></li>
                            </ul>
                        </div>
                    </div>
                </div>
                <div class="panel panel-warning">
                    <div class="panel-heading">
                        <h4 class="panel-title">
                            <a data-toggle="collapse" data-parent="#accordion"
                               href="#collapseFive">
                                <h3>专辑管理</h3>
                            </a>
                        </h4>
                    </div>
                    <div id="collapseFive" class="panel-collapse collapse">
                        <div class="panel-body">
                            <ul class="nav nav-pills nav-stacked">
                                <li>
                                    <<a href="javascript:$('#content').load('${pageContext.request.contextPath}/back/album.jsp');"
                                        class="list-group-item">专辑信息</a></li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="col-xs-10">
            <div id="content">
                <div class="container">
                    <div class="jumbotron">
                        <h2>欢迎使用持名法舟后台管理系统！</h2>
                    </div>
                </div>
                <div id="myCarousel" class="carousel slide">
                    <!-- 轮播（Carousel）指标 -->
                    <ol class="carousel-indicators">
                        <li data-target="#myCarousel" data-slide-to="0" class="active"></li>
                        <li data-target="#myCarousel" data-slide-to="1"></li>
                        <li data-target="#myCarousel" data-slide-to="2"></li>
                    </ol>
                    <!-- 轮播（Carousel）项目 -->
                    <div class="carousel-inner">
                        <div class="item active">
                            <img src="../img/3e4d03381f30e924eebbff0d40086e061d95f7b0.jpg" alt="First slide">
                            <div class="carousel-caption">标题 1</div>
                        </div>
                        <div class="item">
                            <img src="../img/009e9dfd5266d016d30938279a2bd40735fa3576.jpg" alt="Second slide">
                            <div class="carousel-caption">标题 2</div>
                        </div>
                        <div class="item">
                            <img src="../img/098ca7cad1c8a786b4e6a0366609c93d71cf5049.jpg" alt="Third slide">
                            <div class="carousel-caption">标题 3</div>
                        </div>
                    </div>
                    <!-- 轮播（Carousel）导航 -->
                    <a class="left carousel-control" href="#myCarousel" role="button" data-slide="prev">
                        <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
                        <span class="sr-only">Previous</span>
                    </a>
                    <a class="right carousel-control" href="#myCarousel" role="button" data-slide="next">
                        <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
                        <span class="sr-only">Next</span>
                    </a>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="panel-footer">
    <h4 style="text-align: center">百知教育 @baizhiedu.com.cn</h4>
</div>

<div class="modal fade" id="myModal2" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="myModalLabel2"><h4><label for="">文章添加</label></h4></h4>
            </div>
            <div class="modal-body">
                <form enctype="multipart/form-data" id="form12">
                    <div class="form-group" hidden>
                        <label for="">编号</label>
                        <input type="text" class="form-control" id="id" name="id">
                    </div>
                    <div class="form-group">
                        <label for="">标题</label>
                        <input type="text" class="form-control" id="title" name="title" placeholder="请输入标题">
                    </div>
                    <div class="form-group">
                        <textarea id="editor_id" name="content" style="width:100%;height:60px;">
                            &lt;strong&gt;HTML内容&lt;/strong&gt;
                        </textarea>

                    </div>


                    <div class="form-group">
                        <label>封面</label>
                        <input type="file" class="form-control" id="cover1" name="cover1">
                    </div>


                    <div class="form-group">
                        <label for="">所属上师</label>
                        <select class="form-control" id="gurn_id">

                        </select>
                    </div>


                </form>
            </div>
            <div class="modal-footer">
                <button type="button" id="guanbi" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" id="tijiao" class="btn btn-primary" data-dismiss="modal">提交</button>
            </div>
        </div>
    </div>
</div>
</body>

</html>