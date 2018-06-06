$(function(){
    
    var url = window.location.href; //获取当前页url
   
    var tokenQuery = url.queryURLParameter();
    var orderid = tokenQuery['orderid'];
    var employeeid = tokenQuery['employeeid'];
    //刚进入获取所有的银行
    $ajaxMessage();
    $ajax();
    $keyDown();
    function $ajax(){
        var sendData = {};
        $.ajax({ 
                async:false,
                type: "POST",
                // url:"http://192.168.206.49:8080/blchina-cbp/finance/getAllPeriodList",
                url: srcConfig.getAllPeriodList,
                dataType: 'json',
                contentType: "application/json",
                success: function (data) {
                    switch(data['code']){
                    case "0":
                        alert("成功");
                        alert(JSON.stringify(data['data']));
                        //绑定银行 列表
                        blings(data['data']);
                        $click();
                        $leave();
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
    function $ajaxMessage(){
        var dataF = {};
        dataF['orderid'] = Number(orderid);
        $.ajax({ 
                async:false,
                type: "POST",
                // url:"http://192.168.206.49:8080/blchina-cbp/finance/selectFinanceOrderByOrderId",
                url: srcConfig.selectFinanceOrderByOrderId,
                 data: JSON.stringify(dataF),
                dataType: 'json',
                contentType: "application/json",
                success: function (data) {
                    switch(data['code']){
                    case "0":
                        alert("成功");
                        console.log(data['data']);
                        blingsFwo(data['data']);
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
    function blingsFwo(data){
        // "{
        // ""totalloan"":""999"",
        // ""fee"":""1"",
        // ""bankname"":""中国银行"",
        // ""periodnum"":""3"",
        // ""firstpay"":""12"",
        // ""bankid"":""1"",
        // ""periodid"":""7"",
        // ""orderid"":155
        // }"
        var totalloan = data['totalloan'] || '',//贷款总额
            bankname = data['bankname'] || '', //银行名称
            fee = data['fee'] || '', //手续费
            periodnum = data['periodnum'] || '',//贷款期数
            firstpay = data['firstpay'] ||　'';//首付
            $(".missionInput1").val(bankname);
            $(".loanInput").val(totalloan);
            $(".downPaymentInput").val(firstpay);
            $(".missionInput2").val(periodnum);
            $(".poundageInput").val(fee);
    }
    function blings(data){
        console.log(data)
        var result = '';
        $.each(data,function(index,val){
            result += `
                     <li bankid=${val['bankid']} bdlPeriodList=${JSON.stringify(val['bdlPeriodList'])}>${val['bankname']}</li>
                   `;
        })
        $(".bankList").html(result);
    }
    $(".missionMain1").on("click",function(){
        //选择外观按钮
        var html = '';
        $('.carAppearanceBank').show()
            .removeClass('fadeOutRight')
            .addClass('fadeInRight animated')   //淡入效果
            .find('li').unbind('click')  //防止事件重复绑定
            .click(function () { //绑定选择外观事件
                var $this = $(this);
                appearance = $this.text();//把所选外观赋值暂存
                bankid = $this.attr("bankid"); // 获取他的bankid
                //              
                $.each(JSON.parse($this.attr('bdlperiodlist')),function(ind,value){
                    html += `
                        <li periodid=${value['periodid']}>${value['periodvalue']+"期"}</li>
                    `;
                })
                $('.periodsOdd').html(html); 
                $(".missionInput1").val(appearance).attr("bankid",bankid);
                $('.carAppearanceBank').removeClass('fadeInRight').addClass('fadeOutRight');
                
                $(".missionInput2").val('');
                $missionMain2();
            });
            
    })
    function $missionMain2(){
        $(".missionMain2").on("click",function(){
            //选择外观按钮
            $('.carAppearancePeriods').show()
            .removeClass('fadeOutRight')
            .addClass('fadeInRight animated')   //淡入效果
            .find('li').unbind('click')  //防止事件重复绑定
            .click(function () { //绑定选择外观事件
                var $this = $(this);
                appearance = $this.text();//把所选外观赋值暂存
                $(".missionInput2").val(appearance);
                $(".missionInput2").attr("periodid",$this.attr("periodid"));
                $('.carAppearancePeriods').removeClass('fadeInRight').addClass('fadeOutRight');
            });
        })
    }
    // 完成
    function $click(){
         $(".completeBtn").unbind("click").bind("click",function(){
             var $missionInput1 = $(".missionInput1").attr("bankid") || '',
                 $loanInput = $public($(".loanInput")),
                 $missionInputName =$(".missionInput1").val(),
                 $downPaymentInput = $public($(".downPaymentInput")),
                 $missionInput2 = $(".missionInput2").val(),
                 $poundageInput = $public($(".poundageInput"));
                 
            // $(".loanInput").val()?'':function(){alert("请填写贷款总额");return false;}() ;
            // $(".downPaymentInput").val()?'':alert("请填写首付");
            // $(".poundageInput").val()?'':alert("请填写手续费");
            if($missionInput1 && $loanInput && $downPaymentInput && $missionInput2 && $poundageInput){
                console.log({
                            'totalloan':Number($loanInput),
                            'bankname':$missionInputName,
                            'fee':$poundageInput,
                            'bankid':$missionInput1,
                            'periodnum':$missionInput2,
                            'firstpay':$downPaymentInput,
                            'periodid':$(".missionInput2").attr("periodid")
                        })
                $.ajax({ 
                        type: "POST",
                        url: srcConfig.insertfinanceOrder,
                        // url:"http://192.168.206.49:8080/blchina-cbp/finance/insertfinanceOrder",
                        dataType: 'json',
                        contentType: "application/json",
                        data: JSON.stringify({
                            'totalloan':Number($loanInput), //贷款总额
                            'bankname':$missionInputName,   //银行名称
                            'fee':$poundageInput,           //手续费·
                            'bankid':$missionInput1,        //银行ID
                            'periodnum':$missionInput2,     //贷款期数
                            'firstpay':$downPaymentInput,   //贷款首付
                            'periodid':$(".missionInput2").attr("periodid"),  //分期ID
                            'orderid':Number(orderid) 
                        }),
                        success: function (data) {
                                switch(data['code']){
                                case "0":
                                    alert("成功");
                                    window.location.href = "./paperlessOfficeSales/orderDetails.html?orderid="+orderid+"&employeeid="+employeeid+"";
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
                alert("请把信息填写完整");
                return false;
            }
        })
    }
    //后退
    function $leave(){
        $(".leave").on("click",function(){
            history.go(-1);            
        })
    }
    //键盘事件 自动转金额
    function $keyDown(){

        document.querySelector(".loanInput").addEventListener("blur", function(event){
            var $this = $(this);
            var $val = $this.val().split(",").join('');
            $this.val(formatNum($val));
        }, false);

        document.querySelector(".downPaymentInput").addEventListener("blur", function(event){
            var $this = $(this);
            var $val = $this.val().split(",").join('');
            $this.val(formatNum($val));
        }, false);

        document.querySelector(".poundageInput").addEventListener("blur", function(event){
            var $this = $(this);
            var $val = $this.val().split(",").join('');
            $this.val(formatNum($val));
        }, false);

    }
    //公共
    function $public(dom){
        var str = '';
        str =  Number(dom.val().split(",").join('').split('.')[1])>0?
        dom.val().split(",").join('').split('.')[0] +"."+ dom.val().split(",").join('').split('.')[1]:
        dom.val().split(",").join('').split('.')[0];
        return str;
    }
    // 封装金额  
    function formatNum(str){
      
        var newStr = "";
        var count = 0;
 
        if(str.indexOf(".")==-1){
        if(str!=''){
            for(var i=str.length-1;i>=0;i--){
                if(count % 3 == 0 && count != 0){  
                    newStr = str.charAt(i) + "," + newStr;
                }else{                  
                    newStr = str.charAt(i) + newStr;
                }
                    count++;
            }
            str = newStr + ".00"; //自动补小数点后两位
            return str;
        }
    }else{
        if(str!=''){
            for(var i = str.indexOf(".")-1;i>=0;i--){
                if(count % 3 == 0 && count != 0){
                    newStr = str.charAt(i) + "," + newStr;
                }else{
                    newStr = str.charAt(i) + newStr; //逐个字符相接起来
                }
                    count++;
            }
                str = newStr + (str + "00").substr((str + "00").indexOf("."),3);
                return str;
            }
        }
    }
    //聚焦 
    $(".input").on("focus",function(){
    　　 $('body').height($('body')[0].clientHeight);
    })
}(jQuery))