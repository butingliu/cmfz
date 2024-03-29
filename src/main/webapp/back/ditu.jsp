<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="UTF-8">
    <!-- 引入 echarts.js -->
    <script src="${pageContext.request.contextPath}/echarts/echarts.min.js" charset="UTF-8"></script>
    <script type="text/javascript" src="../echarts/china.js" charset="UTF-8"></script>
    <script src="../boot/js/jquery-2.2.1.min.js"></script>
    <script type="text/javascript" src="http://cdn.goeasy.io/goeasy-1.0.3.js"></script>


    <script type="text/javascript">
        $(function () {
            var goEasy = new GoEasy({
                host: 'hangzhou.goeasy.io', //应用所在的区域地址: 【hangzhou.goeasy.io |singapore.goeasy.io】
                appkey: "BC-de6afd1d1d3649e49dc345c422be940b", //替换为您的应用appkey
            });
            goEasy.subscribe({
                channel: "shuijiao",
                onMessage: function (message) {

                    var c = JSON.parse(message.content);
                    console.log(c);
                    myChart.setOption({

                        series: [{
                            name: '人数',
                            type: 'map',
                            mapType: 'china',
                            roam: false,
                            label: {
                                normal: {
                                    show: false
                                },
                                emphasis: {
                                    show: true
                                }
                            },
                            data: c.ditu
                        }]

                    })
                },
            });
            // 基于准备好的dom，初始化echarts实例
            var myChart = echarts.init(document.getElementById('userMap'));
            var option = {
                title: {
                    text: '用户分布图',
                    subtext: '纯属虚构',
                    left: 'center'
                },
                tooltip: {
                    trigger: 'item'
                },
                legend: {
                    orient: 'vertical',
                    left: 'left',
                    data: ['男', '女']
                },
                visualMap: {
                    left: 'left',
                    top: 'bottom',
                    text: ['高', '低'],           // 文本，默认为数值文本
                    calculable: true
                },
                toolbox: {
                    show: true,
                    orient: 'vertical',
                    left: 'right',
                    top: 'center',
                    feature: {
                        mark: {show: true},
                        dataView: {show: true, readOnly: false},
                        restore: {show: true},
                        saveAsImage: {show: true}
                    }
                },
                /* series: [
                     {
                         name: '人数',
                         type: 'map',
                         mapType: 'china',
                         roam: false,
                         label: {
                             normal: {
                                 show: false
                             },
                             emphasis: {
                                 show: true
                             }
                         },
                         data: [
                             /!*!//Math.round(Math.random()*1000)
                             {name: '北京',value: Math.round(Math.random()*1000)},
                             {name: '天津',value: Math.round(Math.random()*1000)},
                             {name: '上海',value: Math.round(Math.random()*1000)},
                             {name: '重庆',value: Math.round(Math.random()*1000)},
                             {name: '河北',value: Math.round(Math.random()*1000)},
                             {name: '河南',value: Math.round(Math.random()*1000)},
                             {name: '云南',value: Math.round(Math.random()*1000)},
                             {name: '辽宁',value: Math.round(Math.random()*1000)},
                             {name: '湖南',value: Math.round(Math.random()*1000)},
                             {name: '安徽',value: Math.round(Math.random()*1000)},
                             {name: '山东',value: Math.round(Math.random()*1000)},
                             {name: '新疆',value: Math.round(Math.random()*1000)},
                             {name: '江苏',value: Math.round(Math.random()*1000)},
                             {name: '浙江',value: Math.round(Math.random()*1000)},
                             {name: '江西',value: Math.round(Math.random()*1000)},
                             {name: '湖北',value: Math.round(Math.random()*1000)},
                             {name: '广西',value: Math.round(Math.random()*1000)},
                             {name: '甘肃',value: Math.round(Math.random()*1000)},
                             {name: '山西',value: Math.round(Math.random()*1000)},
                             {name: '陕西',value: Math.round(Math.random()*1000)},
                             {name: '吉林',value: Math.round(Math.random()*1000)},
                             {name: '福建',value: Math.round(Math.random()*1000)},
                             {name: '贵州',value: Math.round(Math.random()*1000)},
                             {name: '广东',value: Math.round(Math.random()*1000)},
                             {name: '青海',value: Math.round(Math.random()*1000)},
                             {name: '西藏',value: Math.round(Math.random()*1000)},
                             {name: '四川',value: Math.round(Math.random()*1000)},
                             {name: '宁夏',value: Math.round(Math.random()*1000)},
                             {name: '海南',value: Math.round(Math.random()*1000)},
                             {name: '台湾',value: Math.round(Math.random()*1000)},
                             {name: '香港',value: Math.round(Math.random()*1000)},
                             {name: '澳门',value: Math.round(Math.random()*1000)},
                             {name: '内蒙古',value:Math.round(Math.random()*1000)},
                             {name: '黑龙江',value: Math.round(Math.random()*1000)},*!/
                         ]
                     },
                 ]*/
            };

            // 使用刚指定的配置项和数据显示图表。
            //myChart.setOption(option);
            $.ajax({
                type: "post",
                async: false,
                url: "${pageContext.request.contextPath}/user/ditu",
                success: function (data) {
                    myChart.hideLoading();
                    myChart.setOption({
                        series: [{
                            name: '人数',
                            type: 'map',
                            mapType: 'china',
                            roam: false,
                            label: {
                                normal: {
                                    show: false
                                },
                                emphasis: {
                                    show: true
                                }
                            },
                            data: data
                        }]
                    })
                }
            })
            myChart.setOption(option);
        })

    </script>
</head>

<body>
<!-- 为ECharts准备一个具备大小（宽高）的Dom -->
<div id="userMap" style="width: 600px;height:400px;"></div>

</body>
</html>