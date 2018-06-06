$(function(){
    var url = window.location.href; //获取当前页url
    var tokenQuery = url.queryURLParameter();
    $keyDown();
    //点击 添加按钮 弹出框显示 
    $(".add").on("click",function(){
        if($(".box_main").css("display") == "none"){
            $(".textTT").html('');
            $(".inputTT").val('');
            $(".box_main").fadeIn();
        }else{
            $(".box_main").fadeOut();
        }
    })
    //隐藏box
    $(".leave").on("click",function(){
        $(".box_main").fadeOut();
    })
    //聚焦显示 
    // $ONBLUR();
    // function $ONBLUR(){
    //     $(document).on('click', function (event) {  
    //         event.preventDefault();
    //         event.stopPropagation();
    //         if(event.target.className!='textTT'){
    //             if($('.shopList').css("display")=='block'){
    //                 $('.shopList').css("display","none");
    //             }
    //             // $(".nameJ").removeClass("nameJAdd");
    //         }
    //     }); 
    // }
    //封装 按键 查询  
    function $keyDown(){
        $(".listMessage").html('');
        $(".inputTT").off("keyup").on("keyup",function(){
             var senddata = {};
             var boutiqueName = $(".inputTT").val();
             senddata['boutiqueid'] = boutiqueName;
            //  senddata['orderid'] = tokenQuery['orderid'];
             console.log(senddata);
             $.ajax({
                    type: "POST",
                    // url:"http://192.168.206.49:8080/blchina-cbp/boutique/getBoutiqueListByBoutiqueId",
                    url: srcConfig.getBoutiqueListByBoutiqueId, //解开 
                    dataType: 'json',
                    contentType: "application/json", //解开 
                    data: JSON.stringify(senddata),
                    success: function (data) {
                        switch(data['code']){
                            case "0":
                            // alert("成功");
                            console.log(data['data']);
                            bdling(data.data) //绑定
                            break;
                            case "100":
                            alert("已经超过预约时间不能设置");
                            break;
                            case '101':
                            alert("失败");
                            break;
                            case "102":
                            alert("参数不正确");
                            break;
                        }
                    },
                    error: function (data1) {
                        console.log('请求失败');
                    }
             })
        })
    }
    //获取到就绑定
    function bdling(data){
        var $boutiqueName = data['boutiquename'] || '';
        var $monHave = data['stock'] || '';
        $(".monJia").html($boutiqueName);
        $(".monHave").html($monHave);
        if(data['stock'] == "0"){
            $(".monHave").css("color","rgb(204,0,0)");
        }
        // $(".nameJ").addClass("nameJAdd");
        // $(".shopList").show();
        //渲染 商品列表
        // var result = '';
        // $.each(data,function(index,val){
        // result += `<li>
        //             <span class="listAdd">${val['boutiqueName'] || ''}</span>
        //             <span class="listNum">${val['serialNumber'] || ''}</span>
        //             <span class="listMon">${"￥"+val['boutiquePrice'] || ''}</span>
        //            </li>`
        // })
        // $('.listUl').html(result);

        //选中商品
        // $(".listUl li").off("click").on("click",function(event){
            
            // event.stopPropagation();  
            // var $boutiqueName = $(this).find(".listAdd").html();
            // var $boutiquePrice = $(this).find(".listMon").html();
            // var $serialNumber = $(this).find(".listNum").html();
            // $(".inputTT").val($boutiqueName);
            // $(".numBian").html($serialNumber);
            
            // $(this).css({"background":"#f2f2f2"}).siblings("li").css({"background":"#fff"});
            // $('.shopList').hide();
            // $(".nameJ").removeClass("nameJAdd");
        // })
        //确定商品
        $(".sure").off("click").on("click",function(){
            if($(".monHave").html()=="0"){
                alert("库存为空");
                $(".inputTT").val("");
                $(".monJia").html("");
                $(".monHave").html("");
                return false;
            }else{
                if($(".inputTT").val()==""){
                    alert("编号不能为空");
                    return false;
                }else if($(".monHave").html()==''){
                    alert("没有此精品");
                    $(".inputTT").val("");
                    return false;
                }else{ 
                    var boutiqueName = $(".inputTT").val() || '',  //编号
                        boutiquePrice = $(".monJia").html() || ''; //名称
                        stock = $(".monHave").html() || '';        //库存
                        var sendDate = {};
                        sendDate['boutiquename'] = boutiquePrice;
                        sendDate['serialnumber'] = boutiqueName;
                        sendDate['stock'] = stock;
                        sendDate['orderid'] = tokenQuery['orderid'];
                    var dataList = '';
                    console.log(sendDate)
                    $.ajax({
                            type: "POST",
                            // url:"http://192.168.206.49:8080/blchina-cbp/boutique/insertBoutiqueOrder",
                            url: srcConfig.insertBoutiqueOrder, //解开 
                            dataType: 'json',
                            contentType: "application/json", //解开 
                            data: JSON.stringify(sendDate),
                            success: function (data) {
                                switch(data['code']){
                                    case "0":
                                         alert("成功");
                                          dataList = `
                                                    <div class="listMon">
                                                        <span class="lon">${boutiqueName}</span>
                                                        <span class="mon">${boutiquePrice}</span>
                                                    </div>
                                                    `;
                                        $(".listMessage").append(dataList);
                                        $(".box_main").fadeOut();
                                    break;
                                    case '101':
                                    alert("失败");
                                    break;
                                    case "102":
                                    alert("参数不正确");
                                    break;
                                }
                            },
                            error: function (data1) {
                                console.log('请求失败');
                            }
                    })
                   
                }  
            }
           
            // var colo=$(".listMessage").find(".colo");
            //循环遍历价格
            // $.each(colo,function(ind,val){
            //         console.log($(val).html().split("￥")[1]);
            //         stry += Number($(val).html().split("￥")[1]);
            // })
            // stry!=0?$(".money").show():$(".money").hide();
            // //总价 1000 9,999-1000
            // //  分割金额
            // $(".coloR").html("￥"+formatNum(String(stry)));
        })

    }
    //返回
    $(".back").on("click",function(){
         history.go(-1);   
    })
    // 封装金额  
    // function formatNum(str){
    //     var newStr = "";
    //     var count = 0;
    // if(str.indexOf(".")==-1){
    //     for(var i=str.length-1;i>=0;i--){
    //         if(count % 3 == 0 && count != 0){
    //             newStr = str.charAt(i) + "," + newStr;
    //         }else{
    //             newStr = str.charAt(i) + newStr;
    //         }
    //             count++;
    //     }
    //         str = newStr + ".00"; //自动补小数点后两位
    //         // console.log(str)
    //         return str;
    // }else{
    //     for(var i = str.indexOf(".")-1;i>=0;i--){
    //         if(count % 3 == 0 && count != 0){
    //             newStr = str.charAt(i) + "," + newStr;
    //         }else{
    //             newStr = str.charAt(i) + newStr; //逐个字符相接起来
    //         }
    //             count++;
    //     }
    //         str = newStr + (str + "00").substr((str + "00").indexOf("."),3);
    //         // console.log(str)
    //         return str;s
    //     }
    // }
}(jQuery));