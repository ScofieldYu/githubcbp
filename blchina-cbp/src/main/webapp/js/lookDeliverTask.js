


$(function () {
    //->动态计算REM的换算比例
    (function (doc, win) {
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
    })(document, window);

    //获取并渲染日历信息
    $.fn.calendar = function (setDate, options) {
        if (!options) {
            options = {};
        }
        options.lastAndNext = options.lastAndNext || false;
        var date = new Date(setDate);
        var y = date.getFullYear();
        var m = date.getMonth();
        // 本月
        // 该月的第一天是星期几
        var firstDay = new Date(y, m, 1).getDay();
        // 该月一共多少天
        var lastData = new Date(y, m + 1, 0).getDate();
        // 该月的最后一天是星期几
        var $this = $(this);
        $this.find('.month span').html(m + 1 + '月');
        var str = '';
        for (var i = 0; i < firstDay; i++) {
            str += '<li></li>'
        }
        for (var i = firstDay; i < lastData + firstDay; i++) {
            var month = m + 1;
            var day = i - (firstDay) + 1;
            var curClass = y + '-' + month + '-' + day;
            str += '<li class="d' + curClass + '" selectDay="' + curClass + '"><div class="general"><b>' + day + '</b><i></i></div></li>';
        }
        $this.find('.dayList').html(str)
    };

    //绑定日历
    $('#test').calendar(new Date('2017-11-22'), { lastAndNext: false });
    $('#test1').calendar('2017-12', { lastAndNext: false });

    //获取数据
    var url = window.location.href; //获取当前页url
    var sendData = url.queryURLParameter(); //转query
    /* var sendData = {
         employeeid: 1,//'销售id',
         type: 1,      //	'获取预约标识，销售特殊入口进入的传入,传入1就可以',
         orderid: 1     //	'订单号'
     };*/
    $.ajax({
        type: "POST",

        url: 'http://bdl-cbp.yxonline.top/cbp/time/getEmployeeTime',

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
        var returnCarlist = data.returnCarlist;
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
        console.log(trueData)
        /*     //拼接html元素
         var str = '';
         for(var i=0;i<trueData.length;i++){
         str += '<div class="content ">';
         str += '<div class="dateTit">';
         str += '<div class="col-xs-4">';
         str += '<i></i>'+trueData[i][0].date;//取出日期
         str += '</div>';
         str += '</div>';
         str += '<div class="dateCon clearfix">';
         for(var j = 0;j<trueData[i].length;j++){
         var cur = trueData[i][j];
         var sta = cur.status == 2?'select':(cur.status == 1?'':'不可预约');
         str += '<div class="dateConTime '+sta+'">';
         str += '<span class="timeSlot">'+cur.starttime+'-'+cur.endtime+'</span>';
         str += '<em></em>';
         str += '</div>';
         }
         str += '</div>';
         str += '<div class="shadow"></div>';
         str += '</div>';
         }

         //写入DOM
         $('.bookingContent').html(str);
         */
        //绑定事件
        // bandEvent();

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
            $('.d' + curDate).find('div').removeClass().addClass(state)
        }
    }

    //预约时间表

    $('.dayList li').click(function () {

        // console.log(this)  
        var $this = $(this);
        var dateday = $this.attr('selectDay')

        $.get('timeTable.html',{dateday:dateday}, function (html) {

            $.dialog({
                width: '3.15rem',
                height: '6.3rem',
                content: html,
                dialogType: 'justOk',
                beforeOk: function () {
                    console.log($(".bdcon"));


                    $('.time').click(function () {
                        $(this).addClass('on').siblings().removeClass('on');
                    });

                },

                onOk: function (data) {

                    alert(123);
                }
            });
        });
    });



    //选择时间段弹框
    //$('.dayList li').click(function () {
    //    // console.log(this)
    //    var $this = $(this);
    //    $.get('dialog_selectTime.html', function (html) {
    //        $.dialog({
    //            width: '3.15rem',
    //            height: '3.74rem',
    //            content: html,
    //            dialogType: 'justOk',
    //            beforeOk: function () {
    //                $('.time').click(function () {
    //                    $(this).addClass('on').siblings().removeClass('on');
    //                });
    //            },
    //            onOk: function (data) {
    //                var sendData = {
    //                    timeSlot: $(data).find('.on span').html(),
    //                    dateTime: $this.attr('selectDay')
    //                };
    //                bandEvent(sendData)
    //            }
    //        });
    //    });
    //});

    //定义绑定事件&发送数据
    //function bandEvent(obj) {
    //    sureBtn();
    //    //确定按钮
    //    function sureBtn() {
    //        $('.checkDeliverBtn').show();
    //        $('.checkDeliverBtn a').unbind('click').click(function () {
    //            $.dialog({
    //                width: '2.3rem',
    //                height: '1.4rem',
    //                content: '<div class="dialog_sure"><h2>确认修改</h2><p>预约时间仅限修改1次</p></div>',
    //                onOk: function (data) {
    //                    var sendData = {
    //                        retcarid: 1,	//预约id
    //                        employeeid: 1,	//销售id
    //                        starttime: obj.timeSlot.split(' - ')[0],	//开始时间
    //                        endtime: obj.timeSlot.split(' - ')[1],	//结束时间
    //                        date: obj.dateTime,	//日期
    //                        status: 2,	//预约状态1--可预约，2--已预约，3--不可预约
    //                        customerid: 1,	//客户id
    //                        orderid: 1	//订单id
    //                    };
    //                    $.ajax({
    //                        type: "POST",
    //                        url: srcConfig.setCustomerTime,
    //                        dataType: 'json',
    //                        contentType: "application/json",
    //                        data: JSON.stringify(sendData),
    //                        success: function (data) {
    //                            if (data.code == 0) {
    //                                console.log(data.data);
    //                                //确定完毕之后解绑所有事件
    //                                $('.checkDeliverBtn').remove();
    //                                $('.dayList li').unbind('click')
    //                            } else {
    //                                console.log(data.message)
    //                            }
    //                        },
    //                        error: function (data) {
    //                            console.log(data)
    //                        }
    //                    });
    //                },
    //                onCancel: function (data) {
    //                    alert('点了取消')
    //                }
    //            });
    //        })
    //    }
    //}
});

