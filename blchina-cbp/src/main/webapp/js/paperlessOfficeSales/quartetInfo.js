/**
 * Created by ThinkPad User on 2018/2/5.
 */
$(function () {
    var reg = { //正则表单验证
        tel: /^1[3|4|5|7|8|9][0-9]\d{8}$/,
        //用来判断电话号码，通常手机号第一位为1，第二位只可能出现3.4.5.7.8，后面剩下的9位数字随机
        IDCard: /^[1-9]\d{16}[\dxX]$/,
        //用来判断身份证，通常第一位不为零，所以取1-9的数字，中间的16位数字随机，最后一位要么是数字要么是X
        UnifiedSocialCreditIdentifier: /[0-9A-HJ-NPQRTUWXY]{2}\d{6}[0-9A-HJ-NPQRTUWXY]{10}/
        //统一社会信用代码
    };

    var url = window.location.href; //获取当前页url
    var urlQuery = url.queryURLParameter(); //转query
    var customerid = urlQuery.customerid; //从URL中获取customerId

    if (customerid) {//Url中customerid是否存在
        //url中存在customerid 则是从订单详情页面跳转过来的 获取该订单的四方信息
        window.sessionStorage.setItem("customerId", "");
        getFourCustomerListData(customerid)
    } else { //判断缓存中是否有customerId
        var sessionCustomerId = window.sessionStorage.getItem("customerId");
        if (sessionCustomerId) { //如果内存中有 读取内存中的信息 然后再获取
            getFourCustomerListData(JSON.parse(sessionCustomerId))
        }
        //否则就是新创建的
    }
    //获取四方信息
    function getFourCustomerListData(id, phonenumber) {
        var getFourCustomerData = {
            "customerid": id
        };
        if (phonenumber) {
            getFourCustomerData.phonenumber = phonenumber;
        }
        $.ajax({
            type: "POST",
            url: srcConfig.getFourCustomerList,
            dataType: 'json',
            contentType: "application/json",
            data: JSON.stringify(getFourCustomerData),
            /* beforeSend: function (request) {
             request.setRequestHeader("MSESSIONID", token);
             },*/
            success: function (data) {
                if (data.code == 0) {
                    bindFourCustomerData(data.data)
                } else {
                    alert(data.message)
                }
            },
            error: function (data) {
                alert(data.message)
            }
        });
    }

    //绑定原有四方信息
    function bindFourCustomerData(data) {
        for (var i = 0; i < data.length; i++) {
            var curData = data[i];
            //["付款方", "收票方", "售达方", "送达方"];
            switch (Number(curData.type)) {
                case 1:
                    bindOneInfo("#payer", curData);
                    break;
                case 2:
                    bindOneInfo("#ticketHolder", curData);
                    break;
                case 3:
                    bindOneInfo("#saleParty", curData);
                    break;
                case 4:
                    bindOneInfo("#shipToParty", curData);
                    break;
            }
        }

        //绑定单条信息
        function bindOneInfo(conName, data) {
            bindSearchCustomer(conName); //为每一方绑定查询功能
            var $list = $(conName).find("input");
            $list.each(function (index, ele) {
                switch (index) {
                    case 0:
                        $($list[0]).val(data.name);
                        break;
                    case 1:
                        $($list[1]).val(data.idcard);
                        break;
                    case 2:
                        $($list[2]).val(data.phonenumber);
                        break;
                    case 3:
                        $($list[3]).val(data.area || '');
                        break;
                    case 4:
                        $($list[4]).val(data.address || '');
                        break;
                }
            })
        }
    }

    //先默认绑定一次
    bindSearchCustomer("#saleParty"); //送达方
    bindSearchCustomer("#payer"); //付款方
    bindSearchCustomer("#ticketHolder"); //收票方
    bindSearchCustomer("#shipToParty"); //送达方
    function bindSearchCustomer(containerName) {
        var inputList = $(containerName).find("input");
        $(inputList[1]).unbind().focus(function () { //为每一方的证件号码位置绑定查询和弹出选择框事件
            var $this = $(this);
            var customerName = $(inputList[0]).val();
            if (!customerName) { //没有输入的时候
                return;
            }
            /*            //当姓名值改变的时候 要重新搜索 否则就不搜索;
             if($(inputList[0]).attr("last") == customerName){
             return;
             }
             //满足搜索条件后 把搜索条件暂存到元素上
             $(inputList[0]).attr("last",$(inputList[0]).val());*/
            $.ajax({
                type: "POST",
                url: srcConfig.searchFourCustomer,
                dataType: 'json',
                contentType: "application/json",
                data: JSON.stringify({"customerName": customerName}),
                /* beforeSend: function (request) {
                 request.setRequestHeader("MSESSIONID", token);
                 },*/
                success: function (data) {
                    if (data.code == 0) {
                        if (data.data.length == 0) { //没有匹配的结果

                        } else if (data.data.length == 1) { //仅有一条
                            bindOneCustomerInfo(data.data[0])
                        } else if (data.data.length > 1) { //有多条
                            dialogSelectId(data.data)
                        }
                    } else {
                        alert(data.message)
                    }
                },
                error: function (data) {
                    alert(data.message)
                }
            });

            function dialogSelectId(data) {
                //获取数据生成DOMList绑定到页面并绑定click事件
                var liHtml = '';
                for (var i = 0; i < data.length; i++) {
                    liHtml += '<li customer="' + i + '"><p>身份证号：<span>' + data[i].idcard + '</span></p><p>联系电话：<span>' + data[i].phonenumber + '</span></p></li>'
                }
                $(".ECSDialogContent-list").html(liHtml).find("li").click(function () {
                    $(".ECSDialog").hide();
                    //根据点选的li 渲染页面数据
                    bindOneCustomerInfo(data[$(this).attr("customer")])
                });

                //出现弹出框
                $(".ECSDialog").show();
                var dialogHeight = $(".ECSDialogContent").height();
                $(".ECSDialogContent").css({  //渲染到正确位置
                    "marginTop": -(dialogHeight / 2),
                    "opacity": 1
                });

                //点击背景 隐藏弹出框
                $(".ECSShadow").unbind("click").click(function () {
                    $(".ECSDialog").hide();
                });

                //点击手动输入按钮
                $("#manualInput").unbind().click(function () {
                    //点击手动输入按钮后  把弹出框隐藏  并且接触此条信息的弹出框事件
                    $(".ECSDialog").hide();
                    $this.addClass("manualInputFlag").focus();
                })
            }

            //绑定单条信息
            function bindOneCustomerInfo(data) {
                $(inputList[1]).val(data.idcard);
                $(inputList[2]).val(data.phonenumber);
                $(inputList[3]).val(data.area || '');
                $(inputList[4]).val(data.address || '');
                if ($this.hasClass("salePartyFlag")) {
                    getFourCustomerListData(data.customerid, data.phonenumber)
                }
            }
        })
    }


    //四方同步按钮
    $("#synchronization").click(function () {
        var salePartyList = [];
        $("#saleParty").find("input").each(function (index, ele) {
            salePartyList.push($(ele).val())
        });
        setOtherInfo(salePartyList);
        function setOtherInfo(data) {
            var payerInfo = $("#payer").find("input");
            var ticketHolderInfo = $("#ticketHolder").find("input");
            var shipToPartyInfo = $("#shipToParty").find("input");
            $(payerInfo).each(function (index, ele) {
                $(ele).val(data[index])
            });
            $(ticketHolderInfo).each(function (index, ele) {
                $(ele).val(data[index])
            });
            $(shipToPartyInfo).each(function (index, ele) {
                $(ele).val(data[index])
            })
        }

        alert("同步已完成")
    });

    //提交按钮
    $(".completeBtn a").click(function () {

        var errorInfo = [];

        function getQuartetInfo(listData, type) {
            var typeName = ["付款方", "收票方", "售达方", "送达方"];
            //四方名称验证
            var name = $(listData[0]).val();
            if (!name) {
                errorInfo.push("请输入" + typeName[type - 1] + "姓名或公司名称\n");
                return
            }

            //四方身份证或统一社会信用代码验证
            var idCard = $(listData[1]).val().toUpperCase();
            if (!idCard) {
                errorInfo.push("请输入" + typeName[type - 1] + "证件号码\n");
                return
            } else {//如果有输入的值 再判断是否符合身份证或者统一社会信用代码规则
                if (!(reg.IDCard.test(idCard)) || !(reg.UnifiedSocialCreditIdentifier.test(idCard))) {
                    errorInfo.push(typeName[type - 1] + "证件号码输入不合法\n");
                    return
                }
            }

            //手机号正则验证
            var phoneNumber = $(listData[2]).val();//四方手机号
            if (!phoneNumber) {
                errorInfo.push("请输入" + typeName[type - 1] + "手机号\n");
                return
            } else if (!(reg.tel.test(phoneNumber))) {
                errorInfo.push(typeName[type - 1] + "手机号输入不合法\n");
                return
            }

            var returnData = {
                type: type,//四方类型 四方类型1:付款方,2:开票方,3:售达方,4:送达方
                typename: typeName[type - 1],//四方类型名称
                name: name,//四方名称
                idcard: idCard,//四方身份证
                phonenumber: phoneNumber,//四方手机号
                area: $(listData[3]).val(),//区域
                address: $(listData[4]).val()//详细地址
            };
            if (customerid) {
                returnData.orderid = urlQuery.orderid;
            }

            return returnData;
        }

        var salePartyObj = getQuartetInfo($("#saleParty").find("input"), 3); //售达方
        var payerObj = getQuartetInfo($("#payer").find("input"), 1); //付款方
        var ticketHolderObj = getQuartetInfo($("#ticketHolder").find("input"), 2); //收票方
        var shipToPartyObj = getQuartetInfo($("#shipToParty").find("input"), 4); //送达方

        if (errorInfo.length != 0) {//有错误信息存在 则弹出错误信息
            alert(errorInfo.join(""));
            return
        }

        var fourCustomerData = {};
        fourCustomerData.fourCustomerList = [payerObj, ticketHolderObj, salePartyObj, shipToPartyObj];

        $.ajax({
            type: "POST",
            url: srcConfig.insertOrUpdateFourCustomer,
            dataType: 'json',
            contentType: "application/json",
            data: JSON.stringify(fourCustomerData),
            /* beforeSend: function (request) {
             request.setRequestHeader("MSESSIONID", token);
             },*/
            success: function (data) {
                if (data.code == 0) {
                    alert(data.message);
                    if (customerid) { //如果存在 证明是从订单页面跳转进来的
                        window.location.href = './orderDetails.html?orderid=' + urlQuery.orderid + '&employeeid=' + urlQuery.employeeid;
                    } else {
                        window.sessionStorage.setItem("customerId", JSON.stringify(data.data));
                        window.location.href = './createAnOrder.html?employeeid=' + urlQuery.employeeid;
                    }
                } else {
                    alert(data.message)
                }
            },
            error: function (data) {
                alert(data.message)
            }
        });
    })

});