/**
 * Created by ThinkPad User on 2017/11/22.
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
    //渲染日历信息
    function calendar(setDate, options) {
        if (!options) {
            options = {};
        }
        options.lastAndNext = options.lastAndNext || false;
        var date = new Date(setDate+'/1');
        var y = date.getFullYear();
        var m = date.getMonth();
        // var y = setDate.split('/');
        // var m = setDate.split('/');

        // 本月
        // 该月的第一天是星期几
        var firstDay = new Date(y, m, 1).getDay();
        // 该月一共多少天
        var lastData = new Date(y, m + 1, 0).getDate();
        // 该月的最后一天是星期几
        var str = '<div class="calendar_date">';
        str += '<div class="month">';
        str += '<span>' + y + '年' + (m + 1) + '月</span>';
        str += '</div><ul class="dayList clearfix">';
        for (var i = 0; i < firstDay; i++) {
            str += '<li></li>'
        }
        for (var i = firstDay; i < lastData + firstDay; i++) {
            var month = m + 1;
            var day = i - (firstDay) + 1;
            var curClass = y + '-' + month + '-' + day;
            str += '<li class="d' + curClass + '" selectday="' + curClass + '"><div class="general"><b>' + day + '</b><i></i></div></li>';
        }
        str += '</ul></div></div>';
        return str
    }

    var timeLine = '';//已选中时间段
    var year, month, beforeYear, beforeMonth, afterYear, afterMonth;
    year = new Date().getFullYear(); //获取年
    beforeYear = new Date().getFullYear(); //获取年
    afterYear = new Date().getFullYear(); //获取年
    month = new Date().getMonth() + 1; //获取当前月
    beforeMonth = new Date().getMonth() + 1; //获取当前月
    afterMonth = month + 1; //因为页面上有两个月,所以之后的月要多加1
    //绑定日历  先绑定默认两个月的
    $('.calendar').append(calendar(year + '/' + month, {lastAndNext: false}));
    if (afterMonth == 13) {
        afterMonth = 1;
        afterYear = afterYear + 1;
        $('.calendar').append(calendar(afterYear + '/' + afterMonth, {lastAndNext: false}));
    } else {
        $('.calendar').append(calendar(year + '/' + (month + 1), {lastAndNext: false}));
    }

    //获取URL数据
    var url = window.location.href; //获取当前页url
    alert(url)
    var sendData = url.queryURLParameter(); //转query
    var employeeid = sendData.employeeid;
    var type = 1;
    var orderid = sendData.orderid;
    // var retcarid = sendData.retcarid; //预约id
    var customerid = sendData.customerid; //客户id
    var storeId = sendData.storeId; //门店id

    var editFlag = false; //是否可修改标识,为true时,才可以进行预约时间的修改

    //获取销售顾问预约时间数据
    var getData = null;
    $.ajax({
        async: false,
        type: "POST",
        // url: './js/data.txt',
        url: srcConfig.getEmployeeTime,
        dataType: 'json',
        contentType: "application/json",
        data: JSON.stringify(sendData),
        success: function (data) {
            if (data.code == 0) { //可以修改和设置
                editFlag = true;
                getData = data.data;
                renderPage();//渲染数据
            } else if (data.code == 100) { //已经修改过,只能查看
                editFlag = false;
                getData = data.data;
                renderPage();
            } else {
                alert(data.message)
            }
        },
        error: function (data) {
            alert(data)
        }
    });

    // //查询门店预约时间模版
    var getShopTimeData = null;
    $.ajax({
        async: false,
        type: "POST",
        url: srcConfig.getShopTimeTamplate,
        // url: './js/timetamplate.txt',
        dataType: 'json',
        contentType: "application/json",
        data: JSON.stringify({storeid: storeId}), //门店id
        success: function (data) {
            if (data.code == 0) {
                getShopTimeData = data.data;
                bindData() //绑定事件
            } else {
                alert(data.message)
            }
        },
        error: function (data) {
            alert(data)
        }
    });

    //根据获取的数据渲染页面
    function renderPage() {
        var curData = getData;
        var returnCarlist = curData.returnCarlist;
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

        //当天状态
        function dateState(data) {
            // var state = '';
            var l = 0;
            for (var i = 0; i < data.length; i++) {
                //值为2时表示已预约
                data[i] == 2 && l++;
            }
            switch (l) {
                case data.length:
                    return 'busy';
                case 0:
                    return 'free';
                default:
                    return 'general'
            }
        }

        //渲染页面,绑定状态
        for (var i = 0; i < trueData.length; i++) {
            var curList = trueData[i];
            var curDate = curList[0].date;
            var stateAry = [];
            for (var j = 0; j < curList.length; j++) {
                stateAry.push(curList[j].status)
            }
            var state = dateState(stateAry);
            $('.d' + curDate).addClass('longPress').find('div').removeClass().addClass(state);
        }
        if ($('.longPress').length != 0) {
            longPress()
        }
    }

    //定义滑动事件
    ~function () {
        var windowHeight = $(window).height(); //窗口高度
        var $body = $("body");
        $body.css("height", windowHeight); //默认时候$('body').height = 0,先赋值
        var startX, startY, moveEndX, moveEndY, X, Y;
        var moveType;
        $body.on("touchstart", function (e) {
            // e.preventDefault();
            startX = e.originalEvent.changedTouches[0].pageX;
            startY = e.originalEvent.changedTouches[0].pageY;
        });
        $body.on("touchmove", function (e) {
            // e.preventDefault();
            moveEndX = e.originalEvent.changedTouches[0].pageX;
            moveEndY = e.originalEvent.changedTouches[0].pageY;
            X = moveEndX - startX;
            Y = moveEndY - startY;
            if (Math.abs(Y) > Math.abs(X) && Y > 0 && $(window).scrollTop() == 0) { //手势为从上到下
                moveType = 1;
            }
            else if (Math.abs(Y) > Math.abs(X) && Y < 0 && $(window).scrollTop() + windowHeight >= $(document).height()) { //手势为从下到上
                //窗口高度加上滚出去的高度大于文档高度时候
                moveType = 2;
            } else {
                moveType = undefined
            }
        });
        $body.on('touchend', function (e) {
            // e.preventDefault();
            if (moveType) {//moveType 不存在的话 则证明是普通滑动 不需要加载
                if (moveType == 1) { //如果moveType 说明上滑 要加载之前月
                    if (beforeMonth == 1) {
                        beforeYear = beforeYear - 1;
                        beforeMonth = 12
                    } else {
                        beforeMonth = beforeMonth - 1;
                    }
                    $('.calendar').prepend(calendar(beforeYear + '/' + beforeMonth, {lastAndNext: false}));
                } else { //否则就加载之后月
                    if (afterMonth == 13) {
                        afterYear = afterYear + 1;
                        afterMonth = 1
                    } else {
                        afterMonth = afterMonth + 1;
                    }
                    $('.calendar').append(calendar(afterYear + '/' + afterMonth, {lastAndNext: false}));
                }
                moveType = undefined;
                renderPage(); //再执行一次数据渲染,防止返回数据与当前页面上显示的月不一致而无法绑定
                //绑定事件
                bindData()
            }
        });
    }();

    function bindData() {
        if (editFlag) { //当修改标识为true时,才可以进行预约时间的修改
            bindDayClick(getShopTimeData);
            //选择时间段弹框
            function bindDayClick(originData) {
                var periodList = originData.periodList;
                $('.dayList li').unbind('click').click(function () {
                    var $this = $(this);
                    var html = '<div class="dialog_selectTime"><h2 class="selectTime">请选择预约时间段</h2><ul class="timeList">';
                    for (var i = 0; i < periodList.length; i++) {
                        html += '<li class="time"><b></b><span>' + periodList[i].starttime + ' - ' + periodList[i].endtime + '</span><i></i></li>';
                    }
                    html += '</ul></div>';
                    $.dialog({
                        title: '预约时间',
                        width: '3rem',
                        height: '3.8rem',
                        content: html,
                        dialogType: 'justOk',
                        beforeOk: function () {
                            var $time = $('.time');
                            if ($this.children().hasClass('booking')) {
                                for (var i = 0; i < $time.length; i++) {
                                    var $thimi = $($time[i]);
                                    if ($thimi.find('span').html() == timeLine) {
                                        $thimi.addClass('on')
                                    }
                                }
                            }
                            $time.click(function () {
                                $(this).addClass('on').siblings().removeClass('on');
                            });
                        },
                        onOk: function (data) {
                            timeLine = $(data).find('.on span').html();
                            var okData = {
                                timeSlot: timeLine,
                                dateTime: $this.attr('selectday')
                            };
                            if (okData.timeSlot && okData.dateTime) {//只有有选择值的时候才会有确定按钮出现
                                $('.dayList li').removeClass('booking'); //移除之前已选择过的天
                                $this.addClass('booking'); //给当前天加上选中状态
                                sureBtn(okData)
                            }
                        }
                    });
                });
            }
        }

        //确定按钮,定义绑定事件&发送数据
        function sureBtn(obj) {
            $('.checkDeliverBtn').show();
            $('.checkDeliverBtn a').unbind('click').click(function () {
                $.dialog({
                    width: '3rem',
                    height: '1.88rem',
                    title: '修改确认',
                    content: '<div class="dialog_sure"><p>预约时间仅限修改1次</p></div>',
                    onOk: function (data) {
                        var sureBtnData = {
                            retcarid: retcarid,	//预约id
                            employeeid: employeeid,	//销售id
                            starttime: obj.timeSlot.split(' - ')[0],	//开始时间
                            endtime: obj.timeSlot.split(' - ')[1],	//结束时间
                            date: obj.dateTime,	//日期
                            status: 2,	//预约状态1--可预约，2--已预约，3--不可预约
                            customerid: customerid,	//客户id
                            orderid: orderid	//订单id
                        };
                        $.ajax({
                            type: "POST",
                            url: srcConfig.setCustomerTime,
                            dataType: 'json',
                            contentType: "application/json",
                            data: JSON.stringify(sureBtnData),
                            success: function (data) {
                                if (data.code == 0) {
                                    console.log(data.data);
                                    //确定完毕之后解绑所有事件
                                    $('.checkDeliverBtn').remove();
                                    $('.dayList li').unbind('click')
                                } else {
                                    alert(data.message)
                                }
                            },
                            error: function (data) {
                                alert(data)
                            }
                        });
                    },
                    onCancel: function (data) {
                        alert('点了取消')
                    }
                });
            })
        }
    }

    //长按事件
    function longPress() {
        $('.longPress').longPress(function (target) {
            var selectday = $(target).attr('selectday');
            //查看某天销售顾问预约记录
            $.ajax({
                type: "POST",
                url: srcConfig.getDayReturnTime,
                // url: './js/getDayReturnTime.txt',
                dataType: 'json',
                contentType: "application/json",
                data: JSON.stringify({
                    employeeid: employeeid, //销售id
                    date: selectday //日期
                }),
                success: function (data) {
                    if (data.code == 0) {
                        dialogDayReturnTime(data.data)
                    } else {
                        alert(data.message)
                    }
                },
                error: function (data) {
                    alert(data)
                }
            });
            function dialogDayReturnTime(drtData) {
                var m = selectday.split('-')[1];
                var d = selectday.split('-')[2];
                var curHtml = '<div class="dialog_appointment"><div class="head">' + m + '月' + d + '日预约时间表</div><div class="bdcon">';
                for (var i = 0; i < drtData.length; i++) {
                    var on = drtData[i].orderid == orderid ? 'on' : ''; //这句话是为了显示当客户更改提交预约时间后所查看到的状态,在弹出框信息中用蓝色边框表示,(暂用orderid)
                    curHtml += '<div class="pCon ' + on + '">' +
                        '<div class="timeSection">' + drtData[i].starttime + '-' + drtData[i].endtime + '</div>' +
                        '<ul class="ulData">' +
                        '<li class="phone">电话: ' + drtData[i].phonenumber + '</li>' +
                        '<li>联系人: ' + drtData[i].customername + '</li>' +
                        '<li class="cartype">车　型: ' + drtData[i].cartype + '</li>' +
                        '<li>车架号: ' + drtData[i].vinno + '</li></ul></div>'
                }
                curHtml += '</div>';
                $.dialog({
                    dialogType: 'message1',
                    content: curHtml,
                    width: '3rem', //弹出框的宽度,以rem为单位的字符串值
                    height: '5rem', //弹出框的高度,以rem为单位的字符串值
                    title: '预约时间', //弹出框标题,必传
                    beforeOk: function () {

                    },
                    onOk: function () {

                    }
                })
            }
        });


        longPress = function () {
        } //函数重置,保证长按事件只执行一次

    }

});

