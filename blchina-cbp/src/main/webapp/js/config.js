/**
 * Created by ThinkPad User on 2017/11/21.
 */

//->动态计算REM的换算比例
~function (desW) {
    var winW = document.documentElement.clientWidth;
    document.documentElement.style.fontSize = winW / desW * 100 + "px";
}(375);
//字符串公共方法
~function (pro) {
    //myTrim:Remove the string and space
    pro.myTrim = function myTrim() {
        return this.replace(/(^ +| +$)/g, "");
    };

    //mySub:Intercept string, this method is distinguished in English
    pro.mySub = function mySub() {
        var len = arguments[0] || 10, isD = arguments[1] || false, str = "", n = 0;
        for (var i = 0; i < this.length; i++) {
            var s = this.charAt(i);
            /[\u4e00-\u9fa5]/.test(s) ? n += 2 : n++;
            if (n > len) {
                isD ? str += "..." : void 0;
                break;
            }
            str += s;
        }
        return str;
    };

    //myFormatTime:Format time
    pro.myFormatTime = function myFormatTime() {
        var reg = /^(\d{4})(?:-|\/|\.|:)(\d{1,2})(?:-|\/|\.|:)(\d{1,2})(?: +)?(\d{1,2})?(?:-|\/|\.|:)?(\d{1,2})?(?:-|\/|\.|:)?(\d{1,2})?$/g,
            ary = [];
        this.replace(reg, function () {
            ary = ([].slice.call(arguments)).slice(1, 7);
        });
        var format = arguments[0] || "{0}年{1}月{2}日{3}:{4}:{5}";
        return format.replace(/{(\d+)}/g, function () {
            var val = ary[arguments[1]];
            return val.length === 1 ? "0" + val : val;
        });
    };
    //queryURLParameter:Gets the parameters in the URL address bar //截取URL
    pro.queryURLParameter = function queryURLParameter() {
        var reg = /([^?&=]+)=([^?&=]+)/g, obj = {};
        this.replace(reg, function () {
            obj[arguments[1]] = decodeURI(arguments[2]);
        });
        return obj;
    };
}(String.prototype);
//数组公用方法
~function (pro) {
    pro.removeDup = function () {//去重
        var result = [];
        var obj = {};
        for (var i = 0; i < this.length; i++) {
            if (!obj[this[i]]) {
                result.push(this[i]);
                obj[this[i]] = 1;
            }
        }
        return result;
    }
}(Array.prototype);

//字符串 转 base64
function Base64() {

    // private property
    _keyStr = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=";

    // public method for encoding
    this.encode = function (input) {
        var output = "";
        var chr1, chr2, chr3, enc1, enc2, enc3, enc4;
        var i = 0;
        input = _utf8_encode(input);
        while (i < input.length) {
            chr1 = input.charCodeAt(i++);
            chr2 = input.charCodeAt(i++);
            chr3 = input.charCodeAt(i++);
            enc1 = chr1 >> 2;
            enc2 = ((chr1 & 3) << 4) | (chr2 >> 4);
            enc3 = ((chr2 & 15) << 2) | (chr3 >> 6);
            enc4 = chr3 & 63;
            if (isNaN(chr2)) {
                enc3 = enc4 = 64;
            } else if (isNaN(chr3)) {
                enc4 = 64;
            }
            output = output +
                _keyStr.charAt(enc1) + _keyStr.charAt(enc2) +
                _keyStr.charAt(enc3) + _keyStr.charAt(enc4);
        }
        return output;
    };

    // public method for decoding
    this.decode = function (input) {
        var output = "";
        var chr1, chr2, chr3;
        var enc1, enc2, enc3, enc4;
        var i = 0;
        input = input.replace(/[^A-Za-z0-9\+\/\=]/g, "");
        while (i < input.length) {
            enc1 = _keyStr.indexOf(input.charAt(i++));
            enc2 = _keyStr.indexOf(input.charAt(i++));
            enc3 = _keyStr.indexOf(input.charAt(i++));
            enc4 = _keyStr.indexOf(input.charAt(i++));
            chr1 = (enc1 << 2) | (enc2 >> 4);
            chr2 = ((enc2 & 15) << 4) | (enc3 >> 2);
            chr3 = ((enc3 & 3) << 6) | enc4;
            output = output + String.fromCharCode(chr1);
            if (enc3 != 64) {
                output = output + String.fromCharCode(chr2);
            }
            if (enc4 != 64) {
                output = output + String.fromCharCode(chr3);
            }
        }
        output = _utf8_decode(output);
        return output;
    };

    // private method for UTF-8 encoding
    _utf8_encode = function (string) {
        string = string.replace(/\r\n/g, "\n");
        var utftext = "";
        for (var n = 0; n < string.length; n++) {
            var c = string.charCodeAt(n);
            if (c < 128) {
                utftext += String.fromCharCode(c);
            } else if ((c > 127) && (c < 2048)) {
                utftext += String.fromCharCode((c >> 6) | 192);
                utftext += String.fromCharCode((c & 63) | 128);
            } else {
                utftext += String.fromCharCode((c >> 12) | 224);
                utftext += String.fromCharCode(((c >> 6) & 63) | 128);
                utftext += String.fromCharCode((c & 63) | 128);
            }

        }
        return utftext;
    };

    // private method for UTF-8 decoding
    _utf8_decode = function (utftext) {
        var string = "";
        var i = 0;
        var c = c1 = c2 = 0;
        while (i < utftext.length) {
            c = utftext.charCodeAt(i);
            if (c < 128) {
                string += String.fromCharCode(c);
                i++;
            } else if ((c > 191) && (c < 224)) {
                c2 = utftext.charCodeAt(i + 1);
                string += String.fromCharCode(((c & 31) << 6) | (c2 & 63));
                i += 2;
            } else {
                c2 = utftext.charCodeAt(i + 1);
                c3 = utftext.charCodeAt(i + 2);
                string += String.fromCharCode(((c & 15) << 12) | ((c2 & 63) << 6) | (c3 & 63));
                i += 3;
            }
        }
        return string;
    }
}

 var local = 'http://bdl-cbp.blchina.com/cbp/';
