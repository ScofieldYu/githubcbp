$(function(){
    // var url = window.location.href; //获取当前页url
    // var tokenQuery = url.queryURLParameter();

    //身份证正则
    var session = window.sessionStorage;
    var myreg=/^[1][3,4,5,7,8][0-9]{9}$/;  
    var isIDCard1=/^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$/; 
    var isIDCard2=/^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}([0-9]|X)$/;
    //通过手机号获取 信息
    $(".btn").on("click",function(){
        var $phone = $(".fg");
        if (!myreg.test($phone.val())) {  
            alert("你输入的手机号格式不正确");
            $phone.val('');
            return false;  
        } else {
            var sendData = {};
            sendData['phonenumber'] = $phone.val();
            console.log(sendData)
            $.ajax({ 
                type: "POST",
                url: srcConfig.getCustomerByPhone,
                // url:"http://localhost:8080",
                dataType: 'json',
                contentType: "application/json",
                data: JSON.stringify(sendData),
                success: function (data) {
                    switch(data['code']){
                    case "0":
                        alert(data['data']);
                        console.log(data.data)
                        $(".boxTan").fadeOut();
                        binding(data['data'],$phone.val());
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
    $(".leave").on("click",function(){
        
        history.go(-1);   
    })
    //绑定数据
    function binding(data,phone){
       var strObt = '';
        $.each(data,function(ind,val){
            if(phone == val['customer']['phonenumber']){
                 if(data.length=="1"){
                    if(val['customer']['customername']==''&&val['customer']['idcardnum']==''){
                        $("#nameOr").val(val['customer']['customername']);
                        $("#idCard").val(val['customer']['idcardnum']);
                    }else{
                        $("#nameOr").val(val['customer']['customername']).attr("disabled","disabled"); ;
                        $("#idCard").val(val['customer']['idcardnum']).attr("disabled","disabled"); ;
                    }
                    $(".phoneBB").val(phone).attr("disabled","disabled"); 
                 }else{
                     $(".boxChange").show();
                     strObt+= `<div class="pp"><input type="radio" class="radio" name="name"> <p class="text">${val['customer']['customername']}</p><p class="card">${val['customer']['idcardnum']}</p> </div>`
                     
                 }
                // $(".bdl_sec").attr("message","true")
                // $(".kk").hide();
               
                // if(val['customer']['customername']!=''){
                //     // $(".select-menu").show();
                    
                //     var opt1 = '',opt2 = '';
                //     $.each(data,function(index,value){
                //         if(val['customer']['customername']!=''){
                //             opt1+= `<li>${value['customer']['customername']}</li>`
                //         }
                //         if(val['customer']['idcardnum']!=''){
                //             opt2+= `<li>${value['customer']['idcardnum']}</li>`
                //         }
                //     })
                //     $(".opt1").html(opt1);
                //     $(".opt2").html(opt2);
                //     $(".opt1").find("li").eq(0).addClass('select-this');
                //     $(".opt2").find("li").eq(0).addClass('select-this');
                //     $(".optInput1").val($(".opt1").find("li").eq(0).html())
                //     $(".optInput2").val($(".opt2").find("li").eq(0).html())
                //     $(".phoneBB").val(phone);
                // }else{
                //     $(".pp").show();
                // }
            }
        })
        if(data == ''){
            console.log(phone)
            $(".bdl_sec").attr("message","false");

            $(".phoneBB").val(phone).attr("disabled","disabled"); 

        }
        $(".change").html(strObt);
        $(".btnOk").on("click",function(){
            
           var radio_value = '';
           var card = '';
           radio_value = $('.radio').filter(':checked').siblings(".text").html();
           card = $('.radio').filter(':checked').siblings(".card").html();
           $(".boxChange").hide();
           if(radio_value=='' && card==''){
                $("#nameOr").val(radio_value); 
                $("#idCard").val(card); 
           }else{
                $("#nameOr").val(radio_value).attr("disabled","disabled"); 
                $("#idCard").val(card).attr("disabled","disabled"); 
           }
           
           $(".phoneBB").val(phone).attr("disabled","disabled"); 
        })



         //完成创建卡片  
        $(".inputFile").on("click",function(){
            var idC = false,nameC=false;
            
            var $phone1 = $(".phoneBB").val();  //手机号
            var $name = $("#nameOr").val(); //姓名
            var $idCard =$("#idCard").val();//身份证
                if($("#nameOr").val()==''){
                    alert("请输入姓名");
                    return false;
                }
                if(isIDCard1.test($idCard) || isIDCard2.test($idCard)){
                    idC = true;
                }else{
                    alert("身份证输入不正确");
                }
                if (myreg.test($phone1)) {  
                    nameC = true;
                } else {
                    alert("你输入的手机号格式不正确");
                }
                if(idC && nameC){
                    var sendData =  {},$url = srcConfig.selfauth;
                    sendData['phonenumber'] = $phone1;//手机号
                    sendData['customername'] = $name;//客户姓名
                    sendData['idcardnum'] = $idCard//证件号
                
                    $ajax($url,sendData);
                }   
            // if($(".bdl_sec").attr("message")=="false") {
            //     var idC = false,nameC = false;
            //     var $name = $("#nameOr").val();
            //     var $idCard =$("#idCard").val();
            //     if(isIDCard1.test($idCard) || isIDCard2.test($idCard)){
            //         idC = true;
            //     }else{
            //         alert("身份证输入不正确");
            //     }
            //     if (myreg.test($phone1)) {  
            //         nameC = true;
            //     } else {
            //         alert("你输入的手机号格式不正确");
            //     }
            //     if(idC && nameC){
            //         var sendData =  {},$url = srcConfig.selfauth;
            //         sendData['phonenumber'] = $phone1;//手机号
            //         sendData['customername'] = $name;//客户姓名
            //         sendData['idcardnum'] = $idCard//证件号
            //         console.log( srcConfig.selfauth)
            //         $ajax($url,sendData);
            //     }
            // }else{
            //     var $name =$(".optInput1").val(); 
            //     var $idCard = $(".optInput2").val();
            //     var sendData =  {},$url = srcConfig.selfauth;
            //     sendData['phonenumber'] = $phone1;//手机号
            //     sendData['customername'] = $name;//客户姓名
            //     sendData['idcardnum'] = $idCard//证件号
            //     if (myreg.test($phone1)) {      
            //         //ajax
            //         console.log(sendData, srcConfig.selfauth)
            //         $ajax($url,sendData);
            //     } else {
            //         alert("你输入的手机号格式不正确");
            //     }
            // }
        })
    }
    //绑定ajax
   function $ajax($url,sendData){
       console.log(sendData)
     $.ajax({ 
            type: "POST",
            url: $url,
            dataType: 'json',
            contentType: "application/json",
            data: JSON.stringify(sendData),
            success: function (data) {
                switch(data['code']){
                    case "0":
                        alert("成功");
                        alert(data.data)
                        session.setItem("customerName",$("#nameOr").val());
                        window.location.href = "./bdlSell_add.html?customerid="+data['data']+"";
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
   //13661357786
}(jQuery))