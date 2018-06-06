$(function(){
    var session = window.sessionStorage;
    var url = window.location.href; //获取当前页url
   
    var tokenQuery = url.queryURLParameter();
    
    var object = {};

    object['cardid'] = tokenQuery['cardid'];
   
    //显示日历
	$('.tim').date_input();    
    $(".date_selector").animate({"right":".1rem","top":".55rem"});
    // var se = {};
    //  se={code: '0',
    //  message: '登录成功',
    //  data: { 
    //  employeeid: '20488',
    //  phonenumber: '13120370722',
    //  name: '余志诚',
    //  wxname: '余志诚',
    //  token:'5bf2495f62a1a3ba714dc408aef56193',
    //  accounttype:'8'}};
    //  session.setItem('data',JSON.stringify(se))
    //   var dataData = JSON.parse(session.getItem("data"))['data'];
    // $ajax("http://localhost:8086",object);
     $ajax(srcConfig.selectCardDetails,object);
    function $ajax($url,sendData){ 
    $.ajax({ 
            type: "POST",
            url: $url,
            dataType: 'json',
            contentType: "application/json",   //这个放开
            data: JSON.stringify(sendData),
            success: function (data) {
                switch(data['code']){
                    case "0":
                        alert("成功");
                        console.log(data['data']);
                        bdlList(data['data']);
                        bilPush(data['data']);
                        $jax(data['data']);
                    break;
                    case "101":
                        alert("失败");
                    break;
                    case "102":
                        alert("参数不正确");
                    break;
                }
            },
            error: function (data) {
                alert('请求失败');
            }
        });
   }
   function $jax(data){
       var $phoneNumber = data['phoneNumber'] || '';//手机号
        $(".spanBox").attr("href","tel:"+$phoneNumber+"");
        $(".spanBox").off("click").on("click",function(event){
              
            event.stopPropagation();
            
        });
   }
   //绑定
   function bdlList(data){
        var str = '',count = 0;
        var $customername = data['bdlCard']['customername'] || '', //姓名
            $orderid = data['bdlCard']['orderid'] || '',//订单号
            $enddate = data['bdlCard']['enddate']||'';//完成时间
            $cardstatus= data['bdlCard']['cardstatus']||'';//卡片状态
            $(".name").html($customername);    
            $(".num").html($orderid);
            $(".tim").val($enddate);
            //公用
            $.each(data['taskList'],function(index,val){

            if(val['taskstatus'] == "3"){
                count++;
            }
            str += `
                <div class="bdl_Ltitler" id="bdlLetter" taskid=${val['taskid']} taskstatus=${val['taskstatus']}>
                <span class="icon"></span>   
                <p class="bld_mest">
                    <span class="listBdl">
                        <span class="okIcon ${$status(val['taskstatus'])}"></span>
                        <span class="add">${val['description']}</span>
                    </span>
                </p>
                <span class="addPu"></span>
                </div>
            `;  
            })
            $(".bdl_boxAdd").html(str);
            $(".numtwo").html(data['taskList'].length); //总数
            $(".numOne").html(count); //完成数
            switch(data['bdlCard']['cardtype']){
               //销售顾问 购车下单
                case "1":
                    $(".bdl_task").html(returnTask(data['bdlCard']['cardtype'] || '')['message']);
                 break;
               //销售顾问 订单确认
               case "2":
                    $(".bdl_task").html(returnTask(data['bdlCard']['cardtype'] || '')['message']);
               break; 
               //销售顾问 二手车
               case "3":
                    $(".bdl_task").html(returnTask(data['bdlCard']['cardtype'] || '')['message']);
               break; 
               //销售顾问 办理贷款
               case "4":
                    $(".bdl_task").html(returnTask(data['bdlCard']['cardtype'] || '')['message']);
                    $(".complete").hide();
                    $(".back").val("完成");
                    $(".bdl_Ltitler").eq(0).addClass("see");
                    $(".back").on("click",function(){
                        if($("#bdlLetter").find(".okIcon").hasClass("ok")){
                            $aja(data);
                            $.ajax({ 
                                type: "POST",
                                url: srcConfig.updateCardTaskList,
                                dataType: 'json',
                                contentType: "application/json",   //这个放开
                                data: JSON.stringify({
                                    "taskList":[
                                        {"taskid":$("#bdlLetter").attr("taskid"),"taskstatus":$("#bdlLetter").attr("taskstatus")},
                                    ]
                                }),
                                success: function (data) {
                                    switch(data['code']){
                                        case "0":
                                            alert("联系成功");
                                            //跳转 任务列表
                                            window.location.href = "./bdlSell_card.html";
                                        break;
                                        case "101":
                                            alert("失败");
                                        break;
                                        case "102":
                                            alert("参数不正确");
                                        break;
                                    }
                                },
                                error: function (data) {
                                    alert('请求失败');
                                }
                            });
                        }else{
                            alert("请先联系金融经理");
                            return false;
                        }
                    })
                    //进入 订单详情
                   $(".see").on("click",function(){
                         window.location.href = "./paperlessOfficeSales/orderDetails.html?orderid="+data['bdlCard']['orderid']+"&employeeid="+data['bdlCard']['employeeid']+"";
                   });
               break; 
               //销售顾问 车辆置换
               case "5":
                    $(".bdl_task").html(returnTask(data['bdlCard']['cardtype'] || '')['message']);
                    $(".complete").hide();
                    $(".back").val("完成");
                    $(".addPu").eq(1).hide();
                    $(".bdl_Ltitler").eq(0).addClass("see");
                    $(".bdl_Ltitler").eq(1).addClass("seey");
                    $(".see").on("click",function(){
                         window.location.href = "./paperlessOfficeSales/orderDetails.html?orderid="+data['bdlCard']['orderid']+"&employeeid="+data['bdlCard']['employeeid']+"";
                    })
                    $(".back").on("click",function(){
                        if($(".see").find(".okIcon").hasClass("ok")){
                            $aja(data);
                            $.ajax({ 
                                type: "POST",
                                url: srcConfig.updateCardTaskList,
                                dataType: 'json',
                                contentType: "application/json",   //这个放开
                                data: JSON.stringify({
                                    "taskList":[
                                        {"taskid":$(".seey").attr("taskid"),"taskstatus":$(".seey").attr("taskstatus")},
                                    ]
                                }),
                                success: function (data) {
                                    switch(data['code']){
                                        case "0":
                                            alert("预约成功");
                                            //跳转 任务列表
                                            window.location.href = "./bdlSell_card.html";
                                        break;
                                        case "101":
                                            alert("失败");
                                        break;
                                        case "102":
                                            alert("参数不正确");
                                        break;
                                    }
                                },
                                error: function (data) {
                                    alert('请求失败');
                                }
                            });
                        }else{
                            alert("请先预约二手车评估");
                            return false;
                        }
                    })
               break; 
               //销售顾问 车险 
               case "6":
                   $(".bdl_task").html(returnTask(data['bdlCard']['cardtype'] || '')['message']);
                   $(".back").val("完成");
                   $(".addPu").hide();
                   $(".back").on("click",function(){
                    //设置完成时间
                    $aja(data);
                    $.ajax({ 
                        type: "POST",
                        url: srcConfig.updateCardTaskList,
                        dataType: 'json',
                        contentType: "application/json",   //这个放开
                        data: JSON.stringify({
                            "taskList":[
                                {"taskid":$("#bdlLetter").attr("taskid"),"taskstatus":$("#bdlLetter").attr("taskstatus")},
                            ]
                        }),
                        success: function (data) {
                            switch(data['code']){
                                case "0":
                                    alert("办理成功");
                                    //跳转 任务列表
                                    window.location.href = "./bdlSell_card.html";
                                break;
                                case "101":
                                    alert("失败");
                                break;
                                case "102":
                                    alert("参数不正确");
                                break;
                            }
                        },
                        error: function (data) {
                            alert('请求失败');
                        }
                    });
                })
                $(".complete").on("click",function(){
                     window.location.href = "./paperlessOfficeSales/orderDetails.html?orderid="+data['bdlCard']['orderid']+"&employeeid="+data['bdlCard']['employeeid']+"";
                })
               break; 
               //销售顾问 验车上牌 
               case "7":
                    $(".bdl_task").html(returnTask(data['bdlCard']['cardtype'] || '')['message']);
                    $(".back").val("查看订单");
                    $(".complete").hide();
                    $(".bdl_Ltitler").eq(0).addClass("see1");
                    $(".bdl_Ltitler").eq(1).addClass("see1");
                    $(".bdl_Ltitler").eq(2).addClass("see1");
                    $(".bdl_Ltitler").eq(3).addClass("see");
                    $(".bdl_Ltitler").eq(4).addClass("see");
                    $(".see1").on("click",function(){
                        window.location.href = "./bdlSell_collectingData.html";
                    })
                    $(".see").on("click",function(){
                        window.location.href = "./paperlessOfficeSales/orderDetails.html?orderid="+data['bdlCard']['orderid']+"&employeeid="+data['bdlCard']['employeeid']+"";
                    })
                    $(".back").on("click",function(){
                        window.location.href = "./paperlessOfficeSales/orderDetails.html?orderid="+data['bdlCard']['orderid']+"&employeeid="+data['bdlCard']['employeeid']+"";
                    })
               break; 
               //销售顾问 加装精品 
               case "8":
                    $(".bdl_task").html(returnTask(data['bdlCard']['cardtype'] || '')['message']);
                    $(".back").val("完成");
                    $(".complete").hide();
                    $(".addPu").eq(3).hide();
                    $(".bdl_Ltitler").eq("0").addClass("see");
                    $(".bdl_Ltitler").eq("1").addClass("seey");
                    $(".bdl_Ltitler").eq("2").addClass("ssure");
                    //销售预约 加装精品时间
                    $(".see").on("click",function(){
                        //如果预约了 就跳转订单详情
                       window.location.href = "./paperlessOfficeSales/orderDetails.html?orderid="+data['bdlCard']['orderid']+"&employeeid="+data['bdlCard']['employeeid']+"";
                    })
                    $(".seey").on("click",function(){
                        if($(".see").find(".okIcon").hasClass("ok")){
                            window.location.href = "./paperlessOfficeSales/orderDetails.html?orderid="+data['bdlCard']['orderid']+"&employeeid="+data['bdlCard']['employeeid']+"";
                        }else{
                            alert("请先预约加装精品");
                            return false;
                        }
                    })
                    $(".ssure").on("click",function(){
                        if($(".see").find(".okIcon").hasClass("ok")&&$(".seey").find(".okIcon").hasClass("ok")){
                        window.location.href = "./paperlessOfficeSales/orderDetails.html?orderid="+data['bdlCard']['orderid']+"&employeeid="+data['bdlCard']['employeeid']+"";
                        }else{
                            alert("请先完成前两条任务");
                            return false;
                        }
                    })
                    $(".back").on("click",function(){
                        $aja(data);
                        window.location.href = "./bdlSell_card.html";
                    })
               break; 
               //财务 确认收定金 
                case "9":
                    $(".bdl_task").html(returnTask(data['bdlCard']['cardtype'] || '')['message']);
                break;
                //财务收尾款
                case "10":
                break;
               //财务确认收全款
               case "11":
                    $(".sureText").html("");
                    $(".spanBox").hide();
                    $(".textSpan").css("width","100%");
                    $(".bdl_task").html(returnTask(data['bdlCard']['cardtype'] || '')['message']);
                    $(".addPu").hide();
                    $(".complete").on("click",function(){
                        window.location.href = "./paperlessOfficeSales/orderDetails.html?orderid="+data['bdlCard']['orderid']+"&employeeid="+data['bdlCard']['employeeid']+"";
                    })
                    $(".back").on("click",function(){
                        //进入查看订单
                        $aja(data);
                        $.ajax({ 
                            type: "POST",
                            url: srcConfig.updateCardTaskList,
                            dataType: 'json',
                            contentType: "application/json",   //这个放开
                            data: JSON.stringify({
                                "taskList":[
                                    {"taskid":$("#bdlLetter").attr("taskid"),"taskstatus":$("#bdlLetter").attr("taskstatus")},
                                ]
                            }),
                            success: function (data) {
                                switch(data['code']){
                                    case "0":
                                        alert("确认成功");
                                        //跳转 任务列表
                                        window.location.href = "./bdlSell_card.html";
                                    break;
                                    case "101":
                                        alert("失败");
                                    break;
                                    case "102":
                                        alert("参数不正确");
                                    break;
                                }
                            },
                            error: function (data) {
                                alert('请求失败');
                            }
                        });
                    })
               break;
               //确认金融分期收款
               case "12":
               break;
               //确认二手车收款
               case "13":
               break;
               //承包顾问办理车险
               case "14":
                   $(".bdl_task").html(returnTask(data['bdlCard']['cardtype'] || '')['message']);
                   $(".back").val("完成");
                   $(".addPu").hide();
                   $(".back").on("click",function(){
                    $aja(data);  
                    $.ajax({ 
                        type: "POST",
                        url: srcConfig.updateCardTaskList,
                        dataType: 'json',
                        contentType: "application/json",   //这个放开
                        data: JSON.stringify({
                            "taskList":[
                                {"taskid":$("#bdlLetter").attr("taskid"),"taskstatus":$("#bdlLetter").attr("taskstatus")},
                            ]
                        }),
                        success: function (dataD) {
                            switch(dataD['code']){
                                case "0":
                                    alert("办理成功");
                                        //跳转 购车订单***********************
                                        window.location.href = "./bdlSell_card.html";
                                break;
                                case "101":
                                    alert("失败");
                                break;
                                case "102":
                                    alert("参数不正确");
                                break;
                            }
                        },
                        error: function (data) {
                            alert('请求失败');
                        }
                    });
                   });
                   $(".complete").on("click",function(){
                       window.location.href = "./paperlessOfficeSales/orderDetails.html?orderid="+data['bdlCard']['orderid']+"&employeeid="+data['bdlCard']['employeeid']+"";
                   })
               break;
               //评估师置换
               case "15":
                $(".bdl_task").html(returnTask(data['bdlCard']['cardtype'] || '')['message']);
                $(".complete").hide();
                $(".back").val("完成");
                $(".addPu").eq(2).hide();
                $(".bdl_Ltitler").eq(0).addClass("see");
                $(".bdl_Ltitler").eq(1).addClass("see");
                $(".bdl_Ltitler").eq(2).addClass("seey");
                $(".see").on("click",function(){
                    window.location.href = "./paperlessOfficeSales/orderDetails.html?orderid="+data['bdlCard']['orderid']+"&employeeid="+data['bdlCard']['employeeid']+"";
                })
                $(".back").on("click",function(){
                    if($(".see").eq(0).find(".okIcon").hasClass("ok")&&$(".see").eq(1).find(".okIcon").hasClass("ok")){
                    $aja(data);
                    $.ajax({ 
                        type: "POST",
                        url: srcConfig.updateCardTaskList,
                        dataType: 'json',
                        contentType: "application/json",   //这个放开
                        data: JSON.stringify({
                            "taskList":[
                                {"taskid":$(".seey").attr("taskid"),"taskstatus":$(".seey").attr("taskstatus")},
                            ]
                        }),
                        success: function (data) {
                            switch(data['code']){
                                case "0":
                                    alert("成功");
                                    //跳转 任务列表
                                    window.location.href = "./bdlSell_card.html";
                                break;
                                case "101":
                                    alert("失败");
                                break;
                                case "102":
                                    alert("参数不正确");
                                break;
                            }
                        },
                        error: function (data) {
                            alert('请求失败');
                        }
                    });
                    }else{
                        alert("请先完成预约时间和设置价格");
                        return false;
                    }
                })
               break;
               //二手车财务
               case "16":
                    $(".spanBox").hide();
                    $(".textSpan").css("width","100%");
                    $(".sureText").html("请确认已完成");
                    $(".bdl_task").html(returnTask(data['bdlCard']['cardtype'] || '')['message']);
                    $(".addPu").hide();
                    //财务顾问
                    $(".complete").on("click",function(){
                        window.location.href = "./paperlessOfficeSales/orderDetails.html?orderid="+data['bdlCard']['orderid']+"&employeeid="+data['bdlCard']['employeeid']+"";
                    })
                    $(".back").on("click",function(){
                        //进入查看订单
                        $aja(data);
                        $.ajax({ 
                            type: "POST",
                            url: srcConfig.updateCardTaskList,
                            dataType: 'json',
                            contentType: "application/json",   //这个放开
                            data: JSON.stringify({
                                "taskList":[
                                    {"taskid":$("#bdlLetter").attr("taskid"),"taskstatus":$("#bdlLetter").attr("taskstatus")},
                                ]
                            }),
                            success: function (data) {
                                switch(data['code']){
                                    case "0":
                                        alert("确认成功");
                                        //跳转 任务列表
                                        window.location.href = "./bdlSell_card.html";
                                    break;
                                    case "101":
                                        alert("失败");
                                    break;
                                    case "102":
                                        alert("参数不正确");
                                    break;
                                }
                            },
                            error: function (data) {
                                alert('请求失败');
                            }
                        });
                })
               break;
               //金融经理 办理金融分期
               case "17":
                    $(".bdl_task").html(returnTask(data['bdlCard']['cardtype'] || '')['message']);
                    $(".complete").hide();
                    $(".back").val("完成");
                    $(".bdl_Ltitler").eq(0).addClass("see");
                    $(".bdl_Ltitler").eq(1).addClass("seey");
                    $(".addPu").eq(1).hide();
                    $(".back").on("click",function(){
                        if($(".see").find(".okIcon").hasClass("ok")){
                            $aja(data);
                            $.ajax({ 
                            type: "POST",
                            url: srcConfig.updateCardTaskList,
                            dataType: 'json',
                            contentType: "application/json",   //这个放开
                            data: JSON.stringify({
                                "taskList":[
                                    {"taskid":$(".seey").attr("taskid"),"taskstatus":$(".seey").attr("taskstatus")},
                                ]
                            }),
                            success: function (dataD) {
                                switch(dataD['code']){
                                    case "0":
                                        alert("办理成功");
                                        window.location.href = "./bdlSell_card.html";
                                    break;
                                    case "101":
                                        alert("失败");
                                    break;
                                    case "102":
                                        alert("参数不正确");
                                    break;
                                }
                            },
                            error: function (data) {
                                alert('请求失败');
                            }
                        });
                        }else{
                            alert("请先进行预约家访");
                            return false;
                        }
                    })
                    $(".see").on("click",function(){
                        
                        window.location.href = "./paperlessOfficeSales/orderDetails.html?orderid="+data['bdlCard']['orderid']+"&employeeid="+data['bdlCard']['employeeid']+"";
                    });
               break;
               //精品顾问 准备精品
               case "18":
                    $(".bdl_task").html(returnTask(data['bdlCard']['cardtype'] || '')['message']);
                    $(".back").val("完成");
                    $(".addPu").hide();
                    //完成
                    $(".back").on("click",function(){
                    $aja(data);
                    $.ajax({ 
                        type: "POST",
                        url: srcConfig.updateCardTaskList,
                        dataType: 'json',
                        contentType: "application/json",   //这个放开
                        data: JSON.stringify({
                            "taskList":[
                                {"taskid":$("#bdlLetter").attr("taskid"),"taskstatus":$("#bdlLetter").attr("taskstatus")},
                            ]
                        }),
                        success: function (data) {
                            switch(data['code']){
                                case "0":
                                    alert("成功");
                                    //跳转 任务列表
                                    window.location.href = "./bdlSell_card.html";
                                break;
                                case "101":
                                    alert("失败");
                                break;
                                case "102":
                                    alert("参数不正确");
                                break;
                            }
                        },
                        error: function (data) {
                            alert('请求失败');
                        }
                    });
                })
                //查看订单
                $(".complete").on("click",function(){
                    window.location.href = "./paperlessOfficeSales/orderDetails.html?orderid="+data['bdlCard']['orderid']+"&employeeid="+data['bdlCard']['employeeid']+"";
                })
               break;
               //精品顾问加装精品
               case "19":
                    $(".bdl_task").html(returnTask(data['bdlCard']['cardtype'] || '')['message']);
                    $(".addPu").eq(4).hide();
                    $(".addPu").eq(3).hide();
                    $(".back").val("完成");
                    $(".complete").hide();
                    $(".bdl_Ltitler").eq("0").addClass("see");
                    $(".bdl_Ltitler").eq("1").addClass("seey");
                    $(".bdl_Ltitler").eq("2").addClass("ssure");
                    $(".bdl_Ltitler").eq("3").addClass("sure");
                    // var boolen = false;
                    $(".see").on("click",function(){
                        if($(".see").find(".okIcon").hasClass("ok")){
                            //如果发现 查看客户资料子任务完成  就跳转
                            window.location.href = "./paperlessOfficeSales/orderDetails.html?orderid="+data['bdlCard']['orderid']+"&employeeid="+data['bdlCard']['employeeid']+"";
                        }else{
                            //如果发现 查看客户资料子任务没有完成  就ajax 然后跳转
                            $.ajax({ 
                                type: "POST",
                                url: srcConfig.updateCardTaskList,
                                dataType: 'json',
                                contentType: "application/json",   //这个放开
                                data: JSON.stringify({
                                    "taskList":[
                                        {"taskid":$('.see').attr("taskid"),"taskstatus":$('.see').attr("taskstatus")}
                                    ]
                                }),
                                success: function (dataD) {
                                    switch(dataD['code']){
                                        case "0":
                                            alert("成功");
                                            //跳转 任务列表
                                            window.location.href = "./paperlessOfficeSales/orderDetails.html?orderid="+data['bdlCard']['orderid']+"&employeeid="+data['bdlCard']['employeeid']+"";
                                        break;
                                        case "101":
                                            alert("失败");
                                        break;
                                        case "102":
                                            alert("参数不正确");
                                        break;
                                    }
                                },
                                error: function (data) {
                                    alert('请求失败');
                                }
                            });
                        }
                    })
                    //预约时间
                    $(".seey").on("click",function(){
                        //判断如果有查看客户资料  就跳转页面  没有就去完成子任务
                        if($(".see").find(".okIcon").hasClass("ok")){
                            window.location.href = "./paperlessOfficeSales/orderDetails.html?orderid="+data['bdlCard']['orderid']+"&employeeid="+data['bdlCard']['employeeid']+"";                        
                        }else{
                            $.ajax({ 
                                type: "POST",
                                url: srcConfig.updateCardTaskList,
                                dataType: 'json',
                                contentType: "application/json",   //这个放开
                                data: JSON.stringify({
                                    "taskList":[
                                        {"taskid":$('.see').attr("taskid"),"taskstatus":$('.see').attr("taskstatus")}
                                    ]
                                }),
                                success: function (dataD) {
                                    switch(dataD['code']){
                                        case "0":
                                            alert("成功");
                                            //跳转 任务列表
                                            window.location.href = "./paperlessOfficeSales/orderDetails.html?orderid="+data['bdlCard']['orderid']+"&employeeid="+data['bdlCard']['employeeid']+"";
                                        break;
                                        case "101":
                                            alert("失败");
                                        break;
                                        case "102":
                                            alert("参数不正确");
                                        break;
                                    }
                                },
                                error: function (data) {
                                    alert('请求失败');
                                }
                            });
                        }
                    })
                    //与客户确认
                    $(".ssure").on("click",function(){
                        if($(".seey").find(".okIcon").hasClass("ok")){
                            window.location.href = "./paperlessOfficeSales/orderDetails.html?orderid="+data['bdlCard']['orderid']+"&employeeid="+data['bdlCard']['employeeid']+"";
                        }else{
                            alert("请先预约加装精品时间");
                            return false;
                        }
                    })
                    //为客户加装精品
                    $(".sure").on("click",function(){
                        if($(".seey").find(".okIcon").hasClass("ok")&&$(".ssure").find(".okIcon").hasClass("ok")){
                            window.location.href = "./paperlessOfficeSales/orderDetails.html?orderid="+data['bdlCard']['orderid']+"&employeeid="+data['bdlCard']['employeeid']+"";
                        }else{
                            alert("请完成前三条任务");
                            return false;
                        }
                    })
                    //点击完成
                    $(".back").on("click",function(){
                        $aja(data)
                        window.location.href = "./bdlSell_card.html";
                        //满足条件                            
                    })
               break;
               //验车专员 验车上牌
               case "20":
                    $(".bdl_task").html(returnTask(data['bdlCard']['cardtype'] || '')['message']);
                    $(".addPu").eq(3).hide();
                    $(".back").val("完成");
                    $(".complete").hide();
                    $(".bdl_Ltitler").eq("0").addClass("see");
                    $(".bdl_Ltitler").eq("1").addClass("see");
                    $(".bdl_Ltitler").eq("2").addClass("see");
                    $(".bdl_Ltitler").eq("3").addClass("seey");
                    $(".see").on("click",function(){
                    $.ajax({ 
                        type: "POST",
                        url: srcConfig.updateCardTaskList,
                        dataType: 'json',
                        contentType: "application/json",   //这个放开
                        data: JSON.stringify({
                            "taskList":[
                                {"taskid":$(".see").eq(0).attr("taskid"),"taskstatus":$(".see").eq(0).attr("taskstatus")},
                                {"taskid":$(".see").eq(1).attr("taskid"),"taskstatus":$(".see").eq(1).attr("taskstatus")},
                                {"taskid":$(".see").eq(2).attr("taskid"),"taskstatus":$(".see").eq(2).attr("taskstatus")},
                            ]
                        }),
                        success: function (data) {
                            switch(data['code']){
                                case "0":
                                    alert("成功");
                                    //跳转 任务列表
                                    window.location.href = "./paperlessOfficeSales/orderDetails.html?orderid="+data['bdlCard']['orderid']+"&employeeid="+data['bdlCard']['employeeid']+"";
                                break;
                                case "101":
                                    alert("失败");
                                break;
                                case "102":
                                    alert("参数不正确");
                                break;
                            }
                        },
                        error: function (data) {
                            alert('请求失败');
                        }
                        });
                    })
                    $(".back").on("click",function(){
                          if($(".see").eq(0).find(".okIcon").hasClass("ok") && $(".see").eq(1).find(".okIcon").hasClass("ok")&&$(".see").eq(2).find(".okIcon").hasClass("ok")){
                                $.ajax({ 
                                type: "POST",
                                url: srcConfig.updateCardTaskList,
                                dataType: 'json',
                                contentType: "application/json",   //这个放开
                                data: JSON.stringify({
                                    "taskList":[
                                        {"taskid":$(".seey").attr("taskid"),"taskstatus":$(".seey").attr("taskstatus")},
                                    ]
                                }),
                                success: function (data) {
                                    switch(data['code']){
                                        case "0":
                                            alert("办理成功");
                                            //跳转 任务列表
                                            window.location.href = "./bdlSell_card.html";
                                        break;
                                        case "101":
                                            alert("失败");
                                        break;
                                        case "102":
                                            alert("参数不正确");
                                        break;
                                    }
                                },
                                error: function (data) {
                                    alert('请求失败');
                                }
                            });
                          }else{
                              alert("请先查看客户");
                              return false;
                          }
                    })
               break;
               //验车专员 缴纳购置税
               case "21":
                    $(".bdl_task").html(returnTask(data['bdlCard']['cardtype'] || '')['message']);
                    $(".addPu").eq(1).hide();
                    $(".back").val("完成");
                    $(".complete").hide();
                    $(".bdl_Ltitler").eq("0").addClass("see");
                    $(".see").on("click",function(){
                        window.location.href = "./paperlessOfficeSales/orderDetails.html?orderid="+data['bdlCard']['orderid']+"&employeeid="+data['bdlCard']['employeeid']+"";
                    })
                    $(".back").on("click",function(){
                        $aja(data)
                        window.location.href = "./bdlSell_card.html";
                    })
               break;
               //验车专员 办理临牌
               case "22":
                    $(".bdl_task").html(returnTask(data['bdlCard']['cardtype'] || '')['message']);
                    $(".addPu").eq(2).hide();
                    $(".back").val("完成");
                    $(".complete").hide();
                    $(".bdl_Ltitler").eq("0").addClass("see");
                    $(".bdl_Ltitler").eq("1").addClass("see");
                    $(".bdl_Ltitler").eq("2").addClass("seey");
                    $(".see").on("click",function(){
                    $.ajax({ 
                        type: "POST",
                        url: srcConfig.updateCardTaskList,
                        dataType: 'json',
                        contentType: "application/json",   //这个放开
                        data: JSON.stringify({
                            "taskList":[
                                {"taskid":$(".see").eq(0).attr("taskid"),"taskstatus":$(".see").eq(0).attr("taskstatus")},
                                {"taskid":$(".see").eq(1).attr("taskid"),"taskstatus":$(".see").eq(1).attr("taskstatus")}
                            ]
                        }),
                        success: function (data) {
                            switch(data['code']){
                                case "0":
                                    alert("成功");
                                    //跳转 任务列表
                                   window.location.href = "./paperlessOfficeSales/orderDetails.html?orderid="+data['bdlCard']['orderid']+"&employeeid="+data['bdlCard']['employeeid']+"";
                                break;
                                case "101":
                                    alert("失败");
                                break;
                                case "102":
                                    alert("参数不正确");
                                break;
                            }
                        },
                        error: function (data) {
                            alert('请求失败');
                        }
                        });
                    })
                    $(".back").on("click",function(){
                        //以上两条子任务完成 
                        if($(".see").eq(0).find(".okIcon").hasClass("ok") && $(".see").eq(1).find(".okIcon").hasClass("ok")){
                            $.ajax({ 
                                type: "POST",
                                url: srcConfig.updateCardTaskList,
                                dataType: 'json',
                                contentType: "application/json",   //这个放开
                                data: JSON.stringify({
                                    "taskList":[
                                        {"taskid":$(".seey").attr("taskid"),"taskstatus":$(".seey").attr("taskstatus")},
                                    ]
                                }),
                                success: function (data) {
                                    switch(data['code']){
                                        case "0":
                                            alert("办理成功");
                                            //跳转 任务列表
                                            window.location.href = "./bdlSell_card.html";
                                        break;
                                        case "101":
                                            alert("失败");
                                        break;
                                        case "102":
                                            alert("参数不正确");
                                        break;
                                    }
                                },
                                error: function (data) {
                                    alert('请求失败');
                                }
                            });
                        }else{
                            alert("请先查看客户");
                            return false;
                        }
                    })
               break;
               //计划员 确认车辆排产 
               case "23":
               break;
               //计划员 确认车辆发送
               case "24":
               break;
               //计划员 确认车辆到港
               case "25":
               break;
               //计划员 确认车辆在途
               case "26":
               break;
               //计划员 确认车辆到店 
               case "27":
               break;
            //取消 软键盘 弹出
        }
   }
   //封装 相应的办理业务
   function returnTask(data){
        var object = {};
        switch(data){
            //销售顾问
            case '1':
                object['message'] = '办理购车';
                return object;
            break;
            //2--订单确认
            case "2":
                object['message'] = '办理订单确认';
                return object;
            break;
            //-二手车
            case "3":
                object['message'] = '办理二手车';
                return object;
            break;
            //金融分期
            case "4":
                object['message'] = '办理金融分期';
                return object;
            break;
            //车辆置换
            case "5":
                object['message'] = '办理车辆置换';
                return object;
            break;
            //车险
            case "6":
                object['message'] = '办理车险';
                return object;
            break;
            //验车上牌
            case "7":
                object['message'] = '办理验车上牌';
                return object;
            break;
            //精品
            case "8":
                object['message'] = '准备精品';
                return object;
            break;
            //
            case "9":
                object['message'] = '加装精品';
                return object;
            break;
            //
            case "10":
                object['message'] = "办理财务";
                return object;
            break;
            case "11":
                object['message'] = "已支付了全款XXXXX元,请确认";
                return object;
            break;
            case "12":
                object['message'] = "确认金融分期收款";
                return object;
            break;
            case "13":
                object['message'] = "确认二手车收款";
                return object;
            break;
            case "14":
                object['message'] = "办理车险";
                return object;
            break;
            case "15":
                object['message'] = "办理车辆置换";
                return object;
            break;
            case "16":
                object['message'] = "的二手车收购";
                return object;
            break;
            case "17":
                object['message'] = "办理金融分期";
                return object;
            break;
            case "18":
                object['message'] = "准备精品";
                return object;
            break;
            case "19":
                object['message'] = "加装精品";
                return object;
            break;
            case "20":
                object['message'] = "办理验车上牌";
                return object;
            break;
            case "21":
                object['message'] = "缴纳购置税";
                return object;
            break;
            case "22":
                object['message'] = "办理临牌";
                return object;
            break;
            case "23":
                object['message'] = "确认车辆排产";
                return object;
            break;
            case "24":
                object['message'] = "确认车辆发送";
                return object;
            break;
            case "25":
                object['message'] = "确认车辆到港";
                return object;
            break;
             case "26":
                object['message'] = "确认车辆在途";
                return object;
            break;
             case "27":
                object['message'] = "确认车辆到店";
                return object;
            break;
        }
   }
   //封装状态return 
   function $status(status){
        switch(status){
            case "1":
                return 'no';
            break;
            case "2":
                return 'no';
            break;
            case "3":
                return 'ok';
            break;
        }
   }
    //点击按钮
    var $bdl_push = $(".bdl_push");
    $(".bdl_boxS").off("click").on("click",function(event){
        event.stopPropagation();
        if($('.bdl_push').css("display")=='none'){
            $('.bdl_push').show(200);
        }else{
            $('.bdl_push').hide(200);
        }
    })
    //设置点击其他 隐藏日历
    $(document).on('click', function (event) {  
        event.preventDefault();
        event.stopPropagation();
        if(event.target.className!='tim'){
            if($('.calendar').css("display")=='table'){
               $('.calendar').css("display","none");
            }
        }
        if(event.target.className!='bdl_boxS'){
            if($('.bdl_push').css("display")=='block'){
               $('.bdl_push').hide(200);
            }
        }
    }); 
    
    // $aja(data['data']);

    //返回订单  更新时间
    function $aja(data){
            if($(".tim").val()!=''){
                var dataDa = {};
                dataDa['cardid'] = Number(object['cardid']);
                dataDa['enddate'] = $(".tim").val() || '';
                dataDa['employeeid'] = data['bdlCard']['employeeid'];
                console.log(dataDa)
                $.ajax({ 
                    type: "POST",
                    url: srcConfig.updateCardTime,
                    dataType: 'json',
                    contentType: "application/json",   //这个放开
                    data: JSON.stringify(dataDa),
                    success: function (data) {
                        switch(data['code']){
                            case "0":   
                                alert("预约时间设置成功");
                            break;
                            case "101": 
                                alert("失败");
                            break;
                            case "102":
                                alert("参数不正确");
                            break;
                        }
                    },
                    error: function (data) {
                        alert('请求失败');
                    }
                });
            } 
    }
    function bilPush(data){
        $(".bdl_CarSearch").on("click",function(){
            alert("./bdlSell_search.html?employeeId="+data['bdlCard']['employeeid']+"");
            window.location.href = "./bdlSell_search.html?employeeId="+data['bdlCard']['employeeid']+"";
        });
        $(".bdl_CarAdd").on("click",function(){
            window.location.href = "./bdlSell_add.html";
        });
        $(".bdl_CarMain").on("click",function(){
            window.location.href = "./bdlSell_card.html";
        });
    }

    $(".tim").focus(function(event){
        event.stopPropagation();
        document.activeElement.blur();
    });
}(jQuery))