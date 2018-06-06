$(function(){
  var url = window.location.href; //获取当前页url

  var tokenQuery = url.queryURLParameter();
  
  var orderid = tokenQuery['orderid'];
  //回显
  bling();
  function bling(){
    $.ajax({ 
            type: "POST",
            url: srcConfig.getCheckCarInfoByOrderid,
            // url:"http://192.168.206.49:8080/blchina-cbp/checkCarNum/getCheckCarInfoByOrderid",
            dataType: 'json',
            contentType: "application/json",
            data: JSON.stringify({
                'orderid':orderid
            }),
            success: function (data) {
                switch(data['code']){
                case "0":
                    alert("成功");
                    console.log(data['data'])
                    list(data['data']);
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
  //绑定
  function list(data){
     var buytax = data[0]['buytax']||'';//代办购置税
     var tempplate = data[0]['tempplate']||'';//代办临牌
     var checkcar = data[0]['checkcar']||'';//代办验车上牌
     var chooseType = data[0]['chooseType'] || '';//选号类型
     buytax == '1'?$(".tarSwitch").eq(0).addClass("on"):$("#issign1").removeClass("on");
     tempplate == "1"?$(".tarSwitch").eq(1).addClass("on"):$("#issign2").removeClass("on");
     checkcar == "1"?$(".tarSwitch").eq(2).addClass("on"):$("#issign3").removeClass("on");
     $(".missionInput").val(chooseType);
  }
  //开关按钮
  $("#issign1").off("click").on("click",function(){
      $(this).toggleClass('on');
  });
  $("#issign2").off("click").on("click",function(){
      $(this).toggleClass('on');
  });
  $("#issign3").off("click").on("click",function(){
      $(this).toggleClass('on');
  });
  $(".missionMain").on("click",function(){
        //选择外观按钮
        $('.carAppearance').show()
            .removeClass('fadeOutRight')
            .addClass('fadeInRight animated')   //淡入效果
            .find('li').unbind('click')  //防止事件重复绑定
            .click(function () { //绑定选择外观事件
                var $this = $(this);
                appearance = $this.text();//把所选外观赋值暂存
                $(".missionInput").val(appearance);
                $('.carAppearance').removeClass('fadeInRight').addClass('fadeOutRight');
            });
    })
    //回退
    $(".leave").on("click",function(){
        history.go(-1);   
    })
    //发起ajax
    $(".completeBtn").on("click",function(){
        var sendData = {};
        sendData['orderid'] = tokenQuery['orderid'];
        sendData['buytax'] = $("#issign1").hasClass("on")? '1':'0';
        sendData['tempplate'] = $("#issign2").hasClass("on")? '1':'0';
        sendData['checkcar'] = $("#issign3").hasClass("on")? '1':'0';
        if($(".missionInput").val()=="保留原号"){
            sendData['chooseType'] = '保留原号';
        }else if($(".missionInput").val()=="50选一"){
            sendData['chooseType'] = '50选一';
        }else{
            sendData['chooseType'] = '自编号牌';
        }
        console.log(sendData)
        $.ajax({ 
                type: "POST",
                url: srcConfig.insertCheckCarNumOrder,
                // url:"http://192.168.206.49:8080/blchina-cbp/checkCarNum/insertCheckCarNumOrder",
                dataType: 'json',
                contentType: "application/json",
                data: JSON.stringify(sendData),
                success: function (data) {
                    switch(data['code']){
                    case "0":
                        alert("成功");
                        console.log(data['data']);
                        //进入订单详情   不确定
                         window.location.href = "./paperlessOfficeSales/orderDetails.html?orderid="+orderid+"";
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
    
}(jQuery));