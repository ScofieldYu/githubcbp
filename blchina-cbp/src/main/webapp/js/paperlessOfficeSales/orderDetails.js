/**
 * Created by ThinkPad User on 2018/2/5.
 */
$(function () {
    var url = window.location.href; //获取当前页url
    var urlQuery = url.queryURLParameter(); //转query

    $.ajax({
        type: "POST",
        url: srcConfig.getOrderByOrderIdEmployee,
        dataType: 'json',
        contentType: "application/json",
        data: JSON.stringify({
            orderid: urlQuery.orderid,
            employeeid: urlQuery.employeeid
        }),
        /* beforeSend: function (request) {
         request.setRequestHeader("MSESSIONID", token);
         },*/
        success: function (data) {
            if (data.code == 0) {
                console.log(data);
                setDomStyleByAccouttype(data.data);
                bindDataAndEvent(data.data)
            } else {
                alert(data.message)
            }
        },
        error: function (data) {
            alert(data.message)
        }
    });

    // setDomStyleByAccouttype({accouttype:4});
    //根据用户账户 选择在页面中要展示的信息
    function setDomStyleByAccouttype(allData) {
        // 店总	100
        // 销售总监	101
        // 销售经理	102
        // 销售顾问	103
        // 金融顾问	104
        // 精品顾问	105
        // 承保顾问	106
        // 验车上牌专员	107
        // 二手车验车专员	108
        // 二手车评估师	109
        // 财务	110
        // 二手车财务	111
        var $SelectInsurance = $(".item-SelectInsurance");//承保顾问
        var $Boutique = $(".item-Boutique");//精品顾问
        var $CheckCarNum = $(".item-CheckCarNum");//验车上牌专员
        var $Finance = $(".item-Finance");//金融经理
        var $SecondHandCar = $(".item-SecondHandCar"); //9:二手车评估师       10:二手车验车专员

        switch (Number(allData.accouttype)) {
            case 106: //承保顾问
                $SelectInsurance.siblings().not(".title").remove();
                setTaskByType($SelectInsurance, allData.insuranceOrderList);
                break;
            case 105: //精品顾问
                $Boutique.siblings().not(".title").remove();
                setTaskByType($Boutique, allData.boutiqueOrderList);
                break;
            case 107: //验车上牌专员
                $CheckCarNum.siblings().not(".title").remove();
                setTaskByType($CheckCarNum, allData.checkCarNumOrderDTOList);
                break;
            case 104: //金融经理
                $Finance.siblings().not(".title").remove();
                setTaskByType($Finance, allData.financeOrderList);
                break;
            case 108:
            case 109: //109:二手车评估师       108:二手车验车专员
                $SecondHandCar.siblings().not(".title").remove();
                setTaskByType($SecondHandCar, allData.secondHandCarOrderList);
                break;
            default:  //其他人
                //任务位置和状态
                setTaskByType($SelectInsurance, allData.insuranceOrderList);
                setTaskByType($Boutique, allData.boutiqueOrderList);
                setTaskByType($CheckCarNum, allData.checkCarNumOrderDTOList);
                setTaskByType($Finance, allData.financeOrderList);
                setTaskByType($SecondHandCar, allData.secondHandCarOrderList);
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
                        //index+1对应状态码  不相等的时候让不是此状态的其他相应的元素隐藏掉
                        $(ele).remove();
                    }
                    //进行中的任务需要去绑定相关的数据
                    if (taskData[0].status == 2) {
                        //taskList[1]==>任务状态为正在进行中的DOM元素容器
                        bindProcessingData(taskList[1], taskData[0])
                    }
                });
            }
        }

        //进行中的任务数据绑定
        function bindProcessingData(ele, data) {
            var $container = $(ele);
            var containerName = $container.attr("id");

            switch (containerName) {
                case "finance": //金融分期
                    //金融经理电话绑定  ->电话在主数据中
                    $("#financephonenumber").attr("href", "tel:" + allData.financephonenumber);
                    //预约家访时间绑定
                    $("#reservetime").text(data.reservetime);
                    break;
                case "secondHandCar": //车辆置换
                    //二手车评估师电话绑定 ->电话在主数据中
                    $("#secondhandcarphonenumber").attr("href", "tel:" + allData.secondhandcarphonenumber);
                    //预约评估时间绑定
                    $("#reserveSHCTime").text(data.reservetime);
                    break;
                case "selectInsurance": //保险
                    break;
                case "checkCarNum": //验车上牌
                    if (data.buytax != 1) {// 缴纳购置税
                        $(".buytax").hide();
                    } else {
                        $("#buytaxsize").text("完整度" + data.buytaxsize);
                    }

                    if (data.buytax != 1) {//办理临牌
                        $(".tempplate").hide();
                    } else {
                        $("#tempplatecount").text(data.tempplatecount + "次"); //办理临牌次数
                        $("#tempplatesize").text("完整度" + data.tempplatesize);
                    }

                    if (data.checkcar != 1) {//验车上牌
                        $(".tempplate").hide();
                    } else {
                        $("#choosetype").text(data.choosetype);//选号方式
                        $("#reservecheckcartime").text(data.reservecheckcartime);//办理时间
                        $("#checkcarsize").text("完整度" + data.checkcarsize);
                    }
                    break;
                case "boutique": //精品业务
                    //精品办理时间绑定
                    $("#createtime").text(data.createtime);
                    break;
            }
        }

    }


    function bindDataAndEvent(data) {

        $("#saporderid").text(data.orderid); //订单号
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
        $("#buyername").text(data.buyername); //买方姓名
        $("#buyerphonenumber").text(data.buyphonenumber); //买方电话
        $("#customername").text(data.customername); //联系人姓名
        $("#customerphonenumber").text(data.phonenumber); //联系人电话
        $("#cartype").text(data.cartype); //车型
        $("#appearanceinterior").text(data.car ? data.car.carappearance : ""); //外观
        $("#derivename").text(data.car ? data.car.carinterior : ""); //内饰
        $("#realvinno").text(data.realvinno); //车架号

        $(".customerPhone").attr("href", "tel:" + data.buyphonenumber);

        $("#totalprice").text("￥" + data.totalprice); //总价


        //右上角更多按钮
        $(".moreBtn").click(function () {
            $(".moreList").toggle("fast")
        });

        //搜索订单
        $("#goSearchOrder").click(function () {
            window.location.href = "./orderSearch.html?employeeid=" + urlQuery.employeeid;
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
                    employeeid: urlQuery.employeeid
                }),
                success: function (data) {
                    if (data.code == 0) {
                        if (!data.data) {//暂无数据
                            alert("暂无订单信息");
                            return
                        }
                        //将返回的订单列表结果存储到session 并跳转到订单列表页面
                        window.sessionStorage.setItem("orderListData", JSON.stringify(data.data));
                        window.location.href = "./orderList.html?employeeid=" + urlQuery.employeeid
                    } else {
                        alert(data.message)
                    }
                },
                error: function (data) {
                    alert(data)
                }
            });
        });
        //刷新订单
        $("#refreshOrder").click(function () {
            window.location.reload()
        });

        //四方信息
        $("#goCustomer").click(function () {
            window.location.href = './quartetInfo.html?orderid=' + urlQuery.orderid + '&employeeid=' + urlQuery.employeeid + '&customerid=' + data.customerid;
        });

        //物流信息
        $("#logisticsInfo").html(data.logistics ? data.logistics.status + "<b></b>" : "暂无物流信息<b></b>").click(function () {
            window.location.href = '../logisticsInformation.html?orderid=' + urlQuery.orderid + '&employeeid=' + urlQuery.employeeid + '&customerid=' + data.customerid;
        });

        //交车时间
        if (data.returnCarTime) {
            $("#returnCarTime").html(data.returnCarTime.date + "<b></b>").click(function () {
                window.location.href = '../checkTransferTheCar.html?orderid=' + urlQuery.orderid + '&employeeid=' + urlQuery.employeeid + '&customerid=' + data.customerid;
            })
        } else {
            $("#returnCarTime").html("设置<b></b>").click(function () {
                window.location.href = '../new-bookingHandCar.html?orderid=' + urlQuery.orderid + '&employeeid=' + urlQuery.employeeid + '&customerid=' + data.customerid;
            })
        }

        //选择车辆信息
        $("#goSelectCar").click(function () {  //选车
            window.location.href = './selectCar.html?orderid=' + urlQuery.orderid + '&employeeid=' + urlQuery.employeeid;
        });

        //贷款->金融分期
        $(".goFinance").click(function () {
            window.location.href = '../bdlSell_finance.html?orderid=' + urlQuery.orderid + '&employeeid=' + urlQuery.employeeid;
        });
        //车辆置换
        $(".goVehicleReplacement").click(function () {
            window.location.href = './vehicleReplacement.html?orderid=' + urlQuery.orderid + '&employeeid=' + urlQuery.employeeid;
        });
        //选择车险按钮
        $(".goSelectInsurance").click(function () {
            window.location.href = './carInsurancePrice.html?orderid=' + urlQuery.orderid + '&employeeid=' + urlQuery.employeeid;
        });
        //验车上牌
        $(".goValidateCar").click(function () {
            window.location.href = '../bdlSell_validateCar.html?orderid=' + urlQuery.orderid + '&employeeid=' + urlQuery.employeeid;
        });
        //精品
        $(".goCompetitiveProducts").click(function () {
            window.location.href = '../bdlSell_competitiveProducts.html?orderid=' + urlQuery.orderid + '&employeeid=' + urlQuery.employeeid;
        });

        //点击总价跳转到价格列表页面
        $("#goTotalPrice").click(function () {
            window.location.href = './priceDetail.html?orderid=' + urlQuery.orderid + '&employeeid=' + urlQuery.employeeid;
        });

        // 签合同
        $("#signContractBtn").on("click", function () {
            $.ajax({
                type: "POST",
                url: srcConfig.signTemp,
                dataType: 'json',
                contentType: "application/json",
                data: JSON.stringify({
                    saporderid: data.saporderid,
                    orderid: data.orderid,
                    orderstatus: 1
                }),
                success: function (data) {
                    if (data.code == 0) {
                        alert("签署成功");
                        console.log(data);
                    } else {
                        alert(data.message);
                    }
                },
                error: function (data) {
                    alert(data.message)
                }
            });
        })
    }


});


