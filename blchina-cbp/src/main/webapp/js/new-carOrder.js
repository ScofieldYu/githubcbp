/**
 * Created by ThinkPad User on 2017/12/15.
 */

//获取token
var url = window.location.href; //获取当前页url
// url = 'http://bdl-cbp.blchina.com/cbp/new-carOrder.html?phonenumber=18101277323&customername=%E6%9D%8E%E4%BC%9F&idcardnum=110108196905152252&email=NaN&address=%E5%AE%89%E6%85%A7%E5%8C%97%E9%87%8C%E9%80%B8%E5%9B%AD%E5%B0%8F%E5%8C%BA18%E5%8F%B7%E6%A5%BC1102%E5%8F%B7&postcode=100000&isindex=&isSeal=&brandId=2002&openId=oHsB0t_egxKRlGZC6aLwptLYdbfA';
var tokenQuery = url.queryURLParameter(); //转query
/*var getToken = { // tokenQuery 的样子
 phonenumber : '',//客户手机号
 customername :'',//客户姓名
 idcardnum:'',//身份证
 email:'',//
 address:'',//
 postcode:'',//
 isindex:'',//
 isSeal:''
 };*/
var base = new Base64();
var secret = base.encode(tokenQuery.phonenumber + '!!' + new Date().getTime());

var getToken = { // tokenQuery 的样子
    secret: secret, //手机号+“!!”+时间戳base64加密
    nickname: tokenQuery.customername, //昵称
    idcardnum: tokenQuery.idcardnum || '', //身份证
    openid: tokenQuery.openId, //openId 微信唯一识别id
    brandid: tokenQuery.brandId //brandId //门店id
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
        }
    },
    error: function (data) {
        alert(data.message)
    }
});


//获取订单信息页面数据
var openid = tokenQuery.openId;
var brandid = tokenQuery.brandId;

var orderList = [];
$.ajax({
    type: "POST",
    url: srcConfig.getOrderList,
    // url: './js/orderList.txt',
    dataType: 'json',
    contentType: "application/json",
    data: JSON.stringify({
        openid: openid,
        brandid: brandid
    }),
    beforeSend: function (request) {
        request.setRequestHeader("MSESSIONID", token);
    },
    success: function (data) {
        if (data.code == 0) {
            orderList = data.data;
            if (orderList.length == 0) { //没有订单的时候增加默认数据
                $("#orderList").html('<div class="titleBg"></div><div class="orderCon"><div class="orderTit"><div class="saporder" style="width: 100%">暂无订单</div></div><div class="orderDetail" style="height: 6.1rem"><p style="padding-top: 2.5rem;width: 100%;height: 100%;text-align: center;font-size: 0.4rem;font-weight: 900;color: #dedede;">您目前没有订单</p></div></div>')
            }
            if (orderList.length == 1) { //仅仅有一条订单信息(仅有一条真实数据或默认数据)的时候
                bindData('#orderList', orderList[0]);  //绑定数据
                $('.moreList').hide()  //隐藏更多订单按钮
            } else {  //大于一条的时候再获取模版再渲染
                bindData('#orderList', orderList[0]);  //绑定数据
                var orderTemplate = '';
                //获取订单模版
                $.ajax({
                    async: false,
                    url: './orderTemplate.html',
                    type: "GET",
                    success: function (template) {
                        orderTemplate = template;
                    }
                });
                for (var i = 1; i < orderList.length; i++) {
                    var str = '<div id="orderList' + orderList[i].orderid + '" class="orderList">';
                    str += orderTemplate;
                    str += '</div>';
                    var $order = $(str);
                    $('body').append($order);
                    bindData('#orderList' + orderList[i].orderid, orderList[i])
                }
            }
        } else {
            alert(data.message)
        }
    },
    error: function (data) {
        alert(data.message)
    }
});

/**
 * 绑定订单信息数据 绑定垫付证明事件
 * @param content 容器
 * @param data 数据
 */
