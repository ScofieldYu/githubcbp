  
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
             $("#cartb img").attr('src', '../img/536360277393156650.png'); //奥迪 
             $("#cartb input").click(function () {
                 window.location.href = "wlzt.html?name="+sname;
             })
    }
         else if (sname == 2) {
             $("#cartb img").attr('src', '../img/362931900983898429.png'); //保时捷
             $("#cartb input").click(function () {
                 window.location.href = "wlzt.html?name=2";
             })
         } else if (sname = 3) {
             $("#cartb img").attr('src', '../img/157615907823872026.png');//奔驰
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
          
