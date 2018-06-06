$(function () {
     var storage = window.localStorage;
     var object = {},senddata=null;
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
    getFields();
    function getFields(){
        
        //获取地址栏参数
        var url = decodeURI(location.search);
        if(url.split("=")[0].slice(1)=="data"){
            var urlNew = url.split("=")[1];
            var list = urlNew.split("-");
            var newA1 = "";
            for(var i = 0;i<list.length;i++){
                var str;
                if(i%2 == 0){
                    str = ";"
                    if(i == 0){
                        str = "";
                    }
                    newA1 += str + list[i];
                }else{
                    str = ",";
                    if(i == 0){
                        str="";
                    }
                    newA1+=str+list[i];
                }
            }
            var dataNewS = newA1.split(";");
            for(var i = 0; i < dataNewS.length; i ++){
                {   
        　　　　　　　object[dataNewS[i].split(",")[0]] = dataNewS[i].split(",")[1];
        　　　　 }
    　　     }
            if(object){
                object.employeeid = Number(object.employeeid)  //销售id
                object.orderid = Number(object.orderid)        //订单id

                senddata = {
                    "employeeid":object.employeeid,//销售id
                    "orderid":object.orderid       //订单ids
                }
                ajax(senddata)  //--打开
            }else{

                console.log("客户信息获取失败");

            }
        }else{
            if(url.indexOf("?") != -1)//url中存在问号，也就说有参数。  
            {   
            var str = url.substr(1);  //得到?后面的字符串
            var strs = str.split("&");  //将得到的参数分隔成数组[id="123456",Name="bicycle"];
            for(var i = 0; i < strs.length; i ++)  
                {   
        　　　　　　　　object[strs[i].split("=")[0]]=strs[i].split("=")[1]
        　　　　 }
    　　     }
            if(object){
                
                object.employeeid = Number(object.employeeid)
                object.orderid = Number(object.orderid)
                senddata = {
                    "employeeid":object.employeeid,//销售id
                    "orderid":object.orderid       //订单ids
                }
             ajax(senddata)  //--打开
            }else{
                console.log("客户信息获取失败");
            }
        }
        console.log(object)
        console.log(senddata)
     }
     //封装 公用ajax请求
    // ajax(data)
     function ajax(senddata){
        
             $.ajax({
                    type: "POST",
                    url: srcConfig.getCustomerTime,
                    contentType: "application/json",
                    data: JSON.stringify(senddata),
                    success: function (data) {
                        renderPage(data);
                        highLight();
                    },
                    error:function(){
                        console.log("请求失败...");
                    }
                })
     }
      function renderPage(originalData) {
      // var returnCarlist = originalData.data.returnCarlist;
        var returnCarlist = originalData.data || [];//用户所有的数据    *****把这个打开
        var trueData = [];
        var dateAry = "";
        var phone = originalData.message;
        //整理数据
        ~function () {
            var ary = [];
            retcarid = returnCarlist[0].retcarid;
            customerid = returnCarlist[0].customerid;
            for (var i = 0; i < returnCarlist.length; i++) {
                ary.push(returnCarlist[i].date)
            }
            dateAry = ary.removeDup();  //可预定的date日期
            for (var a = 0; a < dateAry.length; a++) {
                var curData = dateAry[a];
                var curList = [];
                for (var b = 0; b < returnCarlist.length; b++) {
                    if (returnCarlist[b].date == curData) {
                        curList.push(returnCarlist[b]);
                    }
                }
                trueData.push(curList);
            }
             var mon=dateAry.slice(-1).join("-").split("-")[1];
             var day=dateAry.slice(-1).join("-").split("-")[2];
            //  alert(mon+"-"+day);
            //  alert(phone);
             $(".dataNow").text(mon+"月"+day+"日");
             $(".phoneBar").html(`<a href="tel:${phone}" class="phone">${phone}</a>`);
        }();
        //拼接html元素
        var str = '';

        for (var i = 0; i < trueData.length; i++) {
            //暂存当天日期
            var dateStr = trueData[i][0].date;
            str += '<div class="content" lockDate="' + dateStr + '">';
            str += '<div class="dateTit">';
            var dateAry = dateStr.split('-');
            str += '<i></i><span  class="date">' + dateAry[1] + '月' + dateAry[2] + '日';//取出日期
            str += '</span></div>';
            str += '<div class="dateCon clearfix">';
            for (var j = 0; j < trueData[i].length; j++) {
                var cur = trueData[i][j];
                //var sta = cur.status == 2 ? 'select' : '';
                
                var sta = trueData[i][j].orderid == object.orderid ? 'select' : '';
                switch(trueData[i][j].status){
                    case "1":
                        str += `<div class="dateConTime odd">`;
                    break;
                    case "2":
                        str += `<div class="dateConTime ${sta}">`;
                    break;
                    case "3":
                        str += `<div class="dateConTime">`;
                    break;
                }
                 str += `<div class="clock"> ${setClock(cur.starttime) }</div>`;
                 str += `<p class="amOrPm">${((cur.starttime.split(':')[0]<=12)?'AM':'PM')}</p>`;
                 str += `<p class="timeSlot">${cur.starttime + ' -' + cur.endtime }</p>`;
                switch(trueData[i][j].status){
                    case "1":
                        str += '<em class="lock" id="lock"></em><em class="status" status="'+trueData[i][j].status +'" date="'+trueData[i][j].date +'" endtime="'+trueData[i][j].endtime +'"  starttime="'+trueData[i][j].starttime +'" retcarid="'+trueData[i][j].retcarid +'" orderid="'+trueData[i][j].orderid +'"></em>';
                    break;
                    case "2":
                        if(trueData[i][j].orderid==object.orderid){
                            str += '<em class="lock on"></em><em class="status q2" status="'+trueData[i][j].status +'" date="'+trueData[i][j].date +'" endtime="'+trueData[i][j].endtime +'"  starttime="'+trueData[i][j].starttime +'" retcarid="'+trueData[i][j].retcarid +'"  orderid="'+trueData[i][j].orderid +'"></em>';
                        }else{
                            str += '<em class="lock"></em><em class="status q1" status="'+trueData[i][j].status +'" date="'+trueData[i][j].date +'" endtime="'+trueData[i][j].endtime +'"  starttime="'+trueData[i][j].starttime +'" retcarid="'+trueData[i][j].retcarid +'"  orderid="'+trueData[i][j].orderid +'"></em>';
                        }
                    break;
                    case "3":
                        str += '<em class="lock"></em><em class="status q1" status="'+trueData[i][j].status +'" date="'+trueData[i][j].date +'" endtime="'+trueData[i][j].endtime +'"  starttime="'+trueData[i][j].starttime +'" retcarid="'+trueData[i][j].retcarid +'"  orderid="'+trueData[i][j].orderid +'"></em>';
                    break;
                }
                str += '</div>';
            }
            str += '</div>';
            str += '<div class="shadow"></div>';
            str += '</div>';    
        }
        //写入DOM
        $('.bookingContent').html(str);
    }
    
    //遍历高亮 
    function highLight(){

        if($(".bookingContent").find(".on").length!=0){
            $(".changed").html("修改");            
            if($(".bookingContent").find(".select").find(".status").attr("orderid")!=object.orderid){
                 storage.removeItem("id");
            }else{
                 storage.setItem('id',"appointment");
            }
        }else{
            $(".changed").html("预约");
        }
        if(!$(".dateConTime").hasClass("select")){
            storage.removeItem("id")
        }
    }
    //指针封装
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
    //复用判断是否是本人id
    function public($this){
        if(object.orderid == $this.find(".status").attr("orderid")){
                        
            $this.find(".lock").removeClass("on").addClass("off");
            // codeMes = storage.getItem("id");
            storage.removeItem("id");

        }else{
            
            if($this.find(".status").hasClass("q1")){
                alert("当天已经约满,请改选一天");
            }
        }
    }
    //添加高亮 类名
    clickGet()
    function clickGet(){
        
        var message = null;
       
        $(".bookingContent").on("click",".dateConTime",function(){
            var $length = $(this).parents(".bookingContent").find(".dateConTime").find(".on").length;
            console.log($length)
            if($(".bookingContent").find(".off").length == 0){
                if(!storage.getItem("id")){
                     $(".bookingBtn").show();
                }
                     $(".booking").hide();
                    
            }else{
               if(!storage.getItem("id")){
                     $(".booking").show();
               }
                     $(".bookingBtn").hide();
            }

            if(storage.getItem("id")){
                
                if(!$(this).hasClass("select")){
                   
                    if($length!="0"){

                         alert("请先解锁");
                         
                    }else{

                         return false;
                    }
                }else{

                   public($(this));
                
                }
            }else{
                
            var li = Array.from($(".bookingContent").find(".odd"));
                
            switch($(this).find(".status").attr("status")){    

                case "1":
                     //遍历查找可预约的id  status=1
                     for(var i=0;i<li.length;i++){

                         $(li[i]).removeClass("select");
                         $(li[i]).find(".status").removeClass("q2");
                         
                     }
                    if($(this).parents(".bookingContent").find(".dateConTime").find(".off")) {
                        var $off = $(this).parents(".bookingContent").find(".dateConTime").find(".off");
                        $off.parent(".dateConTime").removeClass("select");
                        $($off[0]).siblings(".status").removeClass("q2");
                        $(this).addClass("select");
                        $(this).find(".status").addClass("q2");
                     }
                break;
                case "2":
                    public($(this));
                break;
                case "3":
                    alert("已经约满,请改选另一天");
                    return false;
                break;
            }
            
            message = {

                retcarid : Number($(this).find(".status").attr("retcarid")),  
                employeeid : object.employeeid,
                starttime : $(this).find(".status").attr("starttime"),
                endtime : $(this).find(".status").attr("endtime"),
                date : $(this).find(".status").attr("date"),
                status : $(this).find(".status").attr("status"),
                orderid : object.orderid
            }
           }
        })
        
        $(".booking a").on("click",function(e){
            //判断按钮 确定 or 取消
            if($(e.target).attr("judge") === "no"){
                
                storage.setItem("id","no");
                $(".booking").hide();
                $(".bookingContent").find(".dateConTime").removeClass("select");
                $(".bookingContent").find(".status").removeClass("q2");
                $(".bookingContent").find(".off").addClass("on").removeClass("off");
                $(".bookingContent").find(".on").parents(".dateConTime").addClass("select");
                $(".bookingContent").find(".on").siblings(".status").addClass("q2");
                message = null;

            }else{
               
                if(message){
                    
                    message.status = "2";

                }else{

                    return false;

                }
                console.log(message)
                 $.ajax({
                    type: "POST",
                    url: srcConfig.setCustomerTime,
                
                    contentType: "application/json",
                    
                    data: JSON.stringify(message),

                    success: function (data) {

                        if(data.code == "0"){

                          if(message){
                                alert("修改成功");
                                $(".booking").hide();
                                storage.setItem("id",message.date);
                                message = null;
                                $(".booking a").off("click");
                          }else{
                               return false;
                          }
                            
                        }else if(data.code == "100"){
                             alert("预约时间超过时间限制");
                             message = null;
                             $(".booking").hide();
                        }else{
                            alert(data.message);
                            ajax(senddata);
                            $(".booking").hide();
                        }
                    },
                    error:function(){
                        console.log("失败...");
                    }
                })
            }
        })
        
        $(".bookingBtn a").on("click",function(){
            
            if(storage.getItem("id")){
               
                alert("请先解锁");
                return false;

            }else{
                if(message){
                    $(".bookingBtn a").off("click");
                    $.ajax({
                        type: "POST",
                        url: srcConfig.setCustomerTime,
                        contentType: "application/json",
                        data: JSON.stringify(message),
                        success: function (data) {
                            switch(data.code){
                                case "0":
                                    alert("预约成功");
                                    $(".bookingBtn").hide();
                                    storage.setItem("id",message.date);
                                    message = null;
                                break;
                                case "101":
                                    alert(data.message);
                                    ajax(senddata);
                                    storage.removeItem('id');
                                    alert("本地数据已清");
                                    message = null;
                                break;
                                case "100":
                                    alert("预约时间超过时间限制");
                                    message = null;
                                break;
                            }
                        },
                        error:function(){
                            console.log("失败...");
                        }
                    })
                }else{
                    return false;
                }
            }
        })
        //刷新页面;
        $(".refreshBtn").on("click",function(){
            ajax(senddata)
            $(".bookingBtn").hide();
            $(".booking").hide();
        })    
    }
})