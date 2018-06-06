/**
 * Created by ThinkPad User on 2018/3/1.
 */
$(function () {
    var url = window.location.href; //获取当前页url
    var queryUrl = url.queryURLParameter(); //转query

    //获取订单列表
    $.ajax({
        type: "POST",
        url: srcConfig.getOrderListCustomer,
        dataType: 'json',
        contentType: "application/json",
        data: JSON.stringify({
            openid: queryUrl.openId,//用户openid
            brandid: queryUrl.brandId//门店id
            // openid: "a",//用户openid
            // brandid: "2002"//门店id
        }),
        /* beforeSend: function (request) {
         request.setRequestHeader("MSESSIONID", token);
         },*/
        success: function (data) {
            if (data.code == 0) {
                if (data.data.length == 0) { //没有订单的时候
                    $("body").html('<div style="box-sizing: border-box;border-top: 1px solid #dddddd;border-bottom: 1px solid #dddddd;height: 0.45rem;line-height: 0.45rem;background: #FFFFFF;"><div class="orderTit"><p>暂无订单<span id="saporderid"></span></p></div></div><div class="orderDetail"><p style="padding-top: 2.5rem;width: 100%;height: 100%;text-align: center;font-size: 0.4rem;font-weight: 900;color: #dedede;">您目前没有订单</p></div></div>');
                } else if (data.data.length == 1) { //仅有一条订单
                    window.location.href = './customerOrderDetails.html?openId=' + queryUrl.openId + '&brandId=' + queryUrl.brandId + '&orderid=' + data.data[0].orderid;
                } else { //有多条订单的时候
                    bindOrderList(data.data)
                }
            } else {
                alert(data.message)
            }
        },
        error: function (data) {
            alert(data.message)
        }
    });


    //把订单数据绑定到页面上
    function bindOrderList(data) {
        var orderHtml = '';
        for (var i = 0; i < data.length; i++) {
            var curData = data[i];
            orderHtml += '<li orderid="' + curData.orderid + '"><em></em>';
            orderHtml += '<div><p>订单编号：<span>' + curData.orderid + '</span></p></div><div>';
            orderHtml += '<p>车型：<span>' + curData.cartype + '</span></p>';
            orderHtml += '<p><b>外观：<span>' + curData.appearanceinterior + '</span></b><b>内饰：<span>' + curData.derivename + '</span></b></p>';
            orderHtml += '<p>车架号：<span>' + curData.realvinno + '</span></p></div></li>'
        }
        $("#orderList").append(orderHtml).find("li").unbind().click(function () {
            window.location.href = "./customerOrderDetails.html?openId=" + queryUrl.openId + "&brandId=" + queryUrl.brandId + "&orderid=" + $(this).attr("orderid")
        })
    }
});