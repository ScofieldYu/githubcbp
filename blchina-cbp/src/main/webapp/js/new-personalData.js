$(function () {
    var reg = { //正则表单验证
        user: /^[a-zA-Z]\w{4,9}$/,
        //用来判断用户名，第一位不能为数字，也就是小写字母或者大写字母，后面的内容\w表示字符（数字字母下划线）
        //要求是5-10位字符，所以出去第一位，还需要4-9位的\w
        tel: /^1[3|4|5|7|8|9][0-9]\d{8}$/,
        //用来判断电话号码，通常手机号第一位为1，第二位只可能出现3.4.5.7.8，后面剩下的9位数字随机
        mail: /^[1-9a-zA-Z_]\w*@[a-zA-Z0-9]+(\.[a-zA-Z]{2,})+$/,
        //用来判断邮箱，通常邮箱没有以0开头的，所以第一位为1-9数字或者小写字母或者大写字母，第二位开始任意字符
        //也可以只有第一位没有第二位，*表示至少0个，@后面同理，小写字母或者大写字母或者数字，.需要转意符，所以写成\.
        //点后面通常是com或者cn或者com.cn，所以是小写字母或者大写字母至少两位
        IDCard: /^[1-9]\d{16}[\dxX]$/,
        //用来判断身份证，通常第一位不为零，所以取1-9的数字，中间的16位数字随机，最后一位要么是数字要么是X
        zipCode: /^[0-9]\d{5}(?!\d)/,
        //邮编验证
        UnifiedSocialCreditIdentifier: /[0-9A-HJ-NPQRTUWXY]{2}\d{6}[0-9A-HJ-NPQRTUWXY]{10}/
        //统一社会信用代码
    };
    /*主标签切换*/
    $("#gonghu1").click(function () {
        $(this).children("div").addClass("t");
        $("#shihu1").children("div").removeClass("t");
        $(".orderDetail").hide();
        $(".form2").hide();
        $(".qiyeAttestation").show();
        $(".form4").show();
    });
    $("#shihu1").click(function () {
        $(this).children("div").addClass("t");
        $("#gonghu1").children("div").removeClass("t");
        $(".orderDetail").show();
        $(".form2").show();
        $(".qiyeAttestation").hide();
        $(".form4").hide();
    });


    //获取token
    var url = window.location.href; //获取当前页url
    // url = 'http://bdl-cbp.blchina.com/cbp/new-carOrder.html?phonenumber=17310626561&customername=闫亚杰&idcardnum=&email=NaN&address=%E5%AE%89%E6%85%A7%E5%8C%97%E9%87%8C%E9%80%B8%E5%9B%AD%E5%B0%8F%E5%8C%BA18%E5%8F%B7%E6%A5%BC1102%E5%8F%B7&postcode=100000&isindex=&isSeal=&brandId=2028&openId=oHsB0t03lquqSnXwyeqtS5_Xgh4E';
    //alert("personalData-----"+ url);
    var tokenQuery = url.queryURLParameter(); //转query
    var base = new Base64();
    //alert(tokenQuery.nickname);
    var secret = base.encode(tokenQuery.phonenumber + '!!' + new Date().getTime());
    var getToken = { // tokenQuery 的样子
        secret: secret, //手机号+“!!”+时间戳base64加密
        nickname: tokenQuery.nickname, //昵称
        wxheadimgurl: tokenQuery.wxheadimgurl, //微信头像
        idcardnum: tokenQuery.idcardnum ? tokenQuery.idcardnum.toUpperCase() : '', //身份证
        openid: tokenQuery.openId, //openId 微信唯一识别id
        brandid: tokenQuery.brandId, //brandId //门店id
        email: tokenQuery.email, //brandId //email
        phonenumber: tokenQuery.phonenumber, //电话号
        customername: tokenQuery.customername //姓名
    };
    var token = '';
    $.ajax({ //获取token
        async: false,
        type: "POST",
        url: srcConfig.getToken,
        dataType: 'json',
        contentType: "application/json",
        data: JSON.stringify(getToken),
        success: function (data) {
            if (data.code == 0) {//返回token成功
                token = data.data;
            } else {
                alert("token-----success"+data.message);
            }
        },
        error: function (data) {
            alert(data.message);
        }
    });

    var customerid = null;
    /*获取个人信息*/
    $.ajax({
        type: "POST",
        url: srcConfig.getCustomerInfo,
        dataType: 'json',
        contentType: "application/json",
        beforeSend: function (request) {
            request.setRequestHeader("MSESSIONID", token);
        },
        data: JSON.stringify({
            openid: getToken.openid
        }),
        success: function (data) {
        	//alert(JSON.stringify(data));
            if (data.code == 0) {//返回用户原有数据成功
                bindData(data.data);
            } else {
                alert(data.message)
            }
        },
        error: function (data) {
            alert(data.message)
        }
    });
    //绑定信息
    function bindData(data) {
        var customerData = data.customer; //客户信息
        var idcardnum = customerData.idcardnum?customerData.idcardnum.toUpperCase() : '';//身份证号
        var address = customerData.address || '';//地址
        //私户
        customerid = customerData.customerid; //customerid
        $('#customername').val(customerData.customername || ''); //用户名
        $('#identitycardnum').val(idcardnum); //身份证号
        if (idcardnum) {
            $('#identitycardnum').attr('disabled', true); //身份证号输入框禁用
        }
        $('#phonenumber').val(customerData.phonenumber || ''); //客户电话
        $('#email').val(customerData.email || ''); //客户电子邮箱
        $('#J_Address').val(address.split(',')[0]); //客户详细地址
        $('#Area').val(address.split(',')[1] || ""); //客户详细地址
        $('#youbian').val(customerData.postcode || ''); //客户邮编
        //公户
        $("#customername1").val(customerData.customername || ''); //联系人姓名
        $("#identitycardnum1").val(idcardnum); //联系人身份证号
        if (idcardnum) {
            $('#identitycardnum1').attr('disabled', true); //身份证号输入框禁用
        }
        $("#phonenumber1").val(customerData.phonenumber || ''); //联系人电话
        $('#email1').val(customerData.email || ''); //客户电子邮箱
        $('#J_Address2').val(address.split(',')[0]); //客户详细地址
        $('#Area1').val(address.split(',')[1] || ""); //客户详细地址
        $('#postcode').val(customerData.postcode || ''); //客户邮编

        //绑定客户其他信息
        var listInfo = data.listinfo; //客户指标等信息
        switch (listInfo.length) {
            case 0:
                break;
            case 1:
                var curFlag = listInfo[0].accounttype; //1私户 2公户
                if (curFlag == 2) {
                    bindCompanyAccounts(listInfo[0])
                }
                break;
            case 2:
                listInfo.map(function (p1, p2, p3) {
                    if (p1.accounttype == 2) {
                        bindCompanyAccounts(p1)
                    }
                });
                break;
            default:
        }
        function bindCompanyAccounts(data) {
            //加载企业信息
            $('#organizename').val(data.organizename);
            $('#organizeid').val(data.organizecode);
        }
    }

    //个人认证
    $("#detailsUpload").on("click", function () {
        var customername = $("#customername").val();
        var idcardnum = $("#identitycardnum").val().toUpperCase();
        var phonenumber = $("#phonenumber").val();
        if (!customername) {//姓名不存在
            alert('姓名不能为空');
            return
        }
        if (!idcardnum) {//身份证信息不存在
            alert('身份证号码不能为空');
            return
        } else if (!(reg.IDCard.test(idcardnum))) {//身份证信息不符合格式
            alert('身份证号码字符输入不合法');
            return
        }
        if (!phonenumber) {//用户电话
            alert('手机号码不能为空');
            return
        } else if (!(reg.tel.test(phonenumber))) {//手机号码字符输入不合法
            alert('手机号码字符输入不合法');
            return
        }
        var selfauthData = {
            "customername": customername,
            "idcardnum": idcardnum,
            "phonenumber": phonenumber,
            "customerid": customerid
        };
        $.ajax({
            type: "POST",
            url: srcConfig.selfauth,
            dataType: 'json',
            contentType: "application/json",
            beforeSend: function (request) {
                request.setRequestHeader("MSESSIONID", token);
            },
            data: JSON.stringify(selfauthData),
            success: function (data) {
                if (data.code == 0) {
                    alert("实名认证成功");
                } else {
                    alert(data.message)
                }
            },
            error: function (data) {
                alert(data.message)
            }
        })
    });
    //企业认证
    $("#qiyeDetailsUpload").on("click", function () {
        var organizename = $("#organizename").val();
        var organizeid = $("#organizeid").val();
        var customername = $("#customername1").val();
        var idcardnum = $("#identitycardnum1").val().toUpperCase();
        var phonenumber = $("#phonenumber1").val();
        if (!organizename) {//企业名称不存在
            alert('企业名称不能为空');
            return
        }
        if (!organizeid) {//统一社会信用代码
            alert('统一社会信用代码不能为空');
            return
        } else if (!(reg.UnifiedSocialCreditIdentifier.test(organizeid))) {//统一社会信用代码不符合格式
            alert('统一社会信用代码字符输入不合法');
            return
        }
        if (!customername) {//姓名不存在
            alert('联系人姓名不能为空');
            return
        }
        if (!idcardnum) {//身份证信息不存在
            alert('联系人身份证号码不能为空');
            return
        } else if (!(reg.IDCard.test(idcardnum))) {//身份证信息不符合格式
            alert('联系人身份证号码字符输入不合法');
            return
        }
        if (!phonenumber) {//用户电话
            alert('联系人手机号码不能为空');
            return
        } else if (!(reg.tel.test(phonenumber))) {//手机号码字符输入不合法
            alert('联系人手机号码字符输入不合法');
            return
        }
        var companyauth = {
            organizecode: organizeid,//组织机构代码
            organizename: organizename,//公司名称
            customername: customername,//联系人姓名
            idcardnum: idcardnum,//身份证
            phonenumber: phonenumber//手机号
        };
        $.ajax({
            type: "POST",
            dataType: 'json',
            url: srcConfig.companyauth,
            contentType: "application/json",
            beforeSend: function (request) {
                request.setRequestHeader("MSESSIONID", token);
            },
            data: JSON.stringify(companyauth),
            success: function (data) {
                if (data.code == 0) {
                    alert("企业认证成功")
                } else {
                    alert(data.code)
                }
            }
        })
    });
    //私户更新
    $("#accomplish").click(function () {
        var upData = {
            phonenumber: $('#phonenumber').val(), //手机号
            customerid: customerid, //客户id
            email: $('#email').val(), //邮箱
            address: $('#J_Address').val() + "," + $('#Area').val(), //地址
            postcode: $('#youbian').val(), //客户邮编
            accounttype: 1 //账户类型1私户2公户
        };
        sendAccomplishData(upData);
    });
    //公户更新
    $("#companyAccountsUpload").click(function () {
        var upCAData = {
            phonenumber: $('#phonenumber').val(), //手机号
            customerid: customerid, //客户id
            email: $('#email1').val(), //邮箱
            address: $('#J_Address').val() + "," + $('#Area1').val(), //地址
            postcode: $('#postcode').val(), //客户邮编
            identitycardnum1: $('#identitycardnum1').val().toUpperCase(), //联系人身份证
            accounttype: 2,//	账户类型1私户2公户
            organizecode: $('#organizeid').val(),//	组织机构代码
            organizename: $('#organizename').val() //	组织机构名称
        };
        if (!upCAData.organizename) {//企业名称不存在
            alert('企业名称不能为空');
            return
        }
        if (!upCAData.organizecode) {//统一社会信用代码
            alert('统一社会信用代码不能为空');
            return
        } else if (!(reg.UnifiedSocialCreditIdentifier.test(upCAData.organizecode))) {//统一社会信用代码不符合格式
            alert('统一社会信用代码字符输入不合法');
            return
        }
        if (!upCAData.customername) {//姓名不存在
            alert('联系人姓名不能为空');
            return
        }
        if (!upCAData.identitycardnum1) {//身份证信息不存在
            alert('联系人身份证号码不能为空');
            return
        } else if (!(reg.IDCard.test(upCAData.identitycardnum1))) {//身份证信息不符合格式
            alert('联系人身份证号码字符输入不合法');
            return
        }
        if (!upCAData.phonenumber) {//用户电话
            alert('联系人手机号码不能为空');
            return
        } else if (!(reg.tel.test(upCAData.phonenumber))) {//手机号码字符输入不合法
            alert('联系人手机号码字符输入不合法');
            return
        }
        sendAccomplishData(upCAData);
    });
    //往后台发送更新信息
    function sendAccomplishData(data) {
        if (!data.phonenumber) {//用户电话
            alert('手机号码不能为空');
            return
        } else if (!(reg.tel.test(data.phonenumber))) {//手机号码字符输入不合法
            alert('手机号码字符输入不合法');
            return
        }
        if (data.email) {//邮箱存在
            if (!(reg.mail.test(data.email))) {
                alert('邮箱输入不合法');
                return
            }
        }
        if (data.postcode) {//邮编存在
            if (!(reg.zipCode.test(data.postcode))) {
                alert('邮编输入不合法');
                return
            }
        }
        $.ajax({
            type: "POST",
            url: srcConfig.updateCustmer,
            dataType: 'json',
            contentType: "application/json",
            beforeSend: function (request) {
                request.setRequestHeader("MSESSIONID", token);
            },
            data: JSON.stringify(data),
            success: function (data) {
                if (data.code == 0) {
                    $.dialog({
                        dialogType: 'message',
                        content: '<div style="height: 0.4rem;margin-top: 0.2rem;text-align: center;line-height: 0.4rem;font-size: 0.2rem;">您已成功维护了个人资料</div>',
                        width: '3rem', //弹出框的宽度,以rem为单位的字符串值
                        height: '1.8rem', //弹出框的高度,以rem为单位的字符串值
                        title: '更新成功', //弹出框标题,必传
                        onOk: function () {//点确定的回调(默认无,可不传)
                            if (typeof WeixinJSBridge == "undefined") {
                                if (document.addEventListener) {
                                    document.addEventListener('WeixinJSBridgeReady', closeWindow, false);
                                } else if (document.attachEvent) {
                                    document.attachEvent('WeixinJSBridgeReady', closeWindow);
                                    document.attachEvent('onWeixinJSBridgeReady', closeWindow);
                                }
                            } else {
                                closeWindow();
                            }
                            function closeWindow() {
                                WeixinJSBridge.call('closeWindow');
                            }
                        }
                    })
                } else {
                    alert(data.message);
                }
            },
            error: function (data) {
                alert(data.message)
            }
        })
    }
});