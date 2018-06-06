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
                // if (Math.abs(Y) > Math.abs(X) && Y > 0 && windowHeight <= $(window).scrollTop() + $(document).height()) { //手势为从上到下
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


    //获取URL数据
    var url = window.location.href; //获取当前页url
    // url= 'http://xxx.html?code=ODZtBeUCbyBKn1VD-YhrxE2IZ6pQbWywP2dR8bALDLY&state=';
    var queryUrl = url.queryURLParameter(); //转query
    var code = queryUrl.code; //从url中获取code

    var employeeid = null; //employeeid
    var storeid = null; //门店id
    //通过code 获取employeeid 和门店id
    $.ajax({
        async: false, //同步传输  必须先拿到获取employeeid之后才能进行下一步操作
        type: "POST",
        url: srcConfig.getEmployeeidByCode, //获取code接口
        dataType: 'json',
        contentType: "application/json",
        data: JSON.stringify({
            code: code
        }),
        success: function (data) {
            if (data.code == 0) { //可以修改和设置
                employeeid = (data.data).split(',')[0];
                storeid = (data.data).split(',')[1];
            } else {
                alert(data.message)
            }
        },
        error: function (data) {
            alert(JSON.stringify(data))
        }
    });
    // //查询门店预约时间模版
    var getShopTimeData = null;
    $.ajax({
        async: false,
        type: "POST",
        // url: './js/timetamplate.txt',
        url: srcConfig.getShopTimeTamplate,
        dataType: 'json',
        contentType: "application/json",
        data: JSON.stringify({storeid: storeid}), //门店id
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

    //获取销售顾问预约时间数据
    var getData = null;
    $.ajax({
        async: false,
        type: "POST",
        url: srcConfig.getEmployeeTime,
        dataType: 'json',
        contentType: "application/json",
        data: JSON.stringify({
            employeeid: employeeid,
            type: 1
        }),
        success: function (data) {
            if (data.code == 0) { //可以修改和设置
                getData = data.data;
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
                //值为2时表示已预约   3表示忙碌
                // data[i] == 2 && l++;
                if(data[i] == 2 || data[i] == 3){
                    l++
                }
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
            $('.d' + curDate).find('div').removeClass().addClass(state);
        }
    }

    function bindData() {
        bindDayClick(getShopTimeData);
        //选择时间段弹框
        function bindDayClick(originData) {
            var periodList = originData.periodList;
            $('.dayList li').unbind('click').click(function () {
                var $this = $(this);
                var dateStr = $this.attr('selectday').split('-').join('/');
                if (new Date(dateStr) < new Date()) {
                    alert('不能选择过去的日期')
                }else {
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
                }
            });
        }

        //确定按钮,定义绑定事件&发送数据
        function sureBtn(obj) {
            $('.checkDeliverBtn').show();
            $('.checkDeliverBtn a').unbind('click').click(function () {
                //查询用户资料,生成用户信息
                $.ajax({
                    type: "POST",
                    url: srcConfig.getCustomerByEmployeeId,
                    // url: './js/customer.txt',
                    dataType: 'json',
                    contentType: "application/json",
                    data: JSON.stringify({
                        employeeid: '' + employeeid,
                        type:'1'
                    }),
                    success: function (data) {
                        if (data.code == 0) {
                            var trueData = data.data;
                            if((!trueData) || trueData.length == 0){
                                alert('暂无可用用户信息')
                            }else {
                                dialog(data.data)
                            }
                        } else {
                            alert(data.message)
                        }
                    },
                    error: function (data) {
                        alert(data)
                    }
                });
                //弹出框
                function dialog(data) {
                    var dataList = [];
                    for (var i = 0; i < data.length; i++) {//重新绑定和生成数据
                        var curObj = {
                            value: i,
                            phonenumber: data[i].phonenumber,
                            customername: data[i].customername,
                            customerid: data[i].customerid,
                            orderlist: data[i].orderlist
                        };
                        dataList.push(curObj)
                    }
                    var strHtml = '<div class="selectCustomer"><p class="tit">推送给哪位顾客?</p><div class="seList"><span>姓　名</span><div id="customerName" class="select"></div></div><div class="seList"><span>手机号</span><div id="customerPhone" class="select"></div></div><div class="seList"><span>车架号</span><div id="vinno" class="select"></div></div></div>';
                    var customerName, customerPhone, customerid, orderid;
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
                                    var select1 = selectedItem.value; //获取被选中的列表索引
                                    customerName = dataList[select1].customername;
                                    customerPhone = dataList[select1].phonenumber;
                                    customerid = dataList[select1].customerid;
                                    $("#customerPhone").html('<label>' + dataList[select1].phonenumber + '</label>');
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
                                    } else {
                                        $("#vinno").html('<label>' + dataList[select1].orderlist[0].realvinno + '</label>');
                                        orderid = dataList[select1].orderlist[0].orderid;
                                    }
                                }
                            });
                        },
                        onOk: function () {
                            var sureBtnData = {
                                employeeid: employeeid,	//销售id
                                starttime: obj.timeSlot.split(' - ')[0],	//开始时间
                                endtime: obj.timeSlot.split(' - ')[1],	//结束时间
                                date: obj.dateTime,	//日期
                                status: 2,	//预约状态1--可预约，2--已预约，3--不可预约
                                customerid: customerid,	//客户id
                                orderid: orderid,	//订单id
                                customername: customerName,	//客户姓名
                                phonenumber: customerPhone	//客户电话
                            };
                            $.ajax({
                                type: "POST",
                                url: srcConfig.setCustomerTimeByEmployee,
                                dataType: 'json',
                                contentType: "application/json",
                                data: JSON.stringify(sureBtnData),
                                success: function (data) {
                                    if (data.code == 0) {
                                        alert("设置成功")
                                        //确定完毕之后解绑所有事件
                                        $('.dayList li').unbind('click');
                                        $('.checkDeliverBtn').hide();
                                    } else {
                                        alert(data.message)
                                    }
                                },
                                error: function (data) {
                                    alert(data)
                                }
                            });
                        }
                    })
                }
            })
        }
    }
});

