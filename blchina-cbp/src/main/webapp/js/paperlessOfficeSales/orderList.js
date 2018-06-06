/**
 * Created by ThinkPad User on 2018/3/1.
 */
$(function () {
    var url = window.location.href; //获取当前页url
    var queryUrl = url.queryURLParameter(); //转query

    var orderListData = JSON.parse(window.sessionStorage.getItem("orderListData"));
    var currentPage = orderListData.currentPage + 1; //当前页
    var totalPage = orderListData.totalPage; //总页数
    var totalRecord = orderListData.totalRecord; //总条数

    //把本地缓存订单数据绑定到页面上
    bindOrderList(orderListData.datas);
    console.log(orderListData);

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
            window.location.href = "./orderDetails.html?employeeid=" + queryUrl.employeeid + "&orderid=" + $(this).attr("orderid")
        })
    }


    //定义滑动事件
    ~function () {
        var windowHeight = $(window).height(); //窗口高度
        var $body = $("body");
        $body.css("height", windowHeight); //默认时候$('body').height = 0,先赋值
        var startX, startY, moveEndX, moveEndY, X, Y;
        var moveType;
        $body.on("touchstart", function (e) {
            // e.preventDefault();
            startX = e.originalEvent.changedTouches[0].pageX;
            startY = e.originalEvent.changedTouches[0].pageY;
        });
        $body.on("touchmove", function (e) {
            // e.preventDefault();
            moveEndX = e.originalEvent.changedTouches[0].pageX;
            moveEndY = e.originalEvent.changedTouches[0].pageY;
            X = moveEndX - startX;
            Y = moveEndY - startY;
            if (Math.abs(Y) > Math.abs(X) && Y < 0 && $(window).scrollTop() + windowHeight >= $(document).height()) { //手势为从下到上
                //窗口高度加上滚出去的高度大于文档高度时候
                moveType = 1;
                $(".moreInfo").show("fast");
            } else {
                moveType = undefined
            }
        });
        $body.on('touchend', function (e) {
            // e.preventDefault();
            if (moveType) {//moveType 不存在的话 则证明是普通滑动 不需要加载
                getMoreOrder(queryUrl.search);//search存在 证明是从搜索页面跳转过来的否则就是其他页面跳转过来的
                moveType = undefined;
            }
            $(".moreInfo").hide("fast");
        });
    }();


    //获取更多订单信息
    var flag = null;

    function getMoreOrder(type) { //type表示是从那个页面
        if (flag) {//防止网速过慢时多次加载
            return;
        } else {
            flag = 1;
        }
        if (currentPage > totalPage) { //过界判断
            $(".moreInfo p").text("没有更多了");
            flag = null;
            return
        }
        if (type) {  //存在则是从搜索页面跳转过来的  加载更多时需要调用搜索接口
            $.ajax({
                type: "POST",
                url: srcConfig.searchOrderList,
                dataType: 'json',
                contentType: "application/json",
                data: JSON.stringify({
                    customerName: type,//查询条件可为手机号，订单号，sap订单号，车型，客户姓名
                    currentPage: currentPage++,//当前页
                    employeeId: queryUrl.employeeid //员工id
                }),
                success: function (data) {
                    if (data.code == 0) {
                        bindOrderList(data.data.datas)
                    } else {
                        alert(data.message)
                    }
                },
                error: function (data) {
                    alert(data.message)
                }
            });
        } else {//从其他跳转过来的  加载更多时需要普通订单列表接口
            $.ajax({
                type: "POST",
                url: srcConfig.getOrderListEmployee,
                dataType: 'json',
                contentType: "application/json",
                data: JSON.stringify({
                    employeeid: queryUrl.employeeid, //员工id
                    currentPage: currentPage++//当前页
                }),
                success: function (data) {
                    if (data.code == 0) {
                        bindOrderList(data.data.datas)
                    } else {
                        alert(data.message)
                    }
                },
                error: function (data) {
                    alert(data.message)
                }
            });
        }

        //释放
        flag = null;
    }


});