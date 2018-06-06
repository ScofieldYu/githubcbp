/**
 * Created by ThinkPad User on 2017/11/22.
 */
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
            if (month < 10) {
                month = '0' + month
            }
            var day = i - (firstDay) + 1;
            if (day < 10) {
                day = '0' + day
            }
            var curClass = y + '-' + month + '-' + day;
            str += '<li class="show d' + curClass + '" selectday="' + curClass + '"><div class="general"><b>' + day + '</b><i></i></div></li>';
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
    var sendData = url.queryURLParameter(); //转query
    var employeeid = sendData.employeeid; //销售id
    var orderid = sendData.orderid; //当前订单id
    sendData.type = 1;
    var storeId = sendData.storeid; //门店id

    var bookingDate = null;
    var onceFlag = true;

    //获取销售顾问预约时间数据
    var getData = null;
    $.ajax({
        type: "POST",
        // url: './js/data.txt',
        url: srcConfig.getEmployeeTime,
        dataType: 'json',
        contentType: "application/json",
        data: JSON.stringify(sendData),
        success: function (data) {
            if (data.code == 0) {
                getData = data.data; //赋值暂存
                renderPage();//渲染数据
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
        var returnCarlist = getData.returnCarlist;
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
                if(curList[j].orderid == orderid){  //判断当前订单预约的是哪天booking
                    bookingDate = curDate;
                    $('.d' + curDate).addClass('booking');
                }
                stateAry.push(curList[j].status);
            }
            var state = dateState(stateAry);
            $('.d' + curDate).find('div').removeClass().addClass(state);
        }

        if(onceFlag){
            onceFlag = false;
            showData(bookingDate);
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
                bindDayClick()
            }
        });
    }();

    //选择时间段弹框
    function bindDayClick() {
        $('.dayList li').unbind('click').click(showData);
    }

    function showData(bookingDate) {
        var $this = $(this);
        var selectday = "";
        if(typeof bookingDate == "string"){
            selectday = bookingDate;
        }else {
            selectday = $this.attr('selectday')
        }
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
            var curHtml = '<div class="dialog_appointment"><div class="dialogCloseBtn"></div><div class="head">' + m + '月' + d + '日预约时间表</div><div class="bdcon">';
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
    }
});

