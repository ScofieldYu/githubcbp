$(function(){
    var session = window.sessionStorage;
    var url = window.location.href; //获取当前页url
    // var data = {code: '0',
    //  message: '登录成功',
    //  data: { employeeid: '1',
    //  phonenumber: '15910784374',
    //  name: '余志诚',
    //  wxname: '余志诚',
    //  token: '08a0e52aaab6c049f5e7c5479278c0b8' ,
    //  accounttype:'2'} }
    //   session.setItem("data",JSON.stringify(data))
    var tokenQuery = url.queryURLParameter();

    var employeeId = JSON.parse(session.getItem("data"))['data']['employeeid'] || '';  //打开
    var sendData = {};
    var customerid = tokenQuery['customerid'] || '';
    // sendData['employeeid'] = employeeId;   //打开
    var one = false,two = false;
    //初始化
    
    var numBBNumber = session.getItem("numBBNumber") || '';
    var appearance = session.getItem("appearance") || '';
    customerid!=='' ? ($(".txtMission").css("color","#000")):($(".txtMission").css("color","#999"));
    customerid!=='' ? $(".customerInput").val("完成"):$(".customerInput").val("");
    $(".orderFormInput").val()==''?$(".txtOrderForm").css("color","#999"):'';
    $(".orderFormInput").val(numBBNumber);
    $(".missionInput").val(appearance);
    
    numBBNumber ? ($(".inputFile").css("background","#3764be"),$(".txtOrderForm").css("color","#000"),one=true) : ($(".inputFile").css("background","#8c8c91"),$(".txtOrderForm").css("color","#999"),one=false);
    if($(".missionInput").val()=="购车下单"){
       $(".inputFile").css("background","#3764be");
       $(".orderFormMain").on("click",function(){
           return false;
       })
    }
    //客户
    $(".customerMain").on("click",function(){
        window.location.href = "./bdlSell_message.html";
    })  
    
    //任务
    $(".missionMain").on("click",function(){
        if(customerid!==''){
            //选择外观按钮
            $(".missionMain").click(function () {
                $('.carAppearance').show()
                    .removeClass('fadeOutRight')
                    .addClass('fadeInRight animated')   //淡入效果
                    .find('li').unbind('click')  //防止事件重复绑定
                    .click(function () { //绑定选择外观事件
                        var $this = $(this);
                        appearance = $this.text();//把所选外观赋值暂存
                        session.setItem("appearance",appearance);
                        appearance == "购车下单" ? ($(".inputFile").css("background","#3764be"),$(".txtOrderForm").css("color","#999"),$(".orderFormInput").val(''),two=true) : ($(".inputFile").css("background","#8c8c91"),$(".txtOrderForm").css("color","#000"),two=false);
                        if(appearance == "购车下单"){
                            session.getItem("numBBNumber")?session.removeItem("numBBNumber") : '' ;
                            one = false;
                        }
                        $(".missionInput").val(appearance);
                        $('.carAppearance').removeClass('fadeInRight').addClass('fadeOutRight');
                    });
            });
        }else{
            return false;
        }
    })

    //订单  
    $(".orderFormMain").on("click",function(){
        if($(".missionInput").val()!=''){
            if($(".missionInput").val()=="购车下单"){
                return false;
            }else{
                window.location.href = "./bdlSell_listAll.html?customerid="+customerid+"";
            }
        }
    })
    
    //完成按钮
    $(".inputFile").on("click",function(){
       //购车下单　或者　有订单　就可以点击完成
       if(two || one){
            var dataData = {};
            dataData['employeeid'] = Number(employeeId);
            dataData['cardtype'] = '1';
            dataData['customerid'] = Number(customerid);
            dataData['orderid'] = tokenQuery['orderid'];
            dataData['customername'] = session.getItem("customerName");
            
            $.ajax({ 
                type: "POST",
                url: srcConfig.saveCard,
                dataType: 'json',
                contentType: "application/json",   //这个放开
                data: JSON.stringify(dataData),
                success: function (data) {
                    switch(data['code']){
                        case "0":
                            alert("创建成功");
                            //清除存储
                            session.removeItem("appearance");
                            session.removeItem("customername");
                            session.removeItem("numBBNumber");
                            window.location.href = "./bdlSell_card.html";
                        break;
                        case "101":
                            alert("创建失败");
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
           return false;
       }
    })
}(jQuery));