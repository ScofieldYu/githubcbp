/**
 * Created by ThinkPad User on 2017/11/21.
 */

$(function () {
    /*(function (doc, win) {
        var docEl = doc.documentElement,
            resizeEvt = 'orientationchange' in window ? 'orientationchange' : 'resize',
            recalc = function () {
                var clientWidth = docEl.clientWidth;
                if (!clientWidth) return;
                docEl.style.fontSize = 100 * (clientWidth / 375) + 'px';
            };
        if (!doc.addEventListener) return;
        win.addEventListener(resizeEvt, recalc, false);
        doc.addEventListener('DOMContentLoaded', recalc, false);
    })(document, window);*/

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

    //获取页面数据
    var url = window.location.href; //获取当前页url
    var sendData = url.queryURLParameter(); //转query
    // var sendData = {
    //     employeeid: 1,//'销售id',
    //     type: '',      //	'获取预约标识，销售特殊入口进入的传入,传入1就可以',
    //     orderid: 1     //	'订单号'
    // };
    $.ajax({
        type: "POST",
        url: srcConfig.getEmployeeTime,
        dataType: 'json',
        contentType: "application/json",
        data: JSON.stringify(sendData),
        /* beforeSend: function (request) {
         request.setRequestHeader("MSESSIONID", token);
         },*/
        success: function (data) {
            if (data.code == 0) {
                console.log(data);
                renderPage(data.data)
            } else if (data.code == 100) {//如果以及操作过,则隐藏确定按钮,并且解绑所有事件
                //确定完毕之后解绑所有事件
                $('.bookingBtn').hide();
                $('.content').unbind('longPress');
                $('.dateConTime').unbind('click')
            } else {
                console.log(data.message)
            }
        },
        error: function (data) {
            console.log(data)
        }
    });

    //根据获取的数据渲染页面
    function renderPage(data) {
        var returnCarlist = data.returnCarlist || [];//用户所有的数据
        var noVisibleList = data.noVisibleList || [];//被禁用的天
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
            var lock = '';
            //判断是否锁定某天
            for (var k = 0; k < noVisibleList.length; k++) {
                lock = noVisibleList[k] == dateStr ? 'prohibit' : ''
            }
            str += '<div class="content' + lock + '" lockDate="' + dateStr + '">';
            str += '<div class="dateTit">';
            var dateAry = dateStr.split('-');
            str += '<i></i><span  class="date">' + dateAry[1] + '月' + dateAry[2] + '日';//取出日期
            str += '</span></div>';
            str += '<div class="dateCon clearfix">';
            for (var j = 0; j < trueData[i].length; j++) {
                var cur = trueData[i][j];
                var sta = cur.status == 2 ? 'select' : (cur.status == 3 ? 'busy' : '');
                str += '<div class="dateConTime ' + sta + '" trueDate="' + dateStr + '">';
                str += '<span class="timeSlot">' + cur.starttime + '-' + cur.endtime + '</span>';
                str += '<em></em>';
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

    // bandEvent();
    // 绑定事件并发送数据
    function bandEvent() {
        //某天长按
        $('.content').longPress(function (target) {
            var $target = $(target);
            var htmlEle = '<div class="dialog_longPress">' +
                '<p class="icon"><i></i></p>' +
                '<p class="date">' + $target.find('.date').html() + '</p>' +
                '<p class="open">将不开放给客户</p></div>';
            if ($target.hasClass('prohibit')) {
                $target.removeClass('prohibit');
                // $target.find('.dateConTime ').removeClass();
                $.dialog({
                    dialogType: 'message',
                    width: '2.95rem',
                    height: '1.57rem',
                    content: htmlEle,
                    beforeOk: function () {
                        $('.icon').addClass('op');
                        $('.open').html('将开放给客户')
                    }
                })
            } else {
                $target.addClass('prohibit');
                $.dialog({
                    dialogType: 'message',
                    width: '2.95rem',
                    height: '1.57rem',
                    content: htmlEle
                })
            }
            sureBtn()
        });
        //某时段点击
        $('.dateConTime').click(function () {
            var $this = $(this);
            if (!$this.hasClass('select')) {
                $this.hasClass('busy') ? $this.removeClass('busy') : $this.addClass('busy');
                sureBtn()
            }
        });

        //确定按钮
        function sureBtn() {
            $('.bookingBtn').show();
            $('.bookingBtn a').unbind('click').click(function () {
                alert('确定')
                //获取忙碌的时段
                var busyList = $('.busy');
                var listReturmTime = [];
                for (var i = 0; i < busyList.length; i++) {
                    var $cur = $(busyList[i]);
                    var strTime = $cur.find('.timeSlot').html();
                    var curObj = {
                        retcarid: 1,	//预约id
                        employeeid: 1,  //销售id
                        starttime: strTime.split('-')[0],	//开始时间
                        endtime: strTime.split('-')[1],	    //结束时间
                        date: $cur.attr('trueDate'),        //日期
                        status: 3	//预约状态1--可预约，2--已预约，3--不可预约
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
                        customerid: 1,	//客户id
                        employeeid: 1//销售id
                    };
                    listVisibleTime.push(curObj)
                }
                // var sendData = {
                //     listReturmTime: listReturmTime,
                //     listVisibleTime: listVisibleTime,
                //     orderid: 1	//订单id,通过URL获取
                //
                // };
                $.ajax({
                    type: "POST",
                    url: srcConfig.setEmployeeTime,
                    dataType: 'json',
                    contentType: "application/json",
                    data: JSON.stringify(sendData),
                    /* beforeSend: function (request) {
                     request.setRequestHeader("MSESSIONID", token);
                     },*/
                    success: function (data) {
                        if (data.code == 0) {
                            console.log(data.data);
                            //确定完毕之后解绑所有事件
                            $('.bookingBtn').hide();
                            $('.content').unbind('longPress');
                            $('.dateConTime').unbind('click')
                        } else {
                            console.log(data.message)
                        }
                    },
                    error: function (data) {
                        console.log(data)
                    }
                })
            })
        }
    }
});