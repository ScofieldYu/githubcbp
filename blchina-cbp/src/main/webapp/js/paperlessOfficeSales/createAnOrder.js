/**
 * Created by ThinkPad User on 2018/2/27.
 */
$(function () {
    var url = window.location.href; //获取当前页url
    var queryUrl = url.queryURLParameter(); //转query
    //监控根据code获取员工ID和token和账号类型
    var getEmployeeTimeBaseData = {
        code: queryUrl.code //用户code
    };
    var sessionEmployeeId = window.sessionStorage.getItem("employeeid");  //当前用户ID
    var customerId = window.sessionStorage.getItem("customerId");  //session中的customerId
    var employeeid = null;  //当前用户ID
    // var employeeid = 10060101;  //当前用户ID
    if (!sessionEmployeeId) {
        $.ajax({
            type: "POST",
            async: false,
            url: srcConfig.getEmployeeidByCodeBase,
            dataType: 'json',
            contentType: "application/json",
            data: JSON.stringify(getEmployeeTimeBaseData),
            success: function (data) {
                if (data.code == 0) {
                    employeeid = data.data.employeeid;
                    window.sessionStorage.setItem("employeeid", JSON.stringify(data.data.employeeid));
                } else {
                    alert(data.message)
                }
            },
            error: function (data) {
                alert(data.message)
            }
        });
    } else {
        employeeid = JSON.parse(sessionEmployeeId)
    }

    //订单状态
    function getOrderstatus(status) {
        switch (Number(status)) {
            case 0:
                return "待下单";
                break;
            case 1:
                return "待支付";
                break;
            case 2:
                return "待确认";
                break;
            case 3:
                return "已完成";
        }
    }

    var today = new Date();
    $("#orderstatus").text(getOrderstatus(0));
    $("#orderdate").text(today.getFullYear() + "年" + (today.getMonth() + 1) + "月" + today.getDate() + "日"); //下单时间

    //右上角更多按钮
    $(".moreBtn").click(function () {
        $(".moreList").toggle("fast")
    });

    //搜索订单
    $("#goSearchOrder").click(function () {
        window.location.href = "./orderSearch.html?employeeid=" + employeeid;
    });

    //订单列表
    $("#goOrderList").click(function () {
        //获取订单列表
        $.ajax({
            type: "POST",
            async: false,
            url: srcConfig.getOrderListEmployee,
            dataType: 'json',
            contentType: "application/json",
            data: JSON.stringify({
                currentPage: 1,//当前页
                employeeid: employeeid
            }),
            success: function (data) {
                console.log(data);
                if (data.code == 0) {
                    if (!data.data) {//暂无数据
                        alert("暂无订单信息");
                        return
                    }
                    //将返回的订单列表结果存储到session 并跳转到订单列表页面
                    window.sessionStorage.setItem("orderListData", JSON.stringify(data.data));
                    window.location.href = "./orderList.html?employeeid=" + employeeid
                } else {
                    alert(data.message)
                }
            },
            error: function (data) {
                alert(data)
            }
        });
    });


    //创建客户信息
    if(customerId) { //如果存在 则证明之前已经维护了客户信息
        $("#customerInfo").find("p").css("color", "#333333").text("已有客户信息");
    }
    $("#goCustomerInfo").click(function () { //客户信息
        window.location.href = './quartetInfo.html?employeeid=' + employeeid;
    });

    //选择车辆信息
    $("#goSelectCar").click(function () {  //选车
        if(customerId){ //如果存在 则证明之前已经维护了客户信息
            window.location.href = './selectCar.html?employeeid=' + employeeid;
        }else {
            alert("请先维护客户信息")
        }
    });

    //选择贷款
    $("#goLoanInfo").click(function () {  //贷款
        // window.location.href = './selectCar.html?employeeid=' + employeeid;
        alert("请先维护客户信息")
    });

    //选择车辆置换
    $("#goCarSubstitution").click(function () {  //车辆置换
        // window.location.href = './selectCar.html?employeeid=' + employeeid;
        alert("请先维护客户信息")
    });

    //选择车险按钮
    $("#goSelectInsurance").click(function () { //车险
        // window.location.href = './carInsurancePrice.html?employeeid=' + employeeid;
        alert("请先维护客户信息")
    });

    //选择上牌按钮
    $("#goLicensing").click(function () {  //验车上牌
        // window.location.href = './carInsurancePrice.html?employeeid=' + employeeid;
        alert("请先维护客户信息")
    });

    //选择精品按钮
    $("#goBoutique").click(function () { //精品
        // window.location.href = './carInsurancePrice.html?employeeid=' + employeeid;
        alert("请先维护客户信息")
    });


});