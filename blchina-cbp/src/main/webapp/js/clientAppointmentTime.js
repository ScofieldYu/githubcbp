 $(function () {
    var senddata = {

        employeeid: "1",

        orderid: "1",
    };

    $.ajax({

        type: "GET",

        url: "js/sj.json",

        contentType: "application/json",

        data: JSON.stringify(senddata),

        success: function (data) {

            console.log(data.data);

            pagedata(data.data.returnCarlist);

        },
        error: function (data1) {

            console.log(data1)

        },
    })


    function pagedata(data) {
      
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

            for (var i = 0; i < data.length; i++) {

                ary.push(data[i].date)

            }
            console.log(ary);

            var dateAry = ary.removeDup();//可预定的date日期
   
            for (var a = 0; a < dateAry.length; a++) {

                var curData = dateAry[a];

                var curList = [];

                for (var b = 0; b < data.length; b++) {

                    if (data[b].date == curData) {

                        curList.push(data[b])

                    }
                }
                trueData.push(curList);
            }
        }();
        //拼接html元素
        var str = '';
        for (var i = 0; i < trueData.length; i++) {
            str += '<div class="content ">';
            str += '<div class="dateTit">';
            str += '<div class="col-xs-4">';
            str += '<i></i>' + trueData[i][0].date;//取出日期
            str += '</div>';
            str += '</div>';
            str += '<div class="dateCon clearfix">';
            for (var j = 0; j < trueData[i].length; j++) {
                var cur = trueData[i][j];
                var sta = cur.status == 2 ? 'select' : (cur.status == 1 ? '' : '不可预约');
                str += '<div class="dateConTime ' + sta + '">';
                str += '<span class="timeSlot">' + cur.starttime + '-' + cur.endtime + '</span>';

                if (sta == 1) {

                    str += '<em class="q1"></em>';

                }
                else if (sta = 2) {

                    str += '<em class="q2"></em>';
                }

                str += '</div>';
            }

            str += '</div>';
            str += '<div class="shadow"></div>';
            str += '</div>';

        }

        //写入DOM
        $('.bookingContent').html(str);

        $(".content").on("click", function () {

            var date1 = $(e.target);




        });
       
        $(".dateConTime").on("click", function (e) {

            var aa = $(e.target);

            var $others = aa.siblings();

           if(aa.hasClass('select')) {

               alert("不可约");

           }

           else {
             
               aa.hasClass('busy') ? aa.removeClass('busy') : aa.addClass('busy');
               //var time = aa.parent("dateCon").prev("dateTit").children("col - xs - 4").html();
               //alert(time);

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
     var date =       as.attr('trueDate');
            alert(date);
            $.ajax({
                type: "POST",

                url: "http://bdl-cbp.yxonline.top/cbp/time/setCustomerTime",

                contentType: "application/json",

                data: JSON.stringify(senddata),

                success: function (data) {

                    console.log(data);

            }

            })

               })
    }
  

 
})
 



