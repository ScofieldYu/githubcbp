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

    var str = '09:30';
    $('.clock').html(setClock(str));


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

        return '<div class="minute_zhen" style="transform:'+md+'"></div><div class="hour_zhen"  style="transform:'+hd+'"></div><div class="center_dot"></div>'
    }


    //获取页面数据
    var url = window.location.href; //获取当前页url
    var sendData = url.queryURLParameter(); //转query
    var employeeid = sendData.employeeid;
    var orderid = sendData.orderid;
    // var employeeid = '20868';
    // var orderid = '4';
    var customerid = null;
    $.ajax({
        type: "POST",
        url: srcConfig.getEmployeeTime,
        // url: './js/data.txt',
        dataType: 'json',
        contentType: "application/json",
        data: JSON.stringify(sendData),
        /* beforeSend: function (request) {
         request.setRequestHeader("MSESSIONID", token);
         },*/
        success: function (data) {
            if (data.code == 0) {
                renderPage(data)
            }else if(data.code == 100) {
                $('.bookingBtn').hide();
                $('.bookingContent').css('marginBottom','0.1rem');
                renderPage(data)
            }else {
                alert(data.message)
            }
        },
        error: function (data) {
            alert(data)
        }
    });
    //根据获取的数据渲染页面
    function renderPage(originalData) {
        var data = originalData.data;
        var code = data.code;
        var returnCarlist = data.returnCarlist || [];//用户所有的数据
        var noVisibleList = data.noVisibleList || [];//被禁用的天
        var trueData = [];
        //整理数据
        ~function () {
            var ary = [];
            customerid = returnCarlist[0].customerid;
            // orderid = returnCarlist[0].orderid;
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
            //暂存当前日期
            var dateStr = trueData[i][0].date;
            var obsolete = '';
            // if(new Date(dateStr) > new Date()){ //大于当前日期 证明未过期
            //     obsolete = '';
            // }else {
            //     obsolete = ' obsolete';
            // }
            // console.log(new Date('2017-12-12') > new Date())
            //判断是否锁定某天
            var lock = '';
            for (var k = 0; k < noVisibleList.length; k++) {
                lock = noVisibleList[k] == dateStr ? 'prohibit' : '';
                if(lock == 'prohibit'){
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
                str += '<div class="dateConTime ' + sta + obsolete + '" retcarid="'+cur.retcarid+'" trueDate="' + dateStr + '">';
                str += '<div class="clock">' + setClock(cur.starttime) + '</div>';
                str += '<p class="amOrPm">'+((cur.starttime.split(':')[0]<=12)?'AM':'PM')+'</p>';
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

        if (code == 100) {//如果已经操作过,则隐藏确定按钮,并且解绑所有事件
            //确定完毕之后解绑所有事件
            $('.content').unbind('longPress');
            $('.dateConTime').unbind('click')
        }else {
            //绑定事件
            bandEvent();
        }
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

    sureBtn();
    //确定按钮
    function sureBtn() {
        $('.bookingBtn a').unbind('click').click(function () {
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
                    customerid: customerid,	//客户id
                    employeeid: employeeid//销售id
                };
                listVisibleTime.push(curObj)
            }
            var sendSetData = {
                listReturmTime: listReturmTime,
                listVisibleTime: listVisibleTime,
                orderid: orderid	//订单id,通过URL获取
            };
            $.dialog({
                title: '信息确认',
                width: '3rem',
                height: '1.88rem',
                content: '<p class="pushDialog">确定完成设置吗?</p>',
                leftBtn: '取消',
                rightBtn: '确定',
                onOk:function () {
                    $.ajax({
                        type: "POST",
                        url: srcConfig.setEmployeeTime,
                        dataType: 'json',
                        contentType: "application/json",
                        data: JSON.stringify(sendSetData),
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
                            alert(data)
                        }
                    })
                }
            })
        })
    }
});