function bindData(content, data) {
    var $content = $(content);
    //详情
    $content.find('.saporderid').text(data.saporderid || ''); //订单号
    $content.find('.requestTime').text((data.requestTime || '')); //签约时间
    if (data.organizecode) { //organizecode存在表示是公司购买,所以买方姓名要显示公司名
        $content.find('.buyername').text(data.organizename || ''); //买方姓名
        $content.find('.buyeridcardnum').text(data.organizecode || ''); //买方姓名
    } else {
        $content.find('.buyername').text(data.buyername || ''); //买方姓名
        $content.find('.buyeridcardnum').text(data.buyeridcardnum || ''); //买方证件号码
    }
    $content.find('.customername').text(data.customername || ''); //联系人
    $content.find('.idcardnum').text(data.idcardnum || ''); //联系人证件号码
    $content.find('.cartype').text(data.cartype || ''); //车型
    $content.find('.vinno').text(data.vinno || ''); //车架号

    //查看合同 viewTheContract
    $content.find('.viewTheContract a').click(function () {
        if (data.orderid) {
            var getFile = {
                orderId: data.orderid, //订单ID
                documentType: 6 //文档类型
            };
            //查看合同
            $.ajax({
                type: "POST",
                url: srcConfig.getDocumentQuery,
                dataType: 'json',
                contentType: "application/json",
                data: JSON.stringify(getFile),
                /* beforeSend: function (request) {
                 request.setRequestHeader("MSESSIONID", token);
                 },*/
                success: function (data) {
                    console.log(data);
                    if (data.code == 0) {
                        console.log(JSON.stringify(data.data[0]));
                        var ifareStr = '<iframe id=aaa frameborder=0 width=100% height=100% marginheight=0 marginwidth=0 scrolling=no src="' + data.data + '"></iframe>';
                        $('.contract').show().append(ifareStr)
                    } else {
                        console.log(data.message)
                    }
                },
                error: function (data) {
                    console.log(data)
                }
            });
        } else {
            alert("暂无订单")
        }
    });

    //物流
    $content.find('.toShop').text(data.logistStatus? '您的' + data.logistStatus : '暂无物流信息'); //物流信息
    //查看全部物流信息
    $content.find('.wholeBtn').attr('href', data.orderid ? ('./logisticsInformation.html?orderid=' + data.orderid + '&employeeid=' + data.employeeid + '&saporderid=' + data.saporderid) : 'javascript:void 0');
    if (data.status == 2) {
        if (data.apptime) {
            $content.find('.changeTimeBtn').show();
            $content.find('.changeTimeBtn a').attr('href', './new-clientAppointmentTime.html?orderid=' + data.orderid + '&employeeid=' + data.employeeid).html('预约提车时间为  ' + data.apptime + '<i> > </i>'); //修改和查看预约提车时间
        } else {
            $content.find('.extractCarBtn').show();
            $content.find('.extractCarBtn a').attr('href', './new-clientAppointmentTime.html?orderid=' + data.orderid + '&employeeid=' + data.employeeid).show(); //预约提车按钮显示
        }
    } else {
        $content.find('.changeTimeBtn').hide(); //修改提车按钮隐藏
        $content.find('.extractCarBtn').hide(); //预约提车按钮隐藏
    }

    //支付
    $content.find('.totalprice').text('￥' + data.totalprice); //总额
    if (data.deposittotal) {
        $content.find('.deposittotal').text('￥' + data.deposittotal).parent().show(); //定金合计
    } else {
        $content.find('.deposittotal').parent().hide()
    }
    if ((data.depositSum) && data.depositSum <= 10000) {
        //微信支付按钮
        if(data.depositStatus == '支付'){  //1 表示已支付  0表示未支付
            $content.find('.weChat').hide(); //微信支付按钮隐藏
        }else {
            $content.find('.weChat').hide(); //微信支付按钮隐藏
            $content.find('.weChat').show().click(function () { //微信支付按钮显示 且绑定支付事件
                var $thisBtn = $(this);
                $.ajax({
                    type: "POST",
                    url: srcConfig.getPayOrder,
                    dataType: 'json',
                    data: JSON.stringify({orderId: data.orderid}),
                    contentType: "application/json",
                    success: function (data) {
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
                        } else{
                            alert(data.data)
                        }
                    },
                    error: function (data) {
                        alert("服务器链接失败或超时")

                    }
                });
            });
        }
        $content.find('.depositSumTit').show(); //在线支付定金 显示
        $content.find('.depositSum').text('￥' + data.depositSum); // 显示在线支付定金的值
    } else {
        $content.find('.weChat').hide(); //微信支付按钮隐藏
        $content.find('.depositSumTit').hide(); //在线支付定金 隐藏
    }
    $content.find('.employeePhone').text(data.employeePhone).attr('href', 'tel:' + data.employeePhone); //销售顾问电话

    //垫付证明
    $content.find('.prove a').attr('href', data.orderid ? './advanceProve.html?orderid=' + data.orderid + '&employeeid=' + data.employeeid + '&saporderid=' + data.saporderid + '&employeePhone=' + data.employeePhone : 'javascript:void 0')
}

//更多订单按钮
$('.mBtn').click(function () {
    var $this = $(this);
    if ($this.hasClass('on')) {
        $this.removeClass('on').parent().addClass('m30');
        $('.orderList:not(#orderList)').hide()
    } else {
        $this.addClass('on').parent().removeClass('m30');
        $('.orderList:not(#orderList)').show()
    }
});

//查看合同后点击图片回到原始页面
$('.ifreamClose').click(function () {
    $('.contract').hide().find('iframe').remove()
});

//弹出框
/*    var confirmHtml = '<div class="paymentConfirm"><span>汇款账户</span><span>10011 89719 00685 0520</span><span>汇款金额</span><span>￥420,000</span></div>';
 $('.remittance').click(function () {
 $.dialog({
 content: confirmHtml,
 width: '3rem',
 height: '2.63rem',
 title:'支付确认',
 beforeOk: null,
 onOk: null,
 onCancel: null
 })
 });*/