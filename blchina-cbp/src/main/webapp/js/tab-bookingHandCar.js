/**
 * Created by ThinkPad User on 2017/11/27.
 */

//定义长按事件
$.fn.longPress = function (fn) {
    var timeout = undefined;
    var $this = this;
    for (var i = 0; i < $this.length; i++) {
        $this[i].addEventListener('touchstart', function (event) {
            var that = this;
            timeout = setTimeout(function () {
                fn(that)
            }, 800);  //长按时间超过800ms，则执行传入的方法
        }, false);
        $this[i].addEventListener('touchend', function (event) {
            clearTimeout(timeout);  //长按时间少于800ms，不会执行传入的方法
        }, false);
    }
};
$(function () {
    /**
     * //时钟渲染
     * @param dateStr 日期字符串
     * @returns {string} dom元素
     */
    function setClock(dateStr) {
        var dateAry = dateStr.split(':');
        var hours = Number(dateAry[0]);
        var minutes = Number(dateAry[1]);

        var m = minutes * 6;
        //24小时转换
        if (hours > 12) {
            hours = hours - 12;
        }
        //先计算时针在整点所在的位置,再加上多出来的分钟时间
        var h = hours * 30 + 30 * (minutes / 60);
        //分针位置渲染
        var md = "rotate(" + m + "deg)";
        //时针位置渲染
        var hd = "rotate(" + h + "deg)";

        return '<div class="minute_zhen" style="transform:' + md + '"></div><div class="hour_zhen"  style="transform:' + hd + '"></div><div class="center_dot"></div>'
    }


    //获取页面数据
    var url = window.location.href; //获取当前页url
    var queryUrl = url.queryURLParameter(); //转query
    //根据code获取员工ID 和 门店ID
    var getEmployeeTimeBaseData = {
        code: queryUrl.code,//用户code
        // code: '随便给',//用户code
        type: '1'//"获取5天后预约时间，销售特殊入口进入的传入,传入1就可以
    };
    var employeeid = null;
    $.ajax({
        type: "POST",
        url: srcConfig.getEmployeeTimeBase,
        // url: './js/data.txt',
        dataType: 'json',
        contentType: "application/json",
        data: JSON.stringify(getEmployeeTimeBaseData),
        /* beforeSend: function (request) {
         request.setRequestHeader("MSESSIONID", token);
         },*/
        success: function (data) {
            if (data.code == 0) {
                renderPage(data.data)
            } else {
                alert(data.message)
            }
        },
        error: function (data) {
            alert(data)
        }
    });
    //根据获取的数据渲染页面
    function renderPage(originalData) {
        console.log("所有数据",originalData)
        var returnCarlist = originalData.returnCarlist || [];//用户所有的数据
        var noVisibleList = originalData.noVisibleList || [];//被禁用的天
        employeeid = returnCarlist[0].employeeid; //绑定员工id
        var trueData = [];
        //整理数据
        ~function () {
            var ary = [];
            for (var i = 0; i < returnCarlist.length; i++) {
                ary.push(returnCarlist[i].date)
            }
            var dateAry = ary.removeDup();//可预定的date日期
            for (var a = 0; a < dateAry.length; a++) {
                var curData = dateAry[a];
                var curList = [];
                for (var b = 0; b < returnCarlist.length; b++) {
                    if (returnCarlist[b].date == curData) {
                        curList.push(returnCarlist[b])
                    }
                }
                trueData.push(curList);
            }
        }();
        //拼接html元素
        var str = '';
        for (var i = 0; i < trueData.length; i++) {
            //暂存当天日期
            var dateStr = trueData[i][0].date;
            //判断是否锁定某天
            for (var k = 0; k < noVisibleList.length; k++) {
                var lock = noVisibleList[k] == dateStr ? 'prohibit' : '';
                if (lock == 'prohibit') {
                    break
                }
            }
            str += '<div class="content ' + lock + '" lockDate="' + dateStr + '">';
            str += '<div class="dateTit">';
            var dateAry = dateStr.split('-');
            str += '<i></i><span  class="date">' + dateAry[1] + '月' + dateAry[2] + '日';//取出日期
            str += '</span></div>';
            str += '<div class="dateCon clearfix">';
            for (var j = 0; j < trueData[i].length; j++) {
                var cur = trueData[i][j];
                var sta = cur.status == 2 ? 'select' : (cur.status == 3 ? 'busy' : '');
                str += '<div class="dateConTime ' + sta + '" retcarid="'+cur.retcarid+'" trueDate="' + dateStr + '">';
                str += '<div class="clock">' + setClock(cur.starttime) + '</div>';
                str += '<p class="amOrPm">' + ((cur.starttime.split(':')[0] <= 12) ? 'AM' : 'PM') + '</p>';
                str += '<p class="timeSlot">' + cur.starttime + '-' + cur.endtime + '</p>';
                // str += '<em class="lock on"></em>';
                str += '<em class="status"></em>';
                str += '</div>';
            }
            str += '</div>';
            str += '<div class="shadow"></div>';
            str += '</div>';
        }

        //写入DOM
        $('.bookingContent').html(str);
        //绑定事件
        bandEvent();
    }

    //绑定事件
    function bandEvent() {
        //某时段点击
        $('.dateConTime').click(function () {
            var $this = $(this);
            if (!$this.hasClass('select')) { // 未预约,则可以进行状态修改
                $this.hasClass('busy') ? $this.removeClass('busy') : $this.addClass('busy');
                sureBtn()
            }
        });
        //长按
        $('.content').longPress(function (target) {
            var $target = $(target);
            var htmlEle = '<div class="dialog_longPress">' +
                '<span class="date">' + $target.find('.date').html() + '</span>' +
                '<span class="open">将不开放给客户</span></div>';
            if ($target.hasClass('prohibit')) {
                $target.removeClass('prohibit').find('.dateConTime').click(function () {
                    var $this = $(this);
                    if (!$this.hasClass('select')) { // 未预约,则可以进行状态修改
                        $this.hasClass('busy') ? $this.removeClass('busy') : $this.addClass('busy');
                        sureBtn()
                    }
                });
                $.dialog({
                    title: '信息提示',
                    dialogType: 'message',
                    width: '3rem',
                    height: '1.71rem',
                    content: htmlEle,
                    beforeOk: function () {
                        // $('.icon').addClass('op');
                        $('.open').html('将开放给客户')
                    }
                })
            } else {
                $target.addClass('prohibit').find('.dateConTime').unbind('click');//锁定当天,移除当天下所有时间段的点击事件
                $.dialog({
                    title: '信息提示',
                    dialogType: 'message',
                    width: '3rem',
                    height: '1.71rem',
                    content: htmlEle
                })
            }
            sureBtn()
        });

        //如果有默认锁定的天,则直接移除click事件
        $('.prohibit').find('.dateConTime').unbind('click'); //锁定当天,移除当天下所有时间段的点击事件
    }

    //确定按钮
    sureBtn()
    function sureBtn() {
        $('.bookingBtn a').unbind('click').click(function () {
            $.dialog({
                title: '信息确认',
                width: '3rem',
                height: '1.88rem',
                content: '<p class="pushDialog">将交车计划推送给客户吗?</p>',
                leftBtn: '不推送',
                rightBtn: '推送',
                onOk: function () { //点击推送的时候执行的方法
                    //查询用户资料,生成用户信息
                    $.ajax({
                        type: "POST",
                        url: srcConfig.getCustomerByEmployeeId,
                        // url: './js/customer.txt',
                        dataType: 'json',
                        contentType: "application/json",
                        data: JSON.stringify({
                            employeeid:'' + employeeid,
                            type:''
                        }),
                        /* beforeSend: function (request) {
                         request.setRequestHeader("MSESSIONID", token);
                         },*/
                        success: function (data) {
                            if (data.code == 0) {
                                var trueData = data.data;
                                if((!trueData) || trueData.length == 0){
                                    alert('暂无可用用户数据')
                                }else {
                                    dialog(data.data)
                                }
                            } else {
                                alert(data.message)
                            }
                        },
                        error: function (data) {
                            alert(data.message)
                        }
                    });
                },
                onCancel: function () { //点击不推送的时候执行的方法
                    sendEmployeeTime() //不推送的时候把订单Id置空
                }
            });
            //选择用户弹出框
            function dialog(data) {
                var dataList = [];
                for (var i = 0; i < data.length; i++) {//重新绑定和生成数据
                    var curObj = {
                        value: i,
                        phonenumber: data[i].phonenumber,
                        customername: data[i].customername,
                        orderlist: data[i].orderlist
                    };
                    dataList.push(curObj)
                }
                var strHtml = '<div class="selectCustomer"><p class="tit">推送给哪位顾客?</p><div class="seList"><span>姓　名</span><div id="customerName" class="select"></div></div><div class="seList"><span>手机号</span><div id="customerPhone" class="select"></div></div><div class="seList"><span>车架号</span><div id="vinno" class="select"></div></div></div>';
                var customerName, customerPhone, orderid, customerid;
                $.dialog({
                    title: '推送信息',
                    width: '3rem',
                    height: '3.55rem',
                    content: strHtml,
                    leftBtn: '取消',
                    rightBtn: '确认',
                    beforeOk: function () {
                        //绑定下拉列表
                        $.dropdownlist({
                            container: $("#customerName"),
                            defValue: 'customername',
                            value: {value: dataList[0].value, text: dataList[0].customername},   //当前的值{value:value,text:text} 无当前选中值
                            data: dataList,
                            afterSelect: function (selectedItem) {  //下拉选中后执行的事件，参数：selectedItem选中项
                                var select1 = selectedItem.value;
                                $("#customerPhone").html('<label>'+dataList[select1].phonenumber+'</label>');
                                customerid = dataList[select1].orderlist[0].customerid;
                                //判断当前客户名下订单是否唯一,如果有一个以上则渲染下拉列表,如果没有就直接显示
                                if (dataList[select1].orderlist.length > 1) {
                                    var curOrder = dataList[select1].orderlist;
                                    var curOrderList = [];
                                    for (var i = 0; i < curOrder.length; i++) {
                                        var temp = {
                                            value: curOrder[i].orderid,
                                            text: curOrder[i].realvinno
                                        };
                                        curOrderList.push(temp)
                                    }
                                    $.dropdownlist({
                                        container: $("#vinno"),
                                        value: curOrderList[0],
                                        data: curOrderList,
                                        afterSelect: function (selectedItem) {
                                            orderid = selectedItem.value;
                                        }
                                    })
                                }else {
                                    $("#vinno").html('<label>'+dataList[select1].orderlist[0].realvinno+'</label>');
                                    orderid = dataList[select1].orderlist[0].orderid;
                                }
                            }
                        });
                    },
                    onOk: function () {
                        sendEmployeeTime(orderid,customerid)
                    }
                })
            }

            ////整理存储要发送的数据
            function sendEmployeeTime(orderid,customerid) {
                //先获取页面上所有的有用数据
                //获取忙碌的时段
                var busyList = $('.dateConTime');
                var listReturmTime = [];
                for (var i = 0; i < busyList.length; i++) {
                    var $cur = $(busyList[i]);
                    var strTime = $cur.find('.timeSlot').html();
                    var curObj = {
                        retcarid: $cur.attr('retcarid'),	//预约id
                        employeeid: employeeid,  //销售id
                        starttime: strTime.split('-')[0],	//开始时间
                        endtime: strTime.split('-')[1],	    //结束时间
                        date: $cur.attr('trueDate'),        //日期
                        status: $cur.hasClass('busy')?3:($cur.hasClass('select')?2:1)//预约状态1--可预约，2--已预约，3--忙碌
                    };
                    listReturmTime.push(curObj)
                }
                //获取被禁用的天
                var prohibitList = $('.prohibit');
                var listVisibleTime = [];
                for (var j = 0; j < prohibitList.length; j++) {
                    var $cur = $(prohibitList[j]);
                    var curObj = {
                        date: $cur.attr('lockDate'),//不可见时间
                        customerid: customerid || null,	//客户id
                        employeeid: employeeid//销售id
                    };
                    listVisibleTime.push(curObj)
                }
                var sendEmployeeTimeData = {
                    listReturmTime: listReturmTime,
                    listVisibleTime: listVisibleTime,
                    orderid: orderid || null
                };
                console.log(sendEmployeeTimeData)
                $.ajax({
                    type: "POST",
                    url: srcConfig.setEmployeeTime,
                    dataType: 'json',
                    contentType: "application/json",
                    data: JSON.stringify(sendEmployeeTimeData),
                    /* beforeSend: function (request) {
                     request.setRequestHeader("MSESSIONID", token);
                     },*/
                    success: function (data) {
                        if (data.code == 0) {
                            alert("设置成功");
                            //确定完毕之后解绑所有事件
                            $('.bookingBtn').hide();
                            $('.content').unbind('longPress');
                            $('.dateConTime').unbind('click');
                            $('.bookingContent').css('paddingBottom','0')
                        } else {
                            alert(data.message)
                        }
                    },
                    error: function (data) {
                        alert(data.message)
                    }
                })
            }
        })
    }
});