/**
 * Created by ThinkPad User on 2018/2/5.
 */
$(function () {
    var url = window.location.href; //获取当前页url
    var queryUrl = url.queryURLParameter(); //转query

    $.ajax({
        type: "POST",
        url: srcConfig.getOrderByOrderId,
        dataType: 'json',
        contentType: "application/json",
        data: JSON.stringify({
            orderid: queryUrl.orderid
        }),
        /* beforeSend: function (request) {
         request.setRequestHeader("MSESSIONID", token);
         },*/
        success: function (data) {
            if (data.code == 0) {
                bindData(data.data)
            } else {
                alert(data.message)
            }
        },
        error: function (data) {
            alert(data.message)
        }
    });


    function bindData(data) {

        //页面数据绑定
        $("#saporderid").text(data.orderid); //SAP订单ID
        $("#carTypeInfo").text(data.cartype); //车型
        $("#realvinno").text(data.realvinno); //车架号
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

        $("#orderstatus").text(getOrderstatus(data.orderstatus));
        $("#orderdate").text(data.orderdate); //下单时间


        //物流信息
        $("#logistics").html(data.logistics ? data.logistics.status + "<i></i>" : "暂无<i></i>").click(function () {
            window.location.href = '../logisticsInformation.html?orderid=' + data.orderid + '&employeeid=' + data.employeeid + '&customerid=' + data.customerid;

        });
        //和交车时间
        if (data.returnCarTime) {
            $("#returnCarTime").html(data.returnCarTime.date + "<i></i>").click(function () {
                window.location.href = '../new-clientAppointmentTime.html?orderid=' + data.orderid + '&employeeid=' + data.employeeid
            })
        } else {
            $("#returnCarTime").html("设置<i></i>").click(function () {
                window.location.href = '../new-clientAppointmentTime.html?orderid=' + data.orderid + '&employeeid=' + data.employeeid
            })
        }


        //根据任务状态渲染任务所显示的位置
        function setTaskByType(taskList, taskData) {
            if (taskData.length == 0) { //返回数据中如果没有相关信息
                taskList.each(function (index, ele) {
                    //让此相关任务的待办显示,其他隐藏
                    if (index != 0) {
                        $(ele).remove()
                    }
                });
            } else { //返回数据中有相关信息
                taskList.each(function (index, ele) {
                    //根据相关信息的状态 判断任务在哪个状态中显示  status 1:待办  2:进行中  3:已完成
                    if (index + 1 != taskData[0].status) {
                        $(ele).remove()
                    }
                });
            }
        }

        var $Finance = $(".item-Finance");//金融分期
        var $SecondHandCar = $(".item-SecondHandCar"); //车辆置换
        var $SelectInsurance = $(".item-SelectInsurance");//保险
        var $CheckCarNum = $(".item-CheckCarNum");//验车上牌
        var $Boutique = $(".item-Boutique");//加装精品
        setTaskByType($Finance, data.financeOrderList); //金融分期
        setTaskByType($SecondHandCar, data.secondHandCarOrderList); //车辆置换
        setTaskByType($SelectInsurance, data.insuranceOrderList); //保险
        setTaskByType($CheckCarNum, data.checkCarNumOrderList); //验车上牌
        setTaskByType($Boutique, data.boutiqueOrderList); //加装精品


        //贷款->金融分期
        $(".goFinance").click(function () {
            window.location.href = '../bdlSell_finance.html?orderid=' + data.orderid + '&employeeid=' + data.employeeid;
        });
        //车辆置换
        $(".goVehicleReplacement").click(function () {
            window.location.href = './customerVehicleReplacement.html?orderid=' + data.orderid + '&employeeid=' + data.employeeid;
        });
        //选择车险按钮
        $(".goSelectInsurance").click(function () {
            window.location.href = './customerCarInsurancePrice.html?orderid=' + data.orderid + '&employeeid=' + data.employeeid;
        });
        //验车上牌
        $(".goValidateCar").click(function () {
            window.location.href = '../bdlSell_validateCar.html?orderid=' + data.orderid + '&employeeid=' + data.employeeid;
        });
        //精品
        $(".goCompetitiveProducts").click(function () {
            window.location.href = '../bdlSell_competitiveProducts.html?orderid=' + data.orderid + '&employeeid=' + data.employeeid;
        });


        //总额
        $("#totalprice").text('￥' + data.totalprice); //总额
        //联系销售顾问
        $("#contactAdviser").attr('href', "tel:" + data.phonenumber); //联系销售顾问

        //发票信息
        var ticketHolderData = null;
        for (var i = 0; i < data.fourCustomerList.length; i++) {
            if (data.fourCustomerList[i].type == 2) { //等于2的时候就是收票方的信息
                ticketHolderData = data.fourCustomerList[i]
            }
        }
        $("#invoiceName").attr("disabled", "disabled").val(ticketHolderData.name);
        $("#invoiceId").attr("disabled", "disabled").val(ticketHolderData.idcard);

        //点击去支付按钮
        $("#signBtn").click(function () {
            // $.ajax() //先请求开票信息数据
            if (data.cbpDeposit.depositstatus == "未支付") {//判断是否已经支付过
                $(".invoiceInfoPage").show().find(".trueInvoiceInfoPage").removeClass('fadeInUp').addClass('fadeInUp animated');   //淡入效果
                return
            }
            alert("已经支付过,如有其他疑问请您联系销售顾问")
        });

        //开票信息页面关闭按钮
        $(".invoiceInfoPageCloseBtn").click(function () {
            $(".invoiceInfoPage").hide();
        });

        //开票信息页面确定按钮
        $(".invoiceInfoSureBtn").click(function () {
            $(".invoiceInfoPage").hide();
            $("#payMethod").text("定金￥" + data.cbpDeposit.depositfirst); //页面title
            $("#depositfirst").attr('payMethod', '定金￥' + data.cbpDeposit.depositfirst).find("b").text(data.cbpDeposit.depositfirst); //定金选项
            $("#depositsum").attr('payMethod', '全款￥' + data.totalprice).find("b").text(data.totalprice); //全款选项
            // 绑定支付页面数据
            $(".paymentPage").show().find(".truePaymentPage")
                .removeClass('fadeInUp')
                .addClass('fadeInUp animated')   //淡入效果
                .find("li").unbind('click').click(function () {  //选择按钮
                var $this = $(this);
                $this.toggleClass('paymentSelect').siblings().toggleClass('paymentSelect');//选择效果;
                //数据绑定交互
                var payMethod = $(".paymentSelect").find(".payMethod").attr("payMethod");
                var payType = $(".paymentSelect").find(".payType").attr("payType");
                $("#payMethod").text(payMethod);
                $("#payType").text(payType);
            })
        });

        //支付页面关闭按钮
        $(".paymentPageCloseBtn").click(function () {
            $(".paymentPage").hide();
        });

        //支付页面确定按钮
        $(".paySureBtn a").click(function () {
            var price = $(".paymentSelect").find(".payMethod").attr("payMethod");
            var priceAry = price.split('￥');
            if (priceAry[0] === '定金') {
                setPrice(priceAry[1], "定金")
            } else {
                setPrice(priceAry[1], "全款")
            }
            function setPrice(Price, payType) {
                if ($(".paymentSelect").find(".payType").attr("payType") === "线下支付") {
                    alert("选择了线下支付" + payType + ",金额为:" + Price)
                } else {
                    alert("选择了微信支付" + payType + ",金额为:" + Price);
                    var ajaxBackData = null;
                    alert("这是点支付后的订单ID"+queryUrl.orderid);
                    $.ajax({
                        async: false,
                        type: "POST",
                        url: srcConfig.getPayOrderParam,
                        dataType: 'json',
                        data: JSON.stringify({orderId: queryUrl.orderid}),
                        contentType: "application/json",
                        success: function (data) {
                            if (data.code == 0) {
                                ajaxBackData = data.data;

                                alert("这是getPayOrderParam返回的数据"+JSON.stringify(data.data));
                            } else {
                                alert(data.message)
                            }
                        },
                        error: function (data) {
                            alert(data.message)

                        }
                    });
                    alert("这是getPayOrder发送的数据"+JSON.stringify(ajaxBackData));
                    $.ajax({
                        type: "POST",
                        url: srcConfig.getPayOrder2,
                        dataType: 'json',
                        data: JSON.stringify(ajaxBackData),
                        contentType: "application/json",
                        success: function (data) {

                            alert("这是getPayOrder返回的数据"+JSON.stringify(data));
                            if (data.code == 0) {
                                function onBridgeReady() {
                                    WeixinJSBridge.invoke(
                                        'getBrandWCPayRequest', {
                                            "appId": data.data.appid,     //公众号名称，由商户传入
                                            "timeStamp": data.data.timeMillis,         //时间戳，自1970年以来的秒数
                                            "nonceStr": data.data.nonce_str, //随机串
                                            "package": "prepay_id=" + data.data.prepay_id,
                                            "signType": "MD5",         //微信签名方式：
                                            "paySign": data.data.paySign //微信签名
                                        },
                                        function (res) {
                                            if (res.err_msg == "get_brand_wcpay_request:ok") {// 使用以上方式判断前端返回,微信团队郑重提示：res.err_msg将在用户支付成功后返回    ok，但并不保证它绝对可靠。
                                                alert('支付完成');
                                                $thisBtn.hide()
                                            } else {
                                                alert('支付失败,请重新支付')
                                            }
                                        }
                                    );
                                }

                                if (typeof WeixinJSBridge == "undefined") {
                                    if (document.addEventListener) {
                                        document.addEventListener('WeixinJSBridgeReady', onBridgeReady, false);
                                    } else if (document.attachEvent) {
                                        document.attachEvent('WeixinJSBridgeReady', onBridgeReady);
                                        document.attachEvent('onWeixinJSBridgeReady', onBridgeReady);
                                    }
                                } else {
                                    onBridgeReady();
                                }
                            } else {
                                alert(data.data)
                            }
                        },
                        error: function (data) {
                            alert("服务器链接失败或超时")
                        }
                    });
                }
                $(".paymentPage").hide();
            }
        })

    }


});