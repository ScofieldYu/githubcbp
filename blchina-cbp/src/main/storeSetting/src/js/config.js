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
        var reg = /^(\d{4})(?:-|\/|\.|:)(\d{1,2})(?:-|\/|\.|:)(\d{1,2})(?: +)?(\d{1,2})?(?:-|\/|\.|:)?(\d{1,2})?(?:-|\/|\.|:)?(\d{1,2})?$/g, ary = [];
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
            obj[arguments[1]] = arguments[2];
        });
        return obj;
    };
}(String.prototype);
//数组公用方法
~function (pro) {
    pro.removeDup = function(){//去重
        var result = [];
        var obj = {};
        for(var i = 0; i < this.length; i++){
            if(!obj[this[i]]){
                result.push(this[i]);
                obj[this[i]] = 1;
            }
        }
        return result;
    }
}(Array.prototype);

//配置接口URL
// var local = 'http://bdl-cbp.yxonline.top/cbp/';
var local = 'http://192.168.206.27:8080/';
window.srcConfig = {
    selfauth: local + 'auth/selfauth',  //个人认证接口
    updateCustmer: local + 'auth/updateCustmer',  //更新用户
    getToken: local + 'auth/getToken',  //获取token
    companyauth: local + 'auth/companyauth',  //公司认证
    upgradeCBP: local + 'apk/upgradeCBP',   //APK更新
    userLogin: local + 'user/userLogin',  //ipad用户登录
    logout: local + 'user/logout',  //用户登出
    getEmployeeTime: local + 'time/getEmployeeTime',  //获取员工和客户预约时间
    getShopTimeTamplate: local + 'time/getShopTimeTamplate',  //获取门店预约时间模板
    setShopTimeTamplate: local + 'time/setShopTimeTamplate',  //设置门店预约时间模板
    getCustomerTime: local + 'time/getCustomerTime',  //获取客户预约时间
    setEmployeeTime: local + 'time/setEmployeeTime',  //销售顾问设置预约时间
    getOrderList: local + 'order/getOrderList',  //获取订单列表
    getOrderById: local + 'order/getOrderById',  //根据订单id获取订单信息
    getOrderByEmployeeAndContract: local + 'order/getOrderByEmployeeAndContract',  //根据员工id和合同状态获取订单
    getOrderAndContractById: local + 'order/getOrderAndContractById',  //根据员工id获取订单和合同信息
    setCustomerTime: local + 'time/setCustomerTime',  //设置客户预约时间
    getCustomerInfo: local + 'auth/getCustomerInfo'  //获取个人信息接口
};
