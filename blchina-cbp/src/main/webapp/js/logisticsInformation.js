
$(function () {

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


    var url = window.location.href; //获取当前页url
    var sendata = url.queryURLParameter(); //转query
    var orderid = sendata.orderid;






    $.ajax({

        type: "POST",

        url: srcConfig.findbyorderid,

        //url: "js/wuliu.json",

        dataType: 'json',
        contentType: "application/json",
        data: JSON.stringify({ orderid: orderid }),
        success: function (data) {

            console.log(data);
            logistics(data);
        }
    })

    function logistics(data) {

        var wlnumber = [];
        var cartype = data.data.cartype;

        var zhuangtai = data.data.cbpLogistics;
        var saporderid = data.data.saporderid;

        console.log(zhuangtai);

        var str = "";

        for (var i = 0; i < zhuangtai.length; i++) {

            // var dateStr = zhuangtai[i].senddate;

            // var date_str = dateStr.replace(/(\d{4})(\d{2})(\d{2})/g, '$1-$2-$3');
            // console.log(date_str);

            // var dateAry = date_str.split('-');

            var status = zhuangtai[i].status;

            str += '<li>';
            str += '     <i class="node-icon"></i>';
            str += '    <span class="txt">' + status + '</span>';
            str += ' </li>';
        }
        $(".dindanid").text(saporderid);
        $(".car").html(cartype);

        $("ul").html(str);
        $(".wuliu tr:last").remove();

        $("ul li").eq(0).addClass('first');
        $("ul li").eq(0).find(".node-icon").removeAttr("background");
        $("ul li").eq(0).find(".node-icon").css("background-image","url(./img/icon-OK.png)");
    
        $('ul li').last().css("height", "0");
    }
})
