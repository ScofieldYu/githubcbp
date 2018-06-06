$(function () {
    var reg = { //正则表单验证
        user:/^[a-zA-Z]\w{4,9}$/,
        //用来判断用户名，第一位不能为数字，也就是小写字母或者大写字母，后面的内容\w表示字符（数字字母下划线）
        //要求是5-10位字符，所以出去第一位，还需要4-9位的\w
        tel:/^1[3|4|5|7|8|9][0-9]\d{8}$/,
        //用来判断电话号码，通常手机号第一位为1，第二位只可能出现3.4.5.7.8，后面剩下的9位数字随机
        mail:/^[1-9a-zA-Z_]\w*@[a-zA-Z0-9]+(\.[a-zA-Z]{2,})+$/,
        //用来判断邮箱，通常邮箱没有以0开头的，所以第一位为1-9数字或者小写字母或者大写字母，第二位开始任意字符
        //也可以只有第一位没有第二位，*表示至少0个，@后面同理，小写字母或者大写字母或者数字，.需要转意符，所以写成\.
        //点后面通常是com或者cn或者com.cn，所以是小写字母或者大写字母至少两位
        IDCard:/^[1-9]\d{16}[\dxX]$/,
        //用来判断身份证，通常第一位不为零，所以取1-9的数字，中间的16位数字随机，最后一位要么是数字要么是X
        zipCode:/[1-9]d{5}(?!d)/,
        //邮编验证
        UnifiedSocialCreditIdentifier:/[0-9A-HJ-NPQRTUWXY]{2}\d{6}[0-9A-HJ-NPQRTUWXY]{10}/
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
    // url = 'http://bdl-cbp.blchina.com/cbp/new-carOrder.html?phonenumber=17310626561&customername=闫亚杰&idcardnum=&email=NaN&address=%E5%AE%89%E6%85%A7%E5%8C%97%E9%87%8C%E9%80%B8%E5%9B%AD%E5%B0%8F%E5%8C%BA18%E5%8F%B7%E6%A5%BC1102%E5%8F%B7&postcode=100000&isindex=&isSeal=&brandId=2002&openId=oHsB0t_egxKRlGZC6aLwptLYdbfA';
    alert(url)
    var tokenQuery = url.queryURLParameter(); //转query
    var base = new Base64();
    var secret = base.encode(tokenQuery.phonenumber + '!!' + new Date().getTime());
    var getToken = { // tokenQuery 的样子
        secret: secret, //手机号+“!!”+时间戳base64加密
        nickname: tokenQuery.nickname, //昵称
        wxheadimgurl: tokenQuery.wxheadimgurl, //微信头像
        idcardnum: tokenQuery.idcardnum || '', //身份证
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
                // alert(token);
            }else {
                alert(data.message)
            }
        },
        error: function (data) {
            alert(data.message)
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
            phonenumber: tokenQuery.phonenumber, //电话号
            idcardnum: tokenQuery.idcardnum || ''  //身份证
        }),
        success: function (data) {
            alert(JSON.stringify(data));
            if (data.code == 0) {//返回用户原有数据成功
                bindData(data.data);
            }else {
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
        var idcardnum = customerData.idcardnum || '';//身份证号
        var address = customerData.address || '';//地址
        //私户
        customerid = customerData.customerid; //customerid
        $('#customername').val(customerData.customername || ''); //用户名
        $('#identitycardnum').val(idcardnum); //身份证号
        if(idcardnum){
            $('#identitycardnum').attr('disabled',true); //身份证号输入框禁用
        }
        $('#phonenumber').val(customerData.phonenumber || ''); //客户电话
        $('#email').val(customerData.email || ''); //客户电子邮箱
        $('#J_Address').val(address.split(',')[0]); //客户详细地址
        $('#Area').val(address.split(',')[1] || ""); //客户详细地址
        $('#youbian').val(customerData.postcode || ''); //客户邮编
        //公户
        $("#customername1").val(customerData.customername || ''); //联系人姓名
        $("#identitycardnum1").val(idcardnum); //联系人身份证号
        if(idcardnum){
            $('#identitycardnum1').attr('disabled',true); //身份证号输入框禁用
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
                if (curFlag == 1) {
                    bindPersonalAccounts(listInfo[0])
                } else {
                    bindCompanyAccounts(listInfo[0])
                }
                break;
            case 2:
                listInfo.map(function (p1, p2, p3) {
                    if (p1.accounttype == 1) {
                        bindPersonalAccounts(p1)
                    } else {
                        bindCompanyAccounts(p1)
                    }
                });
                break;
            default:
        }
        function bindPersonalAccounts(data) {
            var isindex = data.isindex; //是否自有指标
            if (isindex == 1 || (!isindex)) { //1或者空 为有指标  2无指标
                // 默认都是有指标的状态
            } else {
                $("#powerOfAttorney").attr('flag', 2);
                $("#powerOfAttorney .yesBtn").removeClass('on').eq(1).addClass('on');
                $("#powerOfAttorneyTab").show();
                $('#zbname').val(data.buyername);
                $('#zbcardid').val(data.buyeridcardnum);
                $('#uploadBtn1').attr('uuid',data.attorneyuuid);
                //加载图片
                loadImage({documentType: 4, uuid: data.attorneyuuid}, $('#uploadBtn1'))
            }
        }

        function bindCompanyAccounts(data) {
            //加载营业执照
            $('#uploadBtnY').attr('uuid',data.busilicenseuuid);
            loadImage({documentType: 3, uuid: data.busilicenseuuid}, $('#uploadBtnY'));
            //加载企业信息
            $('#organizename').val(data.organizename);
            $('#organizeid').val(data.organizecode);
            var isseal = data.isseal; //是否带有公章
            if (isseal == 1 || isseal == null) { //1或者空 为带有公章 2无公章
                // 默认都是有公章的状态
            } else {
                $("#officialSeal").attr('flag', 2);
                $("#officialSeal .yesBtn").removeClass('on').eq(1).addClass('on');
                $("#officialSealImg").show();

                $('#uploadBtn2').attr('uuid',data.attorneyuuid);
                //加载公户委托书图片
                loadImage({documentType: 4, uuid: data.attorneyuuid}, $('#uploadBtn2'))
            }
        }
    }

    //个人认证
    $("#detailsUpload").on("click", function () {
        var customername = $("#customername").val();
        var idcardnum = $("#identitycardnum").val();
        var phonenumber = $("#phonenumber").val();

        if(!customername){//姓名不存在
            alert('姓名不能为空');
            return
        }
        if(!idcardnum){//身份证信息不存在
            alert('身份证号码不能为空');
            return
        }else if(!(reg.IDCard.test(idcardnum))){//身份证信息不符合格式
            alert('身份证号码字符输入不合法');
            return
        }
        if(!phonenumber){//用户电话
            alert('手机号码不能为空');
            return
        }else if(!(reg.tel.test(phonenumber))){//手机号码字符输入不合法
            alert('手机号码字符输入不合法');
            return
        }
        var selfauthData = {
            "customername": customername,
            "idcardnum": idcardnum,
            "phonenumber": phonenumber
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
                if(data.code == 0){
                    alert("实名认证成功");
                }else {
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
        var idcardnum = $("#identitycardnum1").val();
        var phonenumber = $("#phonenumber1").val();
        if(!organizename){//企业名称不存在
            alert('企业名称不能为空');
            return
        }
        if(!organizeid){//统一社会信用代码
            alert('统一社会信用代码不能为空');
            return
        }else if(!(reg.UnifiedSocialCreditIdentifier.test(organizeid))){//统一社会信用代码不符合格式
            alert('统一社会信用代码字符输入不合法');
            return
        }
        if(!customername){//姓名不存在
            alert('联系人姓名不能为空');
            return
        }
        if(!idcardnum){//身份证信息不存在
            alert('联系人身份证号码不能为空');
            return
        }else if(!(reg.IDCard.test(idcardnum))){//身份证信息不符合格式
            alert('联系人身份证号码字符输入不合法');
            return
        }
        if(!phonenumber){//用户电话
            alert('联系人手机号码不能为空');
            return
        }else if(!(reg.tel.test(phonenumber))){//手机号码字符输入不合法
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
                if(data.code == 0){
                    alert("企业认证成功")
                }else {
                    alert(data.code)
                }
            }
        })
    });
    //是否自有指标
    $("#powerOfAttorney .yesBtn").click(function () {
        var $this = $(this);
        $(this).addClass('on').siblings().removeClass('on');
        if ($this.hasClass('on') && $this.attr('no') == 'no') {
            $("#powerOfAttorney").attr('flag', 2);
            $("#powerOfAttorneyTab").show()
        } else {
            $("#powerOfAttorney").attr('flag', 1);
            $("#powerOfAttorneyTab").hide()
        }
    });
    //是否携带公章
    $("#officialSeal .yesBtn").click(function () {
        var $this = $(this);
        $(this).addClass('on').siblings().removeClass('on');
        if ($this.hasClass('on') && $this.attr('no') == 'no') {
            $("#officialSeal").attr('flag', 2);
            $("#officialSealImg").show()
        } else {
            $("#officialSeal").attr('flag', 1);
            $("#officialSealImg").hide()
        }
    });
    //私户更新
    $("#accomplish").click(function () {
        var sendUpdata = null;
        var flag = $("#powerOfAttorney").attr('flag');
        if (flag == 1) { //为1时选择的是自带指标
            sendUpdata = getAccomplish();
            sendUpdata.isindex = 1;//是否自有指标1有指标，2无指标
            sendAccomplishData(sendUpdata)
        } else { //否则为不带指标,不带指标的时候要判断委托书等信息
            var buyername = $('#zbname').val();
            var buyeridcardnum = $('#zbcardid').val();
            var attorneyuuid = $('#uploadBtn1').attr('uuid');
            if (buyername && buyeridcardnum && attorneyuuid) {
                sendUpdata = getAccomplish();
                sendUpdata.isindex = 2;//是否自有指标1有指标，2无指标
                sendUpdata.buyername = buyername;//指标人
                sendUpdata.buyeridcardnum = buyeridcardnum; //指标人身份证号
                // sendUpdata.attorneyuuid = attorneyuuid;//委托书
                sendAccomplishData(sendUpdata)
            } else {
                alert("指标信息必须完善,委托书必须上传")
            }
        }
        //获取页面信息
        function getAccomplish() {
            var upData = {
                phonenumber: $('#phonenumber').val(), //手机号
                customerid: customerid, //客户id
                email: $('#email').val(), //邮箱
                address: $('#J_Address').val() + "," + $('#Area').val(), //地址
                postcode: $('#youbian').val(), //客户邮编
                accounttype: 1 //账户类型1私户2公户
            };
            //如果要判断客户姓名和身份证以及电话信息的完善,则在此添加
            return upData
        }
    });
    //公户更新
    $("#companyAccountsUpload").click(function () {
        var sendUpdata = null;
        var flag = $("#officialSeal").attr('flag');
        if (flag == 1) { //为1时选择的是携带公章
            sendUpdata = getCompanyAccounts();
            sendAccomplishData(sendUpdata)
        } else {//不为1时 需要判断委托书是否上传
            if ($("#uploadBtn2").attr('uuid')) {
                sendUpdata = getCompanyAccounts();
                sendUpdata.isseal = 2;
                sendAccomplishData(sendUpdata)
            } else {
                alert("请上传委托书")
            }
        }
        //获取公户页面信息
        function getCompanyAccounts() {
            var upCAData = {
                phonenumber: $('#phonenumber').val(), //手机号
                customerid: customerid, //客户id
                email: $('#email1').val(), //邮箱
                address: $('#J_Address').val() + "," + $('#Area1').val(), //地址
                postcode: $('#postcode').val(), //客户邮编
                isseal: 1,//	是否带有公章
                accounttype: 2,//	账户类型1私户2公户
                organizecode: $('#organizeid').val(),//	组织机构代码
                organizename: $('#organizename').val() //	组织机构名称
            };
            return upCAData
        }
    });
    //往后台发送更新信息
    function sendAccomplishData(data) {
        if(data.email){//邮箱存在
            if(!(reg.mail.test(data.email))){
                alert('邮箱输入不合法');
                return
            }
        }
        if(data.postcode){//邮编存在
            if(!(reg.zipCode.test(data.postcode))){
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
                            if (typeof WeixinJSBridge == "undefined"){
                                if( document.addEventListener ){
                                    document.addEventListener('WeixinJSBridgeReady', closeWindow, false);
                                }else if (document.attachEvent){
                                    document.attachEvent('WeixinJSBridgeReady', closeWindow);
                                    document.attachEvent('onWeixinJSBridgeReady', closeWindow);
                                }
                            }else{
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

    //图片加载
    function loadImage(data, con) {
        $.ajax({
            type: "POST",
            url: srcConfig.getDocumentQuery,
            dataType: 'json',
            contentType: "application/json",
            data: JSON.stringify(data),
            /* beforeSend: function (request) {
             request.setRequestHeader("MSESSIONID", token);
             },*/
            success: function (data) {
                if (data.code == 0) {
                    //数据绑定
                    con.parent().append("<img src='" + data.data[0] + "'>");
                } else {
                    alert(data.message)
                }
            },
            error: function (data) {
                alert(data.message)
            }
        });
    }

    //图片上传事件方法
    function imgChagen() {
        var $this = $(this);
        var sendDate = {
            file: [],
            customerid: customerid
        };
        var idStr = $this.attr('id');
        switch (idStr) {
            case 'uploadBtn1': //私户的图片上传
                sendDate.type = 4;
                sendDate.buyeridcardnum = $("#zbcardid").val();
                if (!sendDate.buyeridcardnum) {
                    // $this.parent().find('img').remove();
                    alert("请完善指标人信息");
                    $this.val(''); //清空重置上次input的输入
                    return
                }
                break;
            case 'uploadBtnY': //公户营业执照
                sendDate.type = 3;
                sendDate.organizecode = $("#organizeid").val();
                if (!sendDate.organizecode) {
                    // $this.parent().find('img').remove();
                    alert("请完善统一社会信用代码");
                    return
                }
                break;
            case 'uploadBtn2': //公户委托书
                sendDate.type = 4;
                sendDate.organizecode = $("#organizeid").val();
                if (!sendDate.organizecode) {
                    // $this.parent().find('img').remove();
                    alert("请完善统一社会信用代码");
                    return
                }
        }
        var file = this.files[0];
        if (!/image\/\w+/.test(file.type)) {
            alert("请确保文件为图像类型");
            return false;
        }
        var reader = new FileReader();
        reader.readAsDataURL(file);
        reader.onload = function () {
            var img = new Image();
            img.src = this.result;
            (sendDate.file).push((this.result).split(',')[1]);
            ~function sendImgData(d) {
                $this.parent().append('<img style="height: 0.3rem" src="./img/onload.gif">');
                $.ajax({
                    // async:false,
                    type: "POST",
                    url: srcConfig.upload,
                    dataType: 'json',
                    contentType: "application/json",
                    beforeSend: function (request) {
                        request.setRequestHeader("MSESSIONID", token);
                    },
                    data: JSON.stringify(d),
                    success: function (data) {
                        if (data.code == 0) {
                            alert("图片上传成功");
                            $this.parent().find('img').remove();
                            $this.parent().append(img); //呈现图像(拍照結果);
                            $this.attr('uuid',1) //手动增加标识,表示已经上传图片成功
                        } else {
                            alert(data.message);
                            $this.parent().find('img').remove();
                        }
                    },
                    errror: function () {
                        $this.parent().find('img').remove();
                        alert(data.message)
                    }
                })
            }(sendDate);
        }
    }
    //图片事件监听
    $("#uploadBtnY")[0].addEventListener('change', imgChagen, false);
    $("#uploadBtn1")[0].addEventListener('change', imgChagen, false);
    $("#uploadBtn2")[0].addEventListener('change', imgChagen, false);
});