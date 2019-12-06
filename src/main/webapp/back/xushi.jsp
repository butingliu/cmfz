<%--
  Created by IntelliJ IDEA.
  User: SHUAI
  Date: 2019/11/11
  Time: 20:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<div class="col-sm-12">
    <%--<div id="main" style="width: 600px;height:400px;"></div>
    <script type="text/javascript">
        // 基于准备好的dom，初始化echarts实例
        var myChart = echarts.init(document.getElementById('main'));

        // 指定图表的配置项和数据
        var option = {
            title: {
                text: 'ECharts 入门示例'
            },
            tooltip: {},
            legend: {
                data:['男','女']
            },
            xAxis: {
                data: ["1天","7天","30天","1年"]
            },
            yAxis: {},
            series: [],
        };

        // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option);
        // Ajax异步数据回显
        $.get("${pageContext.request.contextPath}/json/data.json",function (data) {
            myChart.setOption({
                series:[
                    {
                        name: '男',
                        type: 'bar',
                        data: [1,3,5,7],
                    },{
                        name: '女',
                        type: 'bar',
                        data: [2,4,6,8],
                    }
                ]
            })
        },"json")
    </script>--%>
    <!-- 为ECharts准备一个具备大小（宽高）的Dom -->
    <div id="main" style="width: 600px;height:400px;"></div>

    <script type="text/javascript">

        var goEasy = new GoEasy({
            host: 'hangzhou.goeasy.io', //应用所在的区域地址: 【hangzhou.goeasy.io |singapore.goeasy.io】
            appkey: "BC-de6afd1d1d3649e49dc345c422be940b", //替换为您的应用appkey


        });
        goEasy.subscribe({
            channel: "shuijiao",
            onMessage: function (message) {
                var c = JSON.parse(message.content);
                myChart.setOption({
                    series: [
                        {
                            name: '男',
                            type: 'bar',
                            data: c.man,
                        }, {
                            name: '女',
                            type: 'bar',
                            data: c.woman,
                        }
                    ]
                })
            },
        });
        // 基于准备好的dom，初始化echarts实例
        var myChart = echarts.init(document.getElementById('main'));

        var option = {
            title: {
                text: 'ECharts 入门示例'
            },
            tooltip: {},
            legend: {
                data: ['男', '女']
            },
            xAxis: {
                data: ["1天", "7天", "30天", "1年"]
            },
            yAxis: {},
            series: [],
        };

        // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option);
        // Ajax异步数据回显
        $.get("${pageContext.request.contextPath}/user/xushi", function (data) {
            console.table(data);
            myChart.setOption({
                series: [
                    {
                        name: '男',
                        type: 'bar',
                        data: data.man,
                    }, {
                        name: '女',
                        type: 'bar',
                        data: data.woman,
                    }
                ]
            })
        }, "json")
    </script>
</div>
