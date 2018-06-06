var currselected =2; // 变量：当前所选行
var heightWindow = 0; // 变量：窗口高度



//var a =
//function de(a) {
//    if (a == 1) {
//        window.location.href = "wlzt.html?name=1";
//    } else if (a == 2) {
//        window.location.href = "wlzt.html?name=2";
//    } else if (a == 3) {
//        window.location.href = "wlzt.html?name=3";
//    }
//}

$(document).ready(function () {

    $(".sjs:not(:first)").hide();

    $(".djsj").click(function () {

        $(".sjs:visible").hide();

        $(this).next().show();
        return false;

    });


    // 获取URL中的参数：按名字
    function GetQueryString(name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
        var r = window.location.search.substr(1).match(reg);  //匹配目标参数
        if (r != null)
            {
        return unescape(r[2]);
        }
        return null; //返回参数值
    }
    var  sname= GetQueryString("name");
              
           if (sname != null)
{  
         if (sname == 1) {
             $("#cartb img").attr('src', '../img/奥迪.png'); //奥迪 
             $("#cartb input").click(function () {
                 window.location.href = "wlzt.html?name="+sname;
             })
    }
         else if (sname == 2) {
             $("#cartb img").attr('src', '../img/保时捷.png'); //保时捷
             $("#cartb input").click(function () {
                 window.location.href = "wlzt.html?name=2";
             })
         } else if (sname = 3) {
             $("#cartb img").attr('src', '../img/奔驰.png');//奔驰
             $("#cartb input").click(function () {
                 window.location.href = "wlzt.html?name=3";
             })
    } else {
        return null;
    }
}
else {
    return null;
}
          


});



//$(document).ready(function () {
//     //初始化：事件
//    //$("#btnShowList").attr("onclick", "event_btnShowList_Click();");

//    //$("#btnShowList2").attr("onclick", "event_btnShowList_Click();");


//    //heightWindow == $(window).height();
//    //var _heightDetail2 = heightWindow -100;
//    //$("#detail2").css("height", _heightDetail2 + "px");


//});

//function dj() {
  
//        if (currselected = 2) {

//            $("#btnShowList2").bind("click", function () {

//                $("#detail2").show("slow");
//                $("#detail1").hide();
//            })
//        }
//        else {
//            $("#detail3").show("slow");
//            $("#detail2").hide();
//        }

//    }


