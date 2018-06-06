/**
 * Created by ThinkPad User on 2018/3/1.
 */
$(function () {
    var url = window.location.href; //获取当前页url
    var urlQuery = url.queryURLParameter(); //转query

    //读取本地存储
    var searchHistoryData = window.localStorage.getItem("searchHistory");
    var searchHistory = null;
    //搜索历史数据绑定
    if (!searchHistoryData) {//如果曾经没有过搜索历史
        searchHistory = [];
    } else {//如果曾经有过搜索历史,把搜索历史转变为数组
        searchHistory = JSON.parse(searchHistoryData)
    }
    //将搜索历史绑定到页面
    bindSearchHistory(searchHistory);

    //将搜索历史绑定到页面 并绑定搜索历史点击事件
    function bindSearchHistory(data) {
        var historyListHtml = '';
        for (var i = 0; i < data.length; i++) {
            historyListHtml += '<li>' + data[i] + '</li>'
        }
        $(".historyList").html(historyListHtml).find("li").click(function () {
            searchOrderList($(this).text())
        });
    }

    //根据搜索字段向后台获取数据
    function searchOrderList(title) {
        $.ajax({
            type: "POST",
            url: srcConfig.searchOrderList,
            dataType: 'json',
            contentType: "application/json",
            data: JSON.stringify({
                customerName: title,//查询条件可为手机号，订单号，sap订单号，车型，客户姓名
                currentPage: 1,//当前页
                employeeId: urlQuery.employeeid //员工id
            }),
            success: function (data) {
                if (data.code == 0) {
                    if (data.data.datas.length == 0) { //暂无数据
                        alert("未搜索到相关的订单信息");
                        return
                    }
                    //跳转到订单列表页面
                    window.sessionStorage.setItem("orderListData", JSON.stringify(data.data));
                    window.location.href = "./orderList.html?search=" + title + "&employeeid=" + urlQuery.employeeid
                } else {
                    alert(data.message)
                }
            },
            error: function (data) {
                alert(data.message)
            }
        });
    }

    //在输入框输入搜索条件后按回车
    $("#searchInput").bind("search", function () {
        var $this = $(this);
        //将搜索数据存储到历史搜索并本地缓存
        searchHistory.push($this.val());
        window.localStorage.setItem("searchHistory", JSON.stringify(searchHistory));
        //搜索接口
        searchOrderList($this.val());
    });

    //清空历史记录按钮
    $("#clearSearchHistory").click(function () {
        searchHistory = [];
        //将最新搜索历史绑定到页面
        bindSearchHistory(searchHistory);
        //清空本地存储
        window.localStorage.setItem("searchHistory", JSON.stringify(searchHistory))
    })


});