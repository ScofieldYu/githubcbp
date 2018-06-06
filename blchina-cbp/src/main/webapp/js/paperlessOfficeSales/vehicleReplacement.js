/**
 * Created by ThinkPad User on 2018/2/5.
 */
$(function () {
    var url = window.location.href; //获取当前页url
    var urlQuery = url.queryURLParameter(); //转query

    //修改标识
    var editFlag = 1;

    $.ajax({
        type: "POST",
        url: srcConfig.getSecondHandCarOrder,
        dataType: 'json',
        contentType: "application/json",
        data: JSON.stringify({
            orderid: urlQuery.orderid
        }),
        success: function (data) {
            if (data.code == 0) {
                var trueData = data.data[0];//后端返回数据是以数组形式返回的
                if (!trueData) { //没有值则证明是新建
                    trueData = {};  //数据重置
                    editFlag = null;  //将修改标识重置为空
                }
                //绑定页面数据
                bindVehicleReplacement(trueData);
                //绑定点击按钮事件
                bindEvent(trueData)
            } else {
                alert(data.message)
            }
        },
        error: function (data) {
            alert(data.message)
        }
    });
    //绑定二手车数据
    function bindVehicleReplacement(data) {
        // 初始化 Framework7
        var myApp = new Framework7();
        var toolbarTemplate = '<div class="toolbar"><div class="toolbar-inner"><div class="left"><a href="#" class="link close-picker cancel">取消</a></div><div class="right"><a href="#" class="link close-picker">完成</a></div></div></div>';
        // 置换方式 / Framework7 picker
        myApp.picker({
            input: '#exchangemethod',
            rotateEffect: true,
            toolbarTemplate: toolbarTemplate,
            value: [(data.exchangemethod || "非同品牌置换")],
            cols: [{
                width: '100%',
                textAlign: 'center',
                values: ["非同品牌置换", "同品牌置换", "非同品牌特殊置换", "同品牌特殊置换"]
            }],
            onOpen: function (picker) {
                picker.container.find('.cancel').on('click', function () {
                    picker.setValue([(data.exchangemethod || '非同品牌置换')])
                });
            }
        });

        $("#carbrand").val(data.carbrand || ''); //品牌
        $("#carseries").val(data.carseries || ''); //车系
        $("#cartype").val(data.cartype || ''); //车型
        $("#kilometers").val(data.kilometers || ''); //公里数
        $("#realvinno").val(data.realvinno || ''); //车架号
        $("#price").val(data.price || ''); //评估价
        $("#dealprice").val(data.dealprice || ''); //成交价
    }

    //绑定点击事件
    function bindEvent(data) {

        //业务完成时 改变提交按钮的文字内容和点击事件
        if (data.status == 3) {//业务完成标识    type 1:待办  2:进行中   3:完成
            //页面上的input标签内容不能更改
            $("#carbrand").attr("disabled", "disabled"); //品牌
            $("#carseries").attr("disabled", "disabled"); //车系
            $("#cartype").attr("disabled", "disabled"); //车型
            $("#kilometers").attr("disabled", "disabled"); //公里数
            $("#realvinno").attr("disabled", "disabled"); //车架号
            $("#exchangemethod").attr("disabled", "disabled"); //置换方式
            $("#price").attr("disabled", "disabled"); //评估价
            $("#dealprice").attr("disabled", "disabled"); //成交价
            // 提交按钮变成返回 并绑定返回订单详情事件
            $(".completeBtn a").text("返回").unbind().click(function () {
                window.location.href = './orderDetails.html?orderid=' + urlQuery.orderid + '&employeeid=' + urlQuery.employeeid;
            });
        } else {
            // 二手车信息提交
            $(".completeBtn a").unbind().click(function () {
                var secondHandCar = {
                    orderid: urlQuery.orderid, //订单ID
                    carbrand: $("#carbrand").val(), //车辆品牌
                    carseries: $("#carseries").val(), //车辆车系
                    cartype: $("#cartype").val(), //车辆车型
                    kilometers: $("#kilometers").val(), //公里数
                    realvinno: $("#realvinno").val(), //车架号
                    exchangemethod: $("#exchangemethod").val(), //置换方式
                    price: $("#price").val(), //评估价格
                    dealprice: $("#dealprice").val(), //成交价格
                    secondcarid: editFlag //修改主键
                };

                $.ajax({
                    type: "POST",
                    url: srcConfig.saveSecondHandCarOrder,
                    dataType: 'json',
                    contentType: "application/json",
                    data: JSON.stringify(secondHandCar),
                    /* beforeSend: function (request) {
                     request.setRequestHeader("MSESSIONID", token);
                     },*/
                    success: function (data) {
                        if (data.code == 0) {
                            alert(data.message);
                            window.location.href = './orderDetails.html?orderid=' + urlQuery.orderid + '&employeeid=' + urlQuery.employeeid;
                        } else {
                            alert(data.message)
                        }
                    },
                    error: function (data) {
                        console.log(data)
                    }
                });
            })
        }


    }


});