
$(function () {


    var str = '09:30';
    console.log($('.clock'))
    $('.clock').html(setClock(str))


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


    //var url = window.location.href; //获取当前页url
    //var sendData = url.queryURLParameter(); //转query

    var senddata = {

        employeeid: "2121",

        orderid: "5",
    };

    $.ajax({

        type: "POST",
        url: "http://bdl-cbp.blchina.com/cbp/time/getEmployeeTime",
        contentType: "application/json",

        data: JSON.stringify(senddata),

        success: function (data) {
            console.log(data);
            pagedata(data.data);
        
        },
        error: function (data1) {

       

        },
    })


    function pagedata(data) {

        var returnCalist = data.returnCarlist || [];//用户所有的数据
        var noVisibleList = data.noVisibleList || [];//被禁用的天

        var trueData = [];

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
        //整理数据
        ~function () {
            var ary = [];
            for (var i = 0; i < returnCalist.length; i++) {
                ary.push(returnCalist[i].date)
            }
            console.log(ary);

            var dateAry = ary.removeDup();//可预定的date日期

            for (var a = 0; a < dateAry.length; a++) {

                var curData = dateAry[a];

                var curList = [];

                for (var b = 0; b < returnCalist.length; b++) {

                    if (returnCalist[b].date == curData) {

                        curList.push(returnCalist[b])

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
                var sta = cur.status == 2 ? 'select' : (cur.status == 1 ? '' : '不可预约');
                str += '<div class="dateConTime ' + sta + '" trueDate="' + dateStr + '">';
                str += '<div class="clock">' + setClock(cur.starttime) + '</div>';
                str += '<p class="amOrPm">' + ((cur.starttime.split(':')[0] <= 12) ? 'AM' : 'PM') + '</p>';
                str += '<p class="timeSlot">' + cur.starttime + '-' + cur.endtime + '</p>';
                if (cur.status==2) {
  str += '<em class="q2"></em>';         
                }
                else if (cur.status==1) {
                    str += '<em class="q1"></em>';
                }
                         
                         
             
                str += '</div>';
            }
            str += '</div>';
            str += '<div class="shadow"></div>';
            str += '</div>';
        }

        //写入DOM
        $('.bookingContent').html(str);

      

        $(".dateConTime").on("click", function (e) {

            $(".dateConTime").removeClass('busy');
     
            var aa = $(e.target);
         
            var $others = aa.siblings();

            if (aa.hasClass('select')) {

                alert("不可约");

            }

            else {

                aa.hasClass('busy') ? aa.removeClass('busy') : aa.addClass('busy');


            }

            $others.removeClass("busy");


        })

        $("a").click(function () {

            var yiyue = $('.busy');
            for (var i = 0; i < yiyue.length; i++) {
                var as = $(yiyue[i]);
                
                var strTime = as.find('.timeSlot').html();
              
            }
            
            var starttime = strTime.split('-')[0];
            var endtime = strTime.split('-')[1];

            var date = as.attr('trueDate');
            var senddata = {
                retcarid: 1,
                employeeid: 1,
                starttime: starttime,
                endtime: endtime,
                date: date,
                status: 3,
                customerid: 1,
                orderid: 1
            };


            $.ajax({

                type: "POST",
                url: "http://bdl-cbp.blchina.com/cbp/time/setCustomerTime",
            
                contentType: "application/json",

                data: JSON.stringify(senddata),

                success: function (data) {

                    console.log(data);
                    alert("预约成功！");
                    $(".bookingBtn").hide();
                    $('.dateConTime').unbind('click')
                }

            })
      
        })
    }




});