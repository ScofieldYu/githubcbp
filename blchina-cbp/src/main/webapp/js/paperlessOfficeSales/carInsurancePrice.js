/**
 * Created by ThinkPad User on 2018/1/30.
 */
$(function () {
    var url = window.location.href; //获取当前页url
    var urlQuery = url.queryURLParameter(); //转query

    //获取所有保险信息名称
    $.ajax({
        async: false,
        type: "POST",
        url: srcConfig.getInsuranceInfo,
        dataType: 'json',
        contentType: "application/json",
        data: JSON.stringify({
            orderid: urlQuery.orderid
        }),
        success: function (data) {
            if (data.code == 0) {
                console.log(data);
                bindDomData(data.data);
            } else {
                alert(data.message)
            }
        },
        error: function (data) {
            alert(data.message)
        }
    });

    //绑定保险公司等页面信息数据和事件
    function bindDomData(data) {

        //不计免赔 按钮点击效果
        $("#issign").click(function () {
            $(this).toggleClass('on');
        });

        //绑定保险公司信息
        bindListCompany(data.listCompany);
        function bindListCompany(data) {
            var listCompanyHtml = '';
            for (var i = 0; i < data.length; i++) {
                listCompanyHtml += '<li insurancecompanyid="' + data[i].insurancecompanyid + '" insurancecompanyname="' + data[i].insurancecompanyname + '"><em></em><span>' + data[i].insurancecompanyname + '</span></li>';
            }
            $("#listCompany").html(listCompanyHtml).find("li").unbind().click(function () {
                $(this).addClass("on").siblings().removeClass("on")
            });
        }

        //绑定三者险信息
        bindThreeInsurance(data.threeInsuranceList);
        function bindThreeInsurance(data) {
            $("#threeInsurance").attr("insuranceid", data.insuranceid).attr("insurancetype", data.insurancetype).find(".insuranceTitle p").text(data.insurancename);
            var ListData = data.limitList;
            var ListHtml = '';
            for (var i = 0; i < ListData.length; i++) {
                ListHtml += '<li price="' + ListData[i].limitaccount + '"><em></em><span>' + ListData[i].limitaccount + '万</span></li>';
            }
            $("#threeInsuranceList").html(ListHtml);
            bindOtherEleEvent("threeInsuranceList");
        }

        //绑定车上人员险信息
        bindCarUsers(data.carUserList);
        function bindCarUsers(data) {
            $("#carUsers").attr("insuranceid", data.insuranceid).attr("insurancetype", data.insurancetype).find(".insuranceTitle p").text(data.insurancename);
            var ListData = data.limitList;
            var ListHtml = '';
            for (var i = 0; i < ListData.length; i++) {
                ListHtml += '<li price="' + ListData[i].limitaccount + '"><em></em><span>' + ListData[i].limitaccount + '万</span></li>';
            }
            $("#carUsersList").html(ListHtml);
            bindOtherEleEvent("carUsersList");
        }

        //绑定划痕险信息
        bindScratchList(data.scratchList);
        function bindScratchList(data) {
            $("#scratchInsurance").attr("insuranceid", data.insuranceid).attr("insurancetype", data.insurancetype).find(".insuranceTitle p").text(data.insurancename);
            var ListData = data.limitList;
            var ListHtml = '';
            for (var i = 0; i < ListData.length; i++) {
                ListHtml += '<li price="' + ListData[i].limitaccount + '"><em></em><span>' + ListData[i].limitaccount + '</span></li>';
            }
            $("#scratchList").html(ListHtml);
            bindOtherEleEvent("scratchList");
        }

        //绑定其他保险信息
        bindOtherList(data.otherList);
        function bindOtherList(data) {
            var ListHtml = '';
            for (var i = 0; i < data.length; i++) {
                ListHtml += '<li class="' + (data[i].insurancename.length > 3 ? "widthDefault" : "") + '" insuranceid="' + data[i].insuranceid + '" insurancetype="' + data[i].insurancetype + '"><em></em><span>' + data[i].insurancename + '</span></li>';
            }
            $("#otherList").html(ListHtml).find("li").unbind().click(function () {
                $(this).toggleClass("on");
            })
        }

        //发票类型
        $("#insuranceinvoice").find("li").click(function () {
            $(this).addClass("on").siblings().removeClass("on")
        });

        //是否赠保险
        $("#giveinsurance").find("li").click(function () {
            $(this).addClass("on").siblings().removeClass("on")
        });


        //带有其他选项的li的click事件
        function clickEvent() {
            var $this = $(this);
            $this.toggleClass("on");
            if ($this.hasClass("on")) {
                $this.siblings().removeClass("on");
                $this.parent().next(".Other").removeClass("on").find("input").blur();
            }
        }

        function bindOtherEleEvent(containerName) {
            var $insuranceContainer = $("#" + containerName);
            $insuranceContainer.find("li").unbind().click(clickEvent);
            $insuranceContainer.next(".Other").click(function (e) {
                var $this = $(this);
                $this.toggleClass("on");
                if ($this.hasClass("on")) {
                    $insuranceContainer.find("li").removeClass("on");
                    $this.find("input").focus();
                } else {
                    $this.find("input").blur();
                }
            })
        }

    }

    //根据orderId查询保险
    $.ajax({
        type: "POST",
        url: srcConfig.getInsuranceInfoByOrderId,
        dataType: 'json',
        contentType: "application/json",
        data: JSON.stringify({
            orderid: urlQuery.orderid
        }),
        success: function (data) {
            console.log(data);
            if (data.code == 0) {
                if (data.data) {
                    bindTrueData(data.data);
                    return
                }
                bindTrueData({});
            } else {
                alert(data.message)
            }
        },
        error: function (data) {
            alert(data.message)
        }
    });

    function bindTrueData(data) {
        // insuranceInvoice   //保险增值税发票1--普通，2--专票

        //被选中的保险公司
        var $company = $("#listCompany");
        if (data.insurancecompanyid) {
            $company.find("li").each(function (index, ele) {
                var $ele = $(ele);
                if ($ele.attr("insurancecompanyid") == data.insurancecompanyid) {
                    $ele.addClass("on")
                }
            })
        } else {
            $company.find("li:first-child").addClass("on")
        }

        //是否不计免赔  Y是N否
        if (data.isnodeductible == "Y") {
            $("#issign").addClass('on');
        } else {
            $("#issign").removeClass('on');
        }

        //第一受益人
        $("#insurancebeneficiary").val(data.insurancebeneficiary || '');

        //发票类型
        if (data.insuranceinvoice == 2) {
            $("#insuranceinvoice").find("li:last-child").addClass("on")
        } else {
            $("#insuranceinvoice").find("li:first-child").addClass("on")
        }
        //是否赠保险
        if (data.giveinsurance == 2) {
            $("#giveinsurance").find("li:last-child").addClass("on")
        } else {
            $("#giveinsurance").find("li:first-child").addClass("on")
        }

        //保险和保额绑定
        if (data.extList) {
            var insuranceList = data.extList;
            for (var i = 0; i < insuranceList.length; i++) {
                var insurance = insuranceList[i];
                switch (Number(insurance.insurancetype)) {
                    //判断保险类型 1:三者  2:车上人员  3:划痕 4:其他  5:商业  6:交强险
                    case 1:
                        insuranceListBindData("threeInsuranceList", insurance.insurancelimit);
                        break;
                    case 2:
                        insuranceListBindData("carUsersList", insurance.insurancelimit);
                        break;
                    case 3:
                        insuranceListBindData("scratchList", insurance.insurancelimit);
                        break;
                    case 4: //其他险种  (多选)
                        $("#otherList").find("li").each(function (index, ele) {
                            if ($(ele).attr("insuranceid") == insurance.insuranceid) {
                                $(ele).addClass("on");
                            }
                        });
                        break;
                    case 5:
                        $("#businessInsurance").val(insurance.insurancelimit);
                        break;
                    case 6:
                        $("#compulsoryInsurance").val(insurance.insurancelimit);
                        break;
                }

            }

            //带有其他选项的保险类型 绑定已选数据的方法
            function insuranceListBindData(containerName, price) {
                var OtherFlag = null;
                $("#" + containerName).find("li").each(function (index, ele) {
                    if ($(ele).attr("price") == price) {
                        $(ele).addClass("on");
                        OtherFlag = true;
                    }
                });
                //判断OtherFlag是否存在,存在则证明之前的选项已经匹配上;不存在就证明是选择的自定义价格
                if (!OtherFlag) {
                    $("#" + containerName).next(".Other").addClass("on").find("input").val(price)
                }
            }
        }


        //完成按钮事件和数据绑定
        bindSaveInsuranceBtn(data.status)
    }

    function bindSaveInsuranceBtn(status) {
        if (status == 3) { //1:待办  2:进行中  3:已完成
            //移除所有事件
            $("li").unbind();
            $("#issign").unbind();
            $(".Other").unbind();
            $("input").attr("disabled", "disabled");
            $("#saveInsurance").text("返回").unbind().click(function () {
                window.location.href = './orderDetails.html?orderid=' + urlQuery.orderid + '&employeeid=' + urlQuery.employeeid;
            })
        } else {
            //点击完成之后
            $("#saveInsurance").unbind().click(function () {
                if ($("#insurancebeneficiary").val() == "") {
                    alert("请完善第一受益人或被保险人信息");
                    return
                }
                var numReg = /^[0-9]*$/;
                var compulsoryPrice = $("#compulsoryInsurance").val();
                if (!compulsoryPrice) {
                    alert("请完善交强险价格信息");
                    return
                } else if (!numReg.test(compulsoryPrice)) {
                    alert("交强险价格信息必须为数字");
                    return
                }
                var businessPrice = $("#businessInsurance").val();
                if (!businessPrice) {
                    alert("请完善商业险价格信息");
                    return
                } else if (!numReg.test(businessPrice)) {
                    alert("商业险价格信息必须为数字");
                    return
                }

                var insuranceInfoData = {
                    orderid: urlQuery.orderid, //订单id
                    insurancecompanyid: $("#listCompany").find(".on").attr("insurancecompanyid"), //公司id
                    insurancecompanyname: $("#listCompany").find(".on").attr("insurancecompanyname"), //公司名称
                    isnodeductible: $("#issign").hasClass("on") ? "Y" : "N", //是否不计免赔Y是N否
                    insurancebeneficiary: $("#insurancebeneficiary").val(), //受益人
                    insuranceinvoice: $("#insuranceinvoice").find(".on").attr("insuranceinvoice"), //保险增值税发票1--普通，2--专票
                    giveinsurance: $("#giveinsurance").find(".on").attr("giveinsurance") //是否赠保险1--否，2--是
                };

                //查找有选择保额的保险信息并返回
                function findSelectInsurance(containerName) {
                    var $container = $("#" + containerName);
                    var selectInsurance = $container.find(".on");
                    var insurancelimit = null;
                    if (selectInsurance.length != 0) { //如果返回值不为0 则证明此保险有内容被选择
                        if ($(selectInsurance).find("input").length != 0) {//如果返回值不为0 证明被选择的是(其他)选项
                            insurancelimit = $(selectInsurance).find("input").val()
                        } else {
                            insurancelimit = $(selectInsurance).attr("price");
                        }
                    } else {
                        return null
                    }
                    return {
                        orderid: urlQuery.orderid,//订单id
                        insurancelimit: insurancelimit,//保额
                        insuranceid: $container.attr("insuranceid"),//保险id
                        insurancetype: $container.attr("insurancetype")//保险类型
                    }
                }

                var insuranceExtList = [
                    findSelectInsurance("threeInsurance"),//三者险
                    findSelectInsurance("carUsers"),//车上人员险
                    findSelectInsurance("scratchInsurance"),//划痕险
                    {  //商业险
                        orderid: urlQuery.orderid,//订单id
                        insurancelimit: businessPrice,//保额
                        insuranceid: '',//保险id
                        insurancetype: 5//保险类型 5:商业
                    }, {  //交强险
                        orderid: urlQuery.orderid,//订单id
                        insurancelimit: compulsoryPrice,//保额
                        insuranceid: '',//保险id
                        insurancetype: 6//保险类型 6:交强险
                    }
                ];//除其他保险外的所有保险信息和价格

                //可以多选的其他保险
                function selectOtherInsurance() {
                    var insuranceData = $("#otherList").find(".on");
                    var insuranceList = [];
                    if (insuranceData.length != 0) { //不为0 证明有被选择过的选项
                        $(insuranceData).each(function (index, ele) {
                            insuranceList.push({
                                orderid: urlQuery.orderid,//订单id
                                insurancelimit: null,//保额
                                insuranceid: $(ele).attr("insuranceid"),//保险id
                                insurancetype: $(ele).attr("insurancetype")//保险类型
                            });
                        })
                    } else { //其他保险未被选择过
                        return null
                    }
                    return insuranceList
                }

                var otherInsurance = selectOtherInsurance();//其他保险

                insuranceInfoData.extList = insuranceExtList.concat(otherInsurance);

                $.ajax({
                    type: "POST",
                    url: srcConfig.saveOrUpdateInsuranceInfo,
                    dataType: 'json',
                    contentType: "application/json",
                    data: JSON.stringify(insuranceInfoData),
                    success: function (data) {
                        console.log(data);
                        if (data.code == 0) {
                            alert(data.message);
                            window.location.href = './orderDetails.html?orderid=' + urlQuery.orderid + '&employeeid=' + urlQuery.employeeid;
                        } else {
                            alert(data.message)
                        }
                    },
                    error: function (data) {
                        alert(data.message)
                    }
                });
            })
        }
    }
});