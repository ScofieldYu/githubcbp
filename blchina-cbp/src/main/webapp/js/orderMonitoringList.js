/**
 * Created by ThinkPad User on 2018/1/30.
 */
$(function () {
    var OrderList = JSON.parse(window.sessionStorage.getItem('OrderList'));
    console.log(OrderList)

    //把原始数据按照月份排列组合成新的可用数据
    var trueDataList = []; //生成月份数组
    for (var i = 0; i < OrderList.length; i++) {
        var tempOrder = OrderList[i];  //暂时取出当前的订单
        var date = tempOrder.orderdate.split('-');
        var temp = {
            y: date[0], //年
            m: date[1],  //月
            dataList: []  //要渲染的数据列表
        };
        var dateListTemp = trueDataList[trueDataList.length - 1];  //取出已生成的月份数组的最后一个值
        if (dateListTemp) {
            if (temp.y == dateListTemp.y && temp.m == dateListTemp.m) {  //比较后进行去重
                dateListTemp.dataList.push(tempOrder);
            } else {  //如果日期不同 先生成新的日期,再把新的订单信息放入
                temp.dataList.push(tempOrder);
                trueDataList.push(temp)
            }
        } else { //如果已生成的数组中没有数据,直接把值放入
            temp.dataList.push(tempOrder);
            trueDataList.push(temp)
        }
    }
    console.log(trueDataList)

    function numToWord(num) {
        var Num = Number(num);
        switch (Num) {
            case 1:
                return '一月';
                break;
            case 2:
                return '二月';
                break;
            case 3:
                return '三月';
                break;
            case 4:
                return '四月';
                break;
            case 5:
                return '五月';
                break;
            case 6:
                return '六月';
                break;
            case 7:
                return '七月';
                break;
            case 8:
                return '八月';
                break;
            case 9:
                return '九月';
                break;
            case 10:
                return '十月';
                break;
            case 11:
                return '十一月';
                break;
            case 12:
                return '十二月';
                break;
        }

    }

    // 根据新数据渲染页面
    for (var i = 0; i < trueDataList.length; i++) {
        var orderList = trueDataList[i];
        var dataListStr = '<div class="dataList"><div class="month"><p>' + numToWord(orderList.m) + '</p><i></i></div><ul class="orderList">';
        for (var j = 0; j < orderList.dataList.length; j++) {
            var order = orderList.dataList[j];
            dataListStr += '<li sapOrderId="' + order.saporderid + '"><span>' + order.saporderid + '</span><span>' + order.customername + '</span><span>' + order.employeename + '</span><i></i></li>'
        }
        dataListStr += '</ul></div>';
        $("#orderDataList").append(dataListStr)
    }


    //点击月份折叠效果
    $(".month").click(function () {
        var $this = $(this);
        $this.find("i").toggleClass('on');
        $this.siblings().toggle('normal')
    });

    //点击查看详情
    $("li").click(function () {
        var $this = $(this);
        var sapOrderId = $this.attr('sapOrderId');
        checkDetails(sapOrderId)
    });
    
    function checkDetails(sapOrderId) {
        for (var x = 0; x < OrderList.length; x++) {
            if (sapOrderId == OrderList[x].saporderid) {
                console.log(OrderList[x]);
                window.sessionStorage.setItem('orderDetail',JSON.stringify(OrderList[x]));
                window.location.href = './orderMonitoringDetail.html';
                return
            }
        }
    }
});