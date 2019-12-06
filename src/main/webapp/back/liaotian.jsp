<%--
  Created by IntelliJ IDEA.
  User: SHUAI
  Date: 2019/11/11
  Time: 20:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" language="java" %>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <script type="text/javascript" src="http://cdn.goeasy.io/goeasy-1.0.3.js"></script>
    <script src="../boot/js/jquery-2.2.1.min.js"></script>
    <script>
        $(function () {
            var goEasy = new GoEasy({
                host:'hangzhou.goeasy.io', //应用所在的区域地址: 【hangzhou.goeasy.io |singapore.goeasy.io】
                appkey: "BC-de6afd1d1d3649e49dc345c422be940b", //替换为您的应用appkey
                onConnected: function () {
                    alert("上线一人");
                },
                onDisconnected: function () {
                    alert("下线一人");
                }

            });
            goEasy.subscribe({
                channel: "shuijiao",
                onMessage: function (message) {
                    var p ='<p>'+message.content+'</p>';
                    $('#heihei').append(p);
                    //$('html, body').animate({scrollTop: $('#heihei').height()}, 50);
                    if(message.content!=null){
                        //var he=$('#heihei').height;
                        $('#heihei')[0].scrollTop=$('#heihei')[0].scrollHeight;
                    }
                },
            });
           /* goEasy.subscribePresence({
                channel: "shuijiao",
                onPresence: function(presenceEvents){
                    console.log("Presence events: ", JSON.stringify(presenceEvents));
                }
            });*/


            $('#btn').click(function () {
                var ss=$('#mes').val();
                //消息发送
                goEasy.publish({
                    channel: "shuijiao", //替换为您自己的channel
                    message: ss //替换为您想要发送的消息内容
                })


            })
        })


    </script>
</head>
<body>
<div>
    <div style="overflow:scroll;width: 500px; height: 600px" id="heihei"></div>
    <input type="text" id="mes">||||||<button type="button" id="btn">发送</button>
</div>

</body>