//var local = 'http://127.0.0.1:8080/cbp/';
// var local = 'http://192.168.206.188:8889/blchina-cbp/';
// var local = 'http://test.xbotech.com/cbp-qas/';
//var local = 'http://test.xbotech.com/cbp-qas/';
window.srcConfig = {
    setOpenId: local + 'openId/setOpenId', //登录接口
    selfauth: local + 'auth/selfauth',  //个人认证接口
    updateCustmer: local + 'auth/updateCustmer',  //更新用户
    getToken: local + 'auth/getToken',  //获取token
    companyauth: local + 'auth/companyauth',  //公司认证
    upgradeCBP: local + 'apk/upgradeCBP',   //APK更新
    userLogin: local + 'user/userLogin',  //ipad用户登录
    logout: local + 'user/logout',  //用户登出
    getEmployeeTime: local + 'time/getEmployeeTime',  //获取员s工和客户预约时间
    getShopTimeTamplate: local + 'time/getShopTimeTamplate',  //获取门店预约时间模板
    setShopTimeTamplate: local + 'time/setShopTimeTamplate',  //设置门店预约时间模板
    getCustomerTime: local + 'time/getCustomerTime',  //获取客户预约时间
    setEmployeeTime: local + 'time/setEmployeeTime',  //销售顾问设置预约时间
    getOrderList: local + 'order/getOrderList',  //获取订单列表
    getOrderById: local + 'order/getOrderById',  //根据订单id获取订单信息
    getOrderByEmployeeAndContract: local + 'order/getOrderByEmployeeAndContract',  //根据员工id和合同状态获取订单
    getOrderAndContractById: local + 'order/getOrderAndContractById',  //根据员工id获取订单和合同信息
    getOrderListCustomer: local + 'order/getOrderListCustomer',  //客户端,根据openID和门店ID获取订单列表
    setCustomerTime: local + 'time/setCustomerTime',  //设置客户预约时间
    getCustomerInfo: local + 'auth/getCustomerInfo',  //获取个人信息接口
    pushCarStart: local + 'wx/pushCarStart',  //给销售顾问预约轿车通知
    noticeSet: local + 'wx/noticeSet',  //通知销售顾问设置五天后时间
    getDayReturnTime: local + 'time/getDayReturnTime',  //查看某天销售顾问预约记录
    getEmployeeTimeBase: local + 'time/getEmployeeTimeBase',  //查看销售顾问预约时间(底下tab入口)
    setEmployeeTimeBase: local + 'time/setEmployeeTimeBase',  //设置销售顾问预约时间（底下tab入口）
    setCustomerTimeBase: local + 'time/setCustomerTimeBase',  //销售顾问设置客户时间（底下tab入口）
    getCustomerByEmployeeId: local + 'auth/getCustomerByEmployeeId',  //根据销售顾问id获取匹配用户信息
    // getSignCustomerId: local + 'auth/getSignCustomerId',  //根据客户id获取客户signcustomerid
    upload: local + 'upload',  //文档归档上传
    getDocumentQuery: local + 'documentQuery/getDocumentQuery', //文档查询
    getEmployeeidByCode: local + 'wx/getEmployeeidByCode',  //根据code获取销售顾问id和门店号
    setCustomerTimeByEmployee: local + 'time/setCustomerTimeByEmployee',  //销售顾问设置某客户五天后时间
    findbyorderid: local + 'logistics/findbyorderid',//查看物流
    getPayOrder: local + 'payOrder/getPayOrder',//微信支付接口
    getPayOrderParam: local + 'wXPay/getPayOrderParam',//微信支付参数接口(二期)
    getPayOrder2: local + 'wXPay/getPayOrder',//微信支付接口(二期)
    showMonitorDetail: local + 'monitor/showMonitorDetail',//监控显示
    getEmployeeidByCodeBase: local + 'monitor/getEmployeeidByCodeBase',//监控根据code获取员工id 和token 账号类型
    showOrderSignMonitor: local + 'monitor/showOrderSignMonitor',//根据搜索条件返回结果
    getPaperCosts: local + 'monitor/getPaperCosts',//根据搜索条件返回纸张统计结果
    getBrandStoreByEmployeeId: local + 'brandstore/store',//监控根据员工id 获取门店和品牌信息
    searchEmployeeByBrandId: local + 'constract/searchEmployeeByBrandId',//获取员工列表
    searchEmployee: local + 'constract/searchEmployee',//获取总监以下员工列表
    getCustomerByPhone: local + 'auth/getCustomerByPhone',//根据手机号获取用户信息
    queryCardByEmployee: local + 'card/queryCardByEmployee',//卡片查询
    queryOrderByCustomerid: local + "card/queryOrderByCustomerid",//根据客户id获取订单
    selectCardDetails: local + 'card/selectCardDetails',//任务详情
    updateCardTime: local + 'card/updateCardTime',//销售顾问更新卡片预计完成时间
    searchCardList: local + 'card/searchCardList',//卡片搜索
    getOrderByOrderId: local + "order/getOrderByOrderId",//根据客户id获取客户订单详情
    searchOrderList: local + "order/searchOrderList",//订单搜索
    chooseCar: local + "order/chooseCar",//选车接口
    saveCard: local + 'card/saveCard',//创建卡片,
    insertOrUpdateFourCustomer: local + 'fourCustomer/insertOrUpdateFourCustomer',//维护四方信息,
    getFourCustomerList: local + 'fourCustomer/getFourCustomerList',//查询四方信息(销售顾问),
    searchFourCustomer: local + 'fourCustomer/searchFourCustomer',//搜索四方信息(销售顾问),
    getInsuranceInfo: local + 'insurance/getInsuranceInfo', //查询保险种类
    getInsuranceInfoByOrderId: local + 'insurance/getInsuranceInfoByOrderId', //根据订单查询客户保险
    saveOrUpdateInsuranceInfo: local + 'insurance/saveOrUpdateInsuranceInfo', //保存保险数据接口
    saveSecondHandCarOrder: local + 'secondHandCar/saveSecondHandCarOrder', //二手车置换信息添加
    getSecondHandCarOrder: local + 'secondHandCar/getSecondHandCarOrder', //二手车置换信息获取

    signTemp:local+'contractSign/signTemp',
    getOrderListEmployee:local+'order/getOrderListEmployee',//获取订单列表	
    getOrderByOrderIdEmployee:local+'order/getOrderByOrderIdEmployee',//获取订单详情(销售顾问)


    insertCheckCarNumOrder:local+"checkCarNum/insertCheckCarNumOrder", //添加选中代办信息
    getInfoBySelected:local+"checkCarNum/getInfoBySelected",//回显选中资料列表
    getAllInfo:local+"checkCarNum/getAllInfo", //查询资料列表


    
    collectionInfo:local + 'checkCarNum/collectionInfo',//收集资料
    getAllPeriodList:local+"finance/getAllPeriodList", //金融分期查询所有银行
    insertfinanceOrder:local+"finance/insertfinanceOrder",//金融分期完成
    updateCardStatusDoing:local+"card/updateCardStatusDoing", //更新卡片状态为进行
    updateCardTaskList:local+"card/updateCardTaskList",//卡片子任务完成
    insertBoutiqueOrder:local+'boutique/insertBoutiqueOrder', //添加精品信息订单
    selectFinanceOrderByOrderId:local+"finance/selectFinanceOrderByOrderId", //获取金融分期

    batchForCheckCarPerson:local+"card/batchForCheckCarPerson", //验车专员批量完成
    getFileExt:local+"getFileExt",//查询文件扩展
    getDocumentQueryBase:local+"documentQuery/getDocumentQueryBase",//文档查询二期
    getBoutiqueListByBoutiqueId:local+"boutique/getBoutiqueListByBoutiqueId",//根据精品名称查询精品信息
    getCheckCarInfoByOrderid:local+"checkCarNum/getCheckCarInfoByOrderid" //回显验车上牌
    
};
