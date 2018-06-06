/**
 * Created by ThinkPad User on 2018/1/31.
 */
$(function () {
    var orderDetail = JSON.parse(window.sessionStorage.getItem('orderDetail'));
    $("#saporderid").text(orderDetail.saporderid || ''); //订单编号
    $("#logistStatus").text(orderDetail.logistStatus || '暂无物流信息'); //物流信息
    $("#customername").text(orderDetail.customername || ''); //客户姓名
    $("#idcardnum").text(orderDetail.idcardnum || ''); //客户证件号码
    $("#brandid").text(orderDetail.brandid || ''); //门店号码
    $("#employeename").text(orderDetail.employeename || ''); //销售顾问姓名
    $("#delivertime").text(orderDetail.delivertime ? '是' : '否'); //已触发具备交车按钮
    $("#contractstatus").text(orderDetail.contractstatus == 4 ? '是' : '否'); //已触发具备交车按钮
    $("#employeeSet").text(orderDetail.employeeSet != 0 ? '是' : '否'); //顾问完成微信预约交车
    $("#customerSet").text(orderDetail.customerSet != 0 ? '是' : '否'); //客户微信确认预约交车
});