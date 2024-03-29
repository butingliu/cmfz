<%@page pageEncoding="UTF-8" contentType="text/html; UTF-8" isELIgnored="false" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <title>登录</title>
    <style>
        * {
            margin: 0;
            padding: 0;
        }

        html {
            height: 100%;
        }

        body {
            height: 100%;
            background: #fff url(${pageContext.request.contextPath}/img/backgroud.png) 50% 50% no-repeat;
            background-size: cover;
        }

        .dowebok {
            position: absolute;
            left: 50%;
            top: 50%;
            width: 430px;
            height: 550px;
            margin: -300px 0 0 -215px;
            border: 1px solid #fff;
            border-radius: 20px;
            overflow: hidden;
        }

        .logo {
            width: 104px;
            height: 104px;
            margin: 50px auto 80px;
            background: url(${pageContext.request.contextPath}/img/login.png) 0 0 no-repeat;
        }

        .form-item {
            position: relative;
            width: 360px;
            margin: 0 auto;
            padding-bottom: 30px;
        }

        .form-item input {
            width: 288px;
            height: 48px;
            padding-left: 70px;
            border: 1px solid #fff;
            border-radius: 25px;
            font-size: 18px;
            color: #fff;
            background-color: transparent;
            outline: none;
        }

        .form-item button {
            width: 360px;
            height: 50px;
            border: 0;
            border-radius: 25px;
            font-size: 18px;
            color: #1f6f4a;
            outline: none;
            cursor: pointer;
            background-color: #fff;
        }

        #username {
            background: url(${pageContext.request.contextPath}/img/emil.png) 20px 14px no-repeat;
        }

        #password {
            background: url(${pageContext.request.contextPath}/img/password.png) 23px 11px no-repeat;
        }

        #kptcha {
            background: url(${pageContext.request.contextPath}/img/kp.png) 23px 60px no-repeat;
        }

        .tip {
            display: none;
            position: absolute;
            left: 20px;
            top: 52px;
            font-size: 14px;
            color: #f50;
        }

        .reg-bar {
            width: 360px;
            margin: 20px auto 0;
            font-size: 14px;
            overflow: hidden;
        }

        .reg-bar a {
            color: #fff;
            text-decoration: none;
        }

        .reg-bar a:hover {
            text-decoration: underline;
        }

        .reg-bar .reg {
            float: left;
        }

        .reg-bar .forget {
            float: right;
        }

        .dowebok ::-webkit-input-placeholder {
            font-size: 18px;
            line-height: 1.4;
            color: #fff;
        }

        .dowebok :-moz-placeholder {
            font-size: 18px;
            line-height: 1.4;
            color: #fff;
        }

        .dowebok ::-moz-placeholder {
            font-size: 18px;
            line-height: 1.4;
            color: #fff;
        }

        .dowebok :-ms-input-placeholder {
            font-size: 18px;
            line-height: 1.4;
            color: #fff;
        }

        @media screen and (max-width: 500px) {
            * {
                box-sizing: border-box;
            }

            .dowebok {
                position: static;
                width: auto;
                height: auto;
                margin: 0 30px;
                border: 0;
                border-radius: 0;
            }

            .logo {
                margin: 50px auto;
            }

            .form-item {
                width: auto;
            }

            .form-item input, .form-item button, .reg-bar {
                width: 100%;
            }
        }
    </style>
</head>
<body>
<%-- <div class="dowebok">
     <div class="logo"></div>
     <div class="form-item">
         <input id="username" type="text" autocomplete="off" placeholder="邮箱">
         <p class="tip">请输入合法的邮箱地址</p>
     </div>
     <div class="form-item">
         <input id="password" type="password" autocomplete="off" placeholder="登录密码">
         <p class="tip">邮箱或密码不正确</p>
     </div>
     <div class="form-item"><button id="submit">登 录</button></div>

 </div>--%>
<div class="dowebok">
    <div class="modal-dialog" style="margin-top: 10%;">
        <div class="modal-content" style="text-align: center">
            <%--<div class="modal-header">

                <h4 class="modal-title text-center" id="myModalLabel">持明法洲</h4>
            </div>--%>
            <form id="loginForm" method="post" action="${pageContext.request.contextPath}/admin/login">
                <div class="modal-body" id="model-body">
                    <div class="form-item">
                        <input type="text" id="username" class="form-control" placeholder="用户名" autocomplete="off"
                               name="username">
                        <p class="tip">请输入合法的用户名</p>
                    </div>
                    <div class="form-item">
                        <input type="password" id="password" class="form-control" placeholder="密码" autocomplete="off"
                               name="password">
                        <p class="tip">请输入合法的密码</p>
                    </div>
                    <div>
                        <!-- <img alt="这是图片" src="/img/001.png"/> -->
                        <img alt="验证码"
                             onclick="this.src='${pageContext.request.contextPath}/admin/defaultKaptcha?d='+new Date()*1"
                             src="${pageContext.request.contextPath}/admin/defaultKaptcha"/>
                    </div>
                    <div class="form-item">
                        <input type="text" id="kptcha" class="form-control" placeholder="验证码" autocomplete="off"
                               name="vrifyCode">
                        <p class="tip">请输入合法的验证码</p>
                    </div>
                    <span id="msg"></span>
                </div>
                <div class="modal-footer">
                    <div class="form-item">
                        <button type="button" class="btn btn-primary form-control" id="log" onclick="login()">登录
                        </button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
<script src="${pageContext.request.contextPath}/boot/js/jquery-2.2.1.min.js"></script>
<script>
    /* $(function () {
         $('input').val('')
         $('#log').on('click', function () {
             $('.tip').show()
         })
     })*/
    function login() {

        $.ajax({
            url: "${pageContext.request.contextPath}/admin/login",
            type: "POST",
            datatype: "JSON",
            data: $("#loginForm").serialize(),
            success: function (data) {

                if (data != null & data != "") {

                    $("#msg").html("<span class='error' style='color: #4297d7'>" + data + "</span>")

                } else {
                    location.href = "${pageContext.request.contextPath}/back/main.jsp";
                }
            }

        })
    }
</script>

<div style="text-align:center;margin-top: 20px;">
    <h4>持明法洲</h4>
</div>

</body>
</html>