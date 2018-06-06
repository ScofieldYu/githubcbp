$(function(){

     //原型上添加 方法  长按事件
    $.fn.longPress = function(fn) {
        var timeout = undefined;
        var $this = this;
        for(var i = 0;i<$this.length;i++){
            $this[i].addEventListener('touchstart', function(event) {
                var that = this;
                timeout = setTimeout(function(){    
                    fn(that);
                }, 2000);  //长按时间超过2000ms，则执行传入的方法
            }, false);
            $this[i].addEventListener('touchend', function(event) {
                clearTimeout(timeout);  //长按时间少于2000ms，不会执行传入的方法
            }, false);
        }
    }; 
    var stroge = window.localStorage;
    var session = window.sessionStorage;
    // var se = {};
    //  se={code: '0',
    //  message: '登录成功',
    //  data: { 
    //  employeeid: '20488',
    //  phonenumber: '13120370722',
    //  name: '余志诚',
    //  wxname: '余志诚',
    //  token:'5bf2495f62a1a3ba714dc408aef56193',
    //  accounttype:'10001018'}};
    //  session.setItem('data',JSON.stringify(se))
     //验车专员　10001018

    // tab 切换
    // 长按删除时间 调用 *   （放在 ajax 数据请求成功后）
    var url = window.location.href; //获取当前页urls
    var tokenQuery = url.queryURLParameter();
    
    // 右上多选 栏
    if(!session.getItem("data")){  
              var object = {};
         if(tokenQuery['code']){
             object['code'] = tokenQuery['code'];
             $ajax(srcConfig.getEmployeeidByCodeBase,object);
         }else{
              var data = {};
              data['data'] = {};
              data['data']['employeeid'] = tokenQuery['employeeid'];
              data['data']['accounttype'] = tokenQuery['accounttype'];
              session.setItem("data",JSON.stringify(data));
         }
    }else{
         var dataData = JSON.parse(session.getItem("data"))['data'];
         clickBar();
         $num()
         if(session.getItem("name")){
            var booler = true;
            var tab5LoadEnd = false;
            var currentPage5 = 1;
            
            $(".bdl_push").hide().attr("display","false");
            //跳到 全部页面
            $(".tabtitle").find("li").eq(3).attr('class',"tabhover").siblings('li').attr('class','taba');
            $('.tabcontent').eq(3).show(200).siblings('.tabcontent').hide();
            
            //先进入为空
            $("#tabcontent5").html("");
            $("#tabcontent5").show().siblings(".tabcontent").hide();
            $("#tabcontent5").addClass("checkAll").siblings().removeClass("checkAll");
            //渲染数据
                   
            var dropload = $('.maintab').dropload({
            scrollArea : window,
            loadDownFn : function(me){
              
                    var itemData = {};
                    var cardStatus = '0';
                    itemData['currentPage'] = currentPage5;
                    itemData['cardStatus'] = cardStatus;
                    itemData['customerName'] = session.getItem("name");
                    //获取input 上自定义属性 employeeid
                    itemData['employeeId'] = dataData['employeeid'];
                    //删除 多余旋转按钮 dropload-down
                    console.log(itemData);
                $.ajax({
                    type: 'post',
                    url: srcConfig.searchCardList,
                    // url:"http://192.168.206.188:8889/blchina-cbp/card/searchCardList",
                    dataType: 'json',
                    contentType: "application/json",
                    data: JSON.stringify(itemData),
                    success: function(dataDa){
                        console.log(dataDa)
                        switch(dataDa.code){
                             case "0":
                                $(".text").eq(3).html(dataDa['data']['totalRecord']);
                                var result = '';
                                
                                // 条数
                                if(dataDa['data']['datas']==''){
                                    session.removeItem("name"); 
                                    biling(dataData);
                                    clickGet(dataData);
                                }else{
                                if(currentPage5 <= dataDa['data']['totalPage']){
                                    
                                    for(var i = 0; i <= dataDa['data']['datas'].length-1; i++){
                                        result+= `<div class="tabcontent_list" 
                                        cardid=${dataDa['data']['datas'][i]['cardid'] || ''} 
                                        name=${dataDa['data']['datas'][i]['customername'] || ''}
                                        cardstatus=${dataDa['data']['datas'][i]['cardstatus'] || ''}>                                 
                                                    <div class="bdl_delete">
                                                        <button class="cancel">取消</button>
                                                        <button class="delete">删除</button>
                                                    </div>
                                                     <div class="imgOdd" 
                                                      cardstatus=${dataDa['data']['datas'][i]['cardstatus'] || ''}
                                                      cardid=${dataDa['data']['datas'][i]['cardid'] || ''}></div>
                                                    <span class="bdl_imgT"><img src=${returnTask(dataDa['data']['datas'][i]['cardtype'])['img']} alt=""></span>
                                                    <span class="bdl_txt">请为${dataDa['data']['datas'][i]['customername'] ||''}的订单(编号${dataDa['data']['datas'][i]['orderid']||''})办理${returnTask(dataDa['data']['datas'][i]['cardtype'])['message']}</span>
                                                    <div class="bdl_listMess">
                                                        <div class="bdl_bor">
                                                            <div class="bdl_Lleft">
                                                            <span class="bdl_caImgR"></span>
                                                            <span class="txt">任务</span>
                                                            </div>
                                                            <span class="bdl_caNum"><span>${dataDa['data']['datas']['cardstatus']=="3"? 0:1}</span>/<span>1</span></span>
                                                        </div>
                                                        <div class="bdl_bor">
                                                            <div class="bdl_Lleft">
                                                            <span class="bdl_caImgT"></span>
                                                            <span class="txt">时间</span>
                                                            </div>
                                                <span class="bdl_caNum"><span class="spans">${dataDa['data']['datas'][i]['enddate'] ? dataDa['data']['datas'][i]['enddate'].split('-')[0]+'年'+dataDa['data']['datas'][i]['enddate'].split('-')[1]+'月'+dataDa['data']['datas'][i]['enddate'].split('-')[2]+'日':''}</span>
                                                        </div>
                                                    </div>
                                            </div>`;

                                        if(dataDa['data']['currentPage']>dataDa['data']['totalPage']){
                                            // 数据加载完
                                            tab5LoadEnd = true;
                                            me.lock();
                                            // 无数据
                                            me.noData();
                                            break;
                                        }
                                    }
                                    // 为了测试，延迟1秒加载
                                    setTimeout(function(){
                                        $('#tabcontent5').append(result);
                                        if($(".maintab").find(".dropload-down").length>1){ 
                                            $(".maintab").find(".dropload-down").slice(0).remove();
                                        }
                                        // 每次数据加载完，必须重置
                                        me.resetload();
                                        //长按删除
                                        ~(function(){
                                            
                                            $('.tabcontent_list').longPress(function(ooo){
                                                $(ooo).find(".bdl_delete").show();
                                            });
                                            //弹框 取消
                                            $(".cancel").on("click",function(event){
                                                event.stopPropagation();
                                                
                                                $(this).parent(".bdl_delete").hide();
                                            });
                                            //点击删除 删除对应列表
                                            $(".delete").off("click").on("click",function(){
                                                $(this).parents(".tabcontent_list").remove();
                                            })
                                            //批量　完成上牌
                                            $(".bdl_scottare").on("click",function(){
                                                subQuery();
                                            })
                                            //点击列表跳转 页面 带走卡片id
                                            $(".tabcontent_list").off("click").on("click",function(){
                                                var $cardid = $(this).attr("cardid");
                                                var $cardstatus = $(this).attr("cardstatus");
                                                session.setItem("name",$(this).attr("name"));
                                                //更新卡片为进行中
                                                $updateCardStatusDoingAjax($cardid,$cardstatus);
                                            })

                                        }()) 
                                        
                                         //避免 出现 多次旋转
                                         biling(dataData);
                                         clickGet(dataData);
                                    },1000);
                                }
                                    currentPage5 ++;
                             }
                             break;
                             case "101":
                                alert("失败");
                            break;
                            case "102":
                                alert("参数不正确");
                            break;
                        }
                    },
                    error: function(xhr, type){
                        alert('Ajax error!');
                        // 即使加载出错，也得重置
                        me.resetload();
                    }
                 });
                }
             })

         }else{
             biling(dataData);
             clickGet(dataData);
         }  
    }
    // 获取数量
    function $ajaxNum(num){
        var dataData = JSON.parse(session.getItem("data"))['data'];
        var First= {};
        var employeeid = dataData['employeeid'];
            First['currentPage'] = '1';
            First['cardStatus'] = num;
            First['employeeid'] = employeeid;
        $.ajax({ 
            type: "POST",
            url: srcConfig.queryCardByEmployee,
            dataType: 'json',
            contentType: "application/json",   //这个放开
            data: JSON.stringify(First),
            success: function (data) {
                switch(data['code']){
                    case "0":
                        switch(num){
                            case "0":
                                $(".text").eq(3).html(data['data']['totalRecord']);
                            break;
                            case "1":
                                 console.log(data['data']['totalRecord'])
                                 $(".text").eq(0).html(data['data']['totalRecord']);
                            break;
                            case "2":
                                $(".text").eq(1).html(data['data']['totalRecord']);
                            break;
                            case "3":
                                $(".text").eq(2).html(data['data']['totalRecord']);
                            break;
                        }
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
                alert("请求失败");
            }
        });
    }
    
    //封装 数量num
    function $num(){
        $ajaxNum("1")
        $ajaxNum("2")
        $ajaxNum("3")
        $ajaxNum("0")
    }
        
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
                        // alert(JSON.stringify(data['data']));
                        session.setItem("data",JSON.stringify(data));
                        biling(data['data']);
                        clickGet(data['data']);
                        clickBar();
                        $num()
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
                alert("请求失败");
            }
        });
   }
   //更新卡片为进行中 ajax
   function $updateCardStatusDoingAjax(cardid,cardstatus){
       console.log(cardstatus)
        if(cardstatus=="1"){
            $.ajax({ 
            type: "POST",
            url: srcConfig.updateCardStatusDoing,
            dataType: 'json',
            contentType: "application/json",   //这个放开
            data: JSON.stringify({
                "cardid":cardid
            }),
            success: function (data) {
                switch(data['code']){
                    case "0":
                        alert('成功');
                        window.location.href = "./bdlSell_list.html?cardid="+cardid+"";
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
                alert("请求失败");
            }
         });
        }else{
            window.location.href = "./bdlSell_list.html?cardid="+cardid+"";
        }
   }
   //调用 弹框
   function clickGet(data){
        $(".bdl_CarSearch").on("click",function(){
            window.location.href="./bdlSell_search.html?employeeId="+data['employeeid']+"";
        });
        //添加卡片
        $(".bdl_CarAdd").on("click",function(){
            window.location.href = "./bdlSell_add.html?employeeid="+data['employeeid']+"";
        });
        //首页看板
        $(".bdl_CarMain").on("click",function(){
            window.location.href = "./bdlSell_card.html";
        });
   }
    //获取到的员工id
    function biling(data){
        
        var itemIndex = 0;
        var tab1LoadEnd = false;
        var tab2LoadEnd = false;
        var tab3LoadEnd = false;
        var tab4LoadEnd = false;
        // var tab5LoadEnd = false;
        $('.tabtitle li').off("click").on("click",function () {
            var index = $(this).index();
            var $this = $(this);
            itemIndex = $this.index();
            $(".dropload-down").remove(); 
            $(".bdl_push").hide(200).attr("display","false");
            $(this).attr('class',"tabhover").siblings('li').attr('class','taba');
            $('.tabcontent').eq(index).show(200).siblings('.tabcontent').hide();
            $('.tabcontent').eq(index).addClass("checkAll").siblings().removeClass("checkAll");
            if(itemIndex == '0'){
            // 如果数据没有加载完
                if(!tab1LoadEnd){
                    // 解锁
                    dropload.unlock();
                    dropload.noData(false);
                }else{
                    // 锁定
                    dropload.lock('down');
                    dropload.noData();
                }
            // 如果选中菜单二
            }else if(itemIndex == '1'){
 
                if(!tab2LoadEnd){
                    // 解锁
                    dropload.unlock();
                    dropload.noData(false);
                }else{
                    // 锁定
                    dropload.lock('down');
                    dropload.noData();
                }
            }else if(itemIndex == '2'){
                if(!tab3LoadEnd){
                    // 解锁
                    dropload.unlock();
                    dropload.noData(false);
                }else{
                    // 锁定
                    dropload.lock('down');
                    dropload.noData();
                }
            }else if(itemIndex == '3'){
                if(!tab4LoadEnd){
                    // 解锁
                    dropload.unlock();
                    dropload.noData(false);
                }else{
                    // 锁定
                    dropload.lock('down');
                    dropload.noData();
                }
            }
            // 重置
            dropload.resetload();
        });
        var currentPage1 = 1;
        var currentPage2 = 1;
        var currentPage3 = 1;
        var currentPage4 = 1;
       
        var dropload = $('.maintab').dropload({
            scrollArea : window,
            loadDownFn : function(me){
                var itemDate1 = {};
                var itemDate2 = {};
                var itemDate3 = {};
                var itemDate4 = {};
                if(itemIndex == '0'){
                    if(session.getItem("name")){
                        
                        clickGet(JSON.parse(session.getItem('data'))['data']['employeeid']);
                     
                        return false;
                    }else{  
                        if($(".maintab").find(".dropload-down").length>1){ 
                            $(".maintab").find(".dropload-down").slice(0).remove();
                        }
                        var cardStatus = '1';
                        var employeeid = data['employeeid'];
                        itemDate1['currentPage'] = currentPage1;
                        itemDate1['cardStatus'] = cardStatus;
                        itemDate1['employeeid'] = employeeid;
                        console.log(itemDate1)
                    $.ajax({
                        type: 'post',
                        // url:"http://localhost:8086",
                        // url:"http://192.168.206.188:8889/blchina-cbp/card/queryCardByEmployee",
                        url: srcConfig.queryCardByEmployee, //
                        // 打开
                        dataType: 'json',
                        contentType: "application/json",  //打开
                        data: JSON.stringify(itemDate1),
                        success: function(dataDa){
                            console.log(dataDa)
                            switch(dataDa.code){
                                case "0":
                                    var result = '';
                                    // 条数
                                    if(dataDa['data']['datas']==''){
                                        $(".dropload-down").html(`<div class="dropload-noData">暂无数据</div>`)
                                    }else{
                                        
                                    if(dataDa['data']['currentPage'] <= dataDa['data']['totalPage']){
                                        
                                        for(var i = 0; i <= dataDa['data']['datas'].length-1; i++){
                                            result+= `<div class="tabcontent_list" cardid=${dataDa['data']['datas'][i]['cardid'] || ''} 
                                            cardstatus=${dataDa['data']['datas'][i]['cardstatus'] || ''}>                                 
                                                        <div class="bdl_delete">
                                                            <button class="cancel">取消</button>
                                                            <button class="delete">删除</button>
                                                        </div>
                                                        <div class="imgOdd" 
                                                        cardstatus=${dataDa['data']['datas'][i]['cardstatus'] || ''}
                                                        cardid=${dataDa['data']['datas'][i]['cardid'] || ''}></div>
                                                        <span class="bdl_imgT"><img src=${returnTask(dataDa['data']['datas'][i]['cardtype'])['img']} alt=""></span>
                                                        <span class="bdl_txt">请为${dataDa['data']['datas'][i]['customername'] || ''}的订单(编号${dataDa['data']['datas'][i]['orderid']||''})办理${returnTask(dataDa['data']['datas'][i]['cardtype'])['message']}</span>
                                                        <div class="bdl_listMess">
                                                            <div class="bdl_bor">
                                                                <div class="bdl_Lleft">
                                                                <span class="bdl_caImgR"></span>
                                                                <span class="txt">任务</span>
                                                                </div>
                                                                <span class="bdl_caNum"><span>${dataDa['data']['datas']['cardstatus']=="3"? 0:1}</span>/<span>1</span></span>
                                                            </div>
                                                            <div class="bdl_bor">
                                                                <div class="bdl_Lleft">
                                                                <span class="bdl_caImgT"></span>
                                                                <span class="txt">时间</span>
                                                                </div>
                                                    <span class="bdl_caNum"><span class="spans">${dataDa['data']['datas'][i]['enddate'] ? dataDa['data']['datas'][i]['enddate'].split('-')[0]+'年'+dataDa['data']['datas'][i]['enddate'].split('-')[1]+'月'+dataDa['data']['datas'][i]['enddate'].split('-')[2]+'日':''}</span>
                                                            </div>
                                                        </div>
                                                </div>`;
                                            if(dataDa['data']['currentPage']>dataDa['data']['totalPage']){
                                            
                                                // 数据加载完
                                                tab1LoadEnd = true;
                                                // 锁定
                                                me.lock();
                                                // 无数据
                                                me.noData();
                                                break;
                                            }
                                            
                                        }
                                        currentPage1 ++;
                                        // 为了测试，延迟1秒加载
                                        setTimeout(function(){
                                            $('#tabcontent1').append(result);
                                            // 每次数据加载完，必须重置
                                            me.resetload();
                                            //长按删除
                                            ~(function(){
                                                $('.tabcontent_list').longPress(function(ooo){
                                                    $(ooo).find(".bdl_delete").show();
                                                });
                                                //弹框 取消
                                                $(".cancel").on("click",function(event){
                                                    event.stopPropagation();
                                            
                                                    $(this).parent(".bdl_delete").hide();
                                                });
                                                //点击删除 删除对应列表
                                                $(".delete").off("click").on("click",function(){
                                                    $(this).parents(".tabcontent_list").remove();
                                                });
                                                //批量　完成上牌
                                                $(".bdl_scottare").on("click",function(){
                                                    subQuery();
                                                })
                                                //点击列表跳转 页面 带走卡片id
                                                $(".tabcontent_list").off("click").on("click",function(){
                                                    var $cardid = $(this).attr("cardid");
                                                    var $cardstatus = $(this).attr("cardstatus");
                                                    //更新卡片为进行中
                                                    $updateCardStatusDoingAjax($cardid,$cardstatus);
                                                });
                                            }());
                                        },1000);
                                    }
                                }
                                break;
                                case "101":
                                    alert("失败");
                                break;
                                case "102":
                                    alert("参数不正确");
                                break;
                            }
                        }, 
                        error: function(xhr, type){
                            alert('Ajax error!');
                            // 即使加载出错，也得重置
                            me.resetload();
                        }
                    });
                 }
                }else if(itemIndex == '1'){
                    var cardStatus = '2';
                    var employeeid = data['employeeid'];
                    itemDate2['currentPage'] = currentPage2;
                    itemDate2['cardStatus'] = cardStatus;
                    itemDate2['employeeid'] = employeeid;
                    console.log(itemDate2)
                    $.ajax({
                    type: 'post',
                    // url:"http://localhost:8888",
                    url: srcConfig.queryCardByEmployee,
                    dataType: 'json',
                    contentType: "application/json",
                    data: JSON.stringify(itemDate2),
                    success: function(dataDa){
                        
                        switch(dataDa.code){
                            case "0":
                            alert(JSON.stringify(dataDa));
                              console.log(dataDa['data'])
                            var result = '';
                            if(dataDa['data']['datas']==''){
                                    $(".dropload-down").html(`<div class="dropload-noData">暂无数据</div>`)
                                }else{
                                // alert("2")
                                if(dataDa['data']['currentPage'] <= dataDa['data']['totalPage']){
                                    
                                    for(var i = 0; i <= dataDa['data']['datas'].length-1; i++){
                                    
                                        result+= `<div class="tabcontent_list" cardid=${dataDa['data']['datas'][i]['cardid'] || ''}
                                                  cardstatus=${dataDa['data']['datas'][i]['cardstatus'] || ''}>                                 
                                                    <div class="bdl_delete">
                                                        <button class="cancel">取消</button>
                                                        <button class="delete">删除</button>
                                                    </div>
                                                    <div class="imgOdd" 
                                                      cardstatus=${dataDa['data']['datas'][i]['cardstatus'] || ''}
                                                      cardid=${dataDa['data']['datas'][i]['cardid'] || ''}></div>
                                                    <span class="bdl_imgT"><img src=${returnTask(dataDa['data']['datas'][i]['cardtype'])['img']} alt=""></span>
                                                        <span class="bdl_txt">请为${dataDa['data']['datas'][i]['customername'] ||''}的订单(编号${dataDa['data']['datas'][i]['orderid']||''})办理${returnTask(dataDa['data']['datas'][i]['cardtype'])['message']}</span>
                                                    <div class="bdl_listMess">
                                                        <div class="bdl_bor">
                                                            <div class="bdl_Lleft">
                                                            <span class="bdl_caImgR"></span>
                                                            <span class="txt">任务</span>
                                                            </div>
                                                            <span class="bdl_caNum"><span>0</span>/<span>1</span></span>
                                                        </div>
                                                        <div class="bdl_bor">
                                                            <div class="bdl_Lleft">
                                                            <span class="bdl_caImgT"></span>
                                                            <span class="txt">时间</span>
                                                            </div>
                                                         <span class="bdl_caNum"><span class="spans">${dataDa['data']['datas'][i]['enddate'] ? dataDa['data']['datas'][i]['enddate'].split('-')[0]+'年'+dataDa['data']['datas'][i]['enddate'].split('-')[1]+'月'+dataDa['data']['datas'][i]['enddate'].split('-')[2]+'日':''}</span>
                                                        </div>
                                                    </div>
                                            </div>`;
                                    
                                        if(dataDa['data']['currentPage']>dataDa['data']['totalPage']){
                                            // 数据加载完
                                            tab2LoadEnd = true;
                                            // 锁定
                                            me.lock();
                                            // 无数据
                                            me.noData();
                                            break;
                                        }
                                    }
                                    currentPage2 ++;
                                    // 为了测试，延迟1秒加载
                                    setTimeout(function(){
                                        $('#tabcontent2').append(result);
                                        // 每次数据加载完，必须重置
                                        me.resetload();
                                        //长按删除
                                        ~(function(){
                                            $('.tabcontent_list').longPress(function(ooo){
                                                $(ooo).find(".bdl_delete").show();
                                            });
                                            //弹框 取消
                                            $(".cancel").on("click",function(event){
                                                event.stopPropagation();
                                                $(this).parent(".bdl_delete").hide();
                                            });
                                            //点击删除 删除对应列表
                                            $(".delete").off("click").on("click",function(){
                                                $(this).parents(".tabcontent_list").remove();
                                            })
                                            //批量　完成上牌
                                            $(".bdl_scottare").on("click",function(){
                                                subQuery();
                                            })
                                            //点击列表跳转 页面 带走卡片id
                                            $(".tabcontent_list").off("click").on("click",function(){
                                                var $cardid = $(this).attr("cardid");
                                                //更新卡片为进行中
                                                window.location.href = "./bdlSell_list.html?cardid="+$cardid+"";
                                            })
                                        }())
                                    },1000);
                                }
                            }
                            break;
                            case "101":
                                alert("失败");
                            break;
                            case "102":
                                alert("参数不正确");
                            break;
                        }
                    },
                    error: function(xhr, type){
                        alert('Ajax error!');
                        // 即使加载出错，也得重置
                        me.resetload();
                    }
                 });
              }else if(itemIndex == '2'){
                    var cardStatus = '3';
                    var employeeid = data['employeeid'];
                    itemDate3['currentPage'] = currentPage3;
                    itemDate3['cardStatus'] = cardStatus;
                    itemDate3['employeeid'] = employeeid;
                    console.log(itemDate3)
                    $.ajax({
                    type: 'post',
                    // url:"http://localhost:8888",
                    url: srcConfig.queryCardByEmployee,
                    dataType: 'json',
                    contentType: "application/json",
                    data: JSON.stringify(itemDate3),
                    success: function(dataDa){
                        switch(dataDa.code){
                           
                            case "0":
                                alert(JSON.stringify(dataDa['data']));
                              
                                var result = '';
                                if(dataDa['data']['datas']==''){
                                    $(".dropload-down").html(`<div class="dropload-noData">暂无数据</div>`)
                                }else{
                                //  alert("3")
                                if(dataDa['data']['currentPage'] <= dataDa['data']['totalPage']){
                                    
                                    for(var i = 0; i <= dataDa['data']['datas'].length; i++){
                                    
                                       result+= `<div class="tabcontent_list" cardid=${dataDa['data']['datas'][i]['cardid'] || ''}>                                
                                                    <div class="bdl_delete">
                                                        <button class="cancel">取消</button>
                                                        <button class="delete">删除</button>
                                                    </div>
                                                    <div class="imgOdd"></div>
                                                    <span class="bdl_imgT"><img src=${returnTask(dataDa['data']['datas'][i]['cardtype'])['img']} alt=""></span>
                                                    <span class="bdl_txt">请为${dataDa['data']['datas'][i]['customername'] ||''}的订单(编号${dataDa['data']['datas'][i]['orderid']||''})办理${returnTask(dataDa['data']['datas'][i]['cardtype'])['message']}</span>
                                                    <div class="bdl_listMess">
                                                        <div class="bdl_bor">
                                                            <div class="bdl_Lleft">
                                                            <span class="bdl_caImgR"></span>
                                                            <span class="txt">任务</span>
                                                            </div>
                                                            <span class="bdl_caNum"><span>0</span>/<span>1</span></span>
                                                        </div>
                                                        <div class="bdl_bor">
                                                            <div class="bdl_Lleft">
                                                            <span class="bdl_caImgT"></span>
                                                            <span class="txt">时间</span>
                                                            </div>
                                                            <span class="bdl_caNum"><span class="spans">${dataDa['data']['datas'][i]['enddate'] ? dataDa['data']['datas'][i]['enddate'].split('-')[0]+'年'+dataDa['data']['datas'][i]['enddate'].split('-')[1]+'月'+dataDa['data']['datas'][i]['enddate'].split('-')[2]+'日':''}</span>
                                                        </div>
                                                    </div>
                                            </div>`;
                                          
                                        if(dataDa['data']['currentPage']>dataDa['data']['totalPage']){
                                            // 数据加载完
                                            tab3LoadEnd = true;
                                            // 锁定
                                            me.lock();
                                            // 无数据
                                            me.noData();
                                            break;
                                        }
                                    }
                                    currentPage3 ++;
                                    // 为了测试，延迟1秒加载
                                    setTimeout(function(){
                                        $('#tabcontent3').append(result);
                                        // 每次数据加载完，必须重置
                                        me.resetload();
                                        //长按删除
                                        ~(function(){
                                            $('.tabcontent_list').longPress(function(ooo){
                                                $(ooo).find(".bdl_delete").show();
                                            });
                                            //弹框 取消
                                            $(".cancel").on("click",function(event){
                                                event.stopPropagation();
                                                $(this).parent(".bdl_delete").hide();
                                            });
                                            //点击删除 删除对应列表
                                            $(".delete").off("click").on("click",function(){
                                                $(this).parents(".tabcontent_list").remove();
                                            })
                                            //批量　完成上牌
                                            $(".bdl_scottare").on("click",function(){
                                                subQuery();
                                            })
                                            //点击列表跳转 页面 带走卡片id
                                            $(".tabcontent_list").off("click").on("click",function(){
                                                var $cardid = $(this).attr("cardid");
                                                //更新卡片为进行中
                                                window.location.href = "./bdlSell_list.html?cardid="+$cardid+"";
                                            })
                                        }())
                                    },1000);
                                }
                            }
                            break;
                            case "101":
                                alert("失败");
                            break;
                            case "102":
                                alert("参数不正确");
                            break;
                        }
                    },
                    error: function(xhr, type){
                        alert('Ajax error!');
                        // 即使加载出错，也得重置
                        me.resetload();
                    }
                 });
              }else if(itemIndex = "3"){
                    var cardStatus = '0';
                    var employeeid = data['employeeid'];
                    itemDate4['currentPage'] = currentPage4;
                    itemDate4['cardStatus'] = cardStatus;
                    itemDate4['employeeid'] = employeeid;
                    console.log(itemDate4)
                    $.ajax({
                    type: 'post',
                    // url:"http://localhost:8888",
                    url: srcConfig.queryCardByEmployee,
                    dataType: 'json',
                    contentType: "application/json",
                    data: JSON.stringify(itemDate4),
                    success: function(dataDa){
                        switch(dataDa.code){
                                case "0":
                                alert(JSON.stringify(dataDa['data']));
                                //点击全部  重新获取全部的数量
                                $(".text").eq(3).html(dataDa['data']['totalRecord']);
                                var result = '';
                                if(dataDa['data']['datas']==''){
                            
                                    $(".dropload-down").html(`<div class="dropload-noData">暂无数据</div>`)
                                }else{ 
                                
                                 if(dataDa['data']['currentPage'] <= dataDa['data']['totalPage']){
                                        
                                    for(var i = 0; i <= dataDa['data']['datas'].length-1; i++){
          
                                        console.log(dataDa['data']['datas'][i])   
                                        console.log(dataDa['data']['datas'].length)
                                            result+= `<div class="tabcontent_list" cardid=${dataDa['data']['datas'][i]['cardid'] || ''} cardstatus=${dataDa['data']['datas'][i]['cardstatus'] || ''}>                              
                                                    <div class="bdl_delete">
                                                        <button class="cancel">取消</button>
                                                        <button class="delete">删除</button>
                                                    </div>
                                                    <div class="imgOdd" 
                                                      cardstatus=${dataDa['data']['datas'][i]['cardstatus'] || ''}
                                                      cardid=${dataDa['data']['datas'][i]['cardid'] || ''}></div>
                                                    <span class="bdl_imgT"><img src=${returnTask(dataDa['data']['datas'][i]['cardtype'])['img']} alt=""></span>
                                                    <span class="bdl_txt">请为${dataDa['data']['datas'][i]['customername'] ||''}的订单(编号${dataDa['data']['datas'][i]['orderid']||''})办理${returnTask(dataDa['data']['datas'][i]['cardtype'])['message']}</span>
                                                    <div class="bdl_listMess">
                                                        <div class="bdl_bor">
                                                            <div class="bdl_Lleft">
                                                            <span class="bdl_caImgR"></span>
                                                            <span class="txt">任务</span>
                                                            </div>
                                                            <span class="bdl_caNum"><span>0</span>/<span>1</span></span>
                                                        </div>
                                                        <div class="bdl_bor">
                                                            <div class="bdl_Lleft">
                                                            <span class="bdl_caImgT"></span>
                                                            <span class="txt">时间</span>
                                                            </div>
                                                          <span class="bdl_caNum"><span class="spans">${dataDa['data']['datas'][i]['enddate'] ? dataDa['data']['datas'][i]['enddate'].split('-')[0]+'年'+dataDa['data']['datas'][i]['enddate'].split('-')[1]+'月'+dataDa['data']['datas'][i]['enddate'].split('-')[2]+'日':''}</span>
                                                        </div>
                                                    </div>
                                            </div>`;
                                        
                                        if(dataDa['data']['currentPage']>dataDa['data']['totalPage']){
                                        
                                            // 数据加载完
                                            tab4LoadEnd = true;
                                            // 锁定
                                            me.lock();
                                            // 无数据
                                            me.noData();
                                            break;
                                        }
                                        
                                    }
                                    currentPage4 ++;
                                    // 为了测试，延迟1秒加载
                                    setTimeout(function(){
                                        $('#tabcontent4').append(result);
                                        // 每次数据加载完，必须重置
                                        //长按删除
                                        ~(function(){
                                            $('.tabcontent_list').longPress(function(ooo){
                                                $(ooo).find(".bdl_delete").show();
                                            });
                                            //弹框 取消
                                            $(".cancel").on("click",function(event){
                                                event.stopPropagation();
                                                $(this).parent(".bdl_delete").hide();
                                            });
                                            //点击删除 删除对应列表
                                            $(".delete").off("click").on("click",function(){
                                                $(this).parents(".tabcontent_list").remove();
                                            })
                                            //批量　完成上牌
                                            $(".bdl_scottare").on("click",function(){
                                                subQuery();
                                            })
                                            //点击列表跳转 页面 带走卡片id
                                            $(".tabcontent_list").off("click").on("click",function(){
                                                var $cardid = $(this).attr("cardid");
                                                var $cardstatus = $(this).attr("cardstatus");
                                                //更新卡片为进行中
                                                $updateCardStatusDoingAjax($cardid,$cardstatus);
                                            })
                                        }())
                                        me.resetload();  
                                    },1000);
                                }
                            }
                            break;
                            case "101":
                                alert("失败");
                            break;
                            case "102":
                                alert("参数不正确");
                            break;
                        }
                    },
                    error: function(xhr, type){
                        alert('Ajax error!');
                        // 即使加载出错，也得重置
                        me.resetload();
                    }
                 });
              }
            }
        })
    }
    // 右上多选 栏
    function clickBar(){
        var dataData = JSON.parse(session.getItem("data"))['data'];
        var $bdl_push = $(".other");
        var $yanCar = $(".yanCar");
        if(dataData['accounttype'] == "107" || dataData['accounttype'] == "108"){
           $(".bdl_img").attr("src","./img/icon02.png");
        }else{
           $(".bdl_img").attr("src","./img/big.png");
        }
        $(".bdl_boxS").off("click").on("click",function(event){
            event.stopPropagation();
            //pan duan shi fou wei yan che zhuan yuan
            if(dataData['accounttype'] == "107" || dataData['accounttype'] == "108"){
                if($yanCar.css("display")=='none'){
                    $yanCar.show(200);
                }else{
                    $yanCar.hide(200);
                } 
            }else{
                 if($bdl_push.css("display")=='none'){
                    $bdl_push.show(200);
                }else{
                    $bdl_push.hide(200);
                }
            }
        })
    }
    //点击其他  隐藏弹出框
   $(document).on('click', function (e) {  
        if(e.target.className!='bdl_boxS'){
            if($('.other').css("display")=='block'){
                $('.other').hide(200);
            }
            if($('.yanCar').css("display")=='block'){
                $('.yanCar').hide(200);
            }
        }
    }); 
    //批量 完成上牌
    function subQuery(){
        $(".bottomFn").show();
        $(".maintab").css("margin-bottom","1.1rem");
        $(".checkAll").find(".imgOdd").fadeIn().removeClass("imgOkk");
        //图标点击
        $(".imgOdd").off("click").on("click",function(event){
            event.stopPropagation()//阻止冒泡
            if($(this).hasClass("imgOkk")){
                $(this).removeClass("imgOkk");
            }else{
                $(this).addClass("imgOkk");
            }
        })
        //点击取消
        $(".bave").on("click",function(){
            $(".bottomFn").hide();
            $(".maintab").css("margin-bottom","0");
            $(".checkAll").find(".imgOdd").fadeOut();
        })
        //点击完成
        $(".ok").on("click",function(){
           var length = $(".checkAll").find(".imgOkk").length;
           var sendData = {};
           var bdlCardList = [];
           for(var i = 0;i < length;i++){
               if($(".checkAll").find(".imgOkk")[i]!="3"){
                    bdlCardList.push({
                        'cardid':$($(".checkAll").find(".imgOkk")[i]).attr("cardid"),
                        'cardstatus':$($(".checkAll").find(".imgOkk")[i]).attr("cardstatus")
                    });
               }
           }
           sendData['bdlCardList'] = bdlCardList;
           if(bdlCardList==''){
                alert("没有选取任务");
                $(".bottomFn").hide();
                $(".maintab").css("margin-bottom","0");
                $(".checkAll").find(".imgOdd").fadeOut();
               return false;
           }else{
               console.log(sendData)
                $.ajax({ 
                type: "POST",
                url: srcConfig.batchForCheckCarPerson,
                // url:'http://192.168.206.188:8889/blchina-cbp/card/batchForCheckCarPerson',
                dataType: 'json',
                contentType: "application/json",
                data: JSON.stringify(sendData),
                success: function (data) {
                        switch(data['code']){
                        case "0":
                            alert("成功");
                            $(".bottomFn").hide();
                            $(".maintab").css("margin-bottom","0");
                            $(".checkAll").find(".imgOdd").fadeOut();
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
              })
           }
        })
    }
    // 返回任务  封装
    function returnTask(data){
        var object = {};
        switch(data){
            //销售顾问
            case '1':
                object['message'] = '购车';
                object['img'] = './img/gou.png';
                return object;
            break;
            case "2":
                object['message'] = '订单确认';
                object['img'] = './img/tap02.png';
                return object;
            break;
            case "3":
                object['message'] = '二手车';
                object['img'] = './img/tap02.png';
                return object;
            break;
            case "4":
                object['message'] = '金融分期';
                object['img'] = './img/dai.png';
                return object;
            break;
            case "5":
                object['message'] = '车辆置换';
                object['img'] = './img/zhi.png';
                return object;
            break;
            case "6":
                object['message'] = '车险';
                object['img'] = './img/bao.png';
                return object;
            break;
            case "7":
                object['message'] = '验车上牌';
                object['img'] = './img/tap02.png';
                return object;
            break;
            case "8":
                object['message'] = '精品';
                object['img'] = './img/jing.png';
                return object;
            break;
            case "9":
                object['message'] = '收定金';
                object['img'] = './img/cai.png';
                return object;
            break;
            case "10":
                object['message'] = '收尾款';
                object['img'] = './img/cai.png';
                return object;
            break;
            case "11":
                object['message'] = '收全款';
                object['img'] = './img/cai.png';
                return object;
            break;
            case "12":
                object['message'] = '金融分期收款';
                object['img'] = './img/cai.png';
                return object;
            break;
            case "13":
                object['message'] = '二手车收款';
                object['img'] = './img/cai.png';
                return object;
            break;
            case "14":
                object['message'] = '车险';
                object['img'] = './img/bao.png';
                return object;
            break;
            case "15":
                object['message'] = '车辆置换';
                object['img'] = './img/zhi.png';
                return object;
            break;
            case "16":
                object['message'] = '收款';
                object['img'] = './img/cai.png';
                return object;
            break;
            case "17":
                object['message'] = '金融分期';
                object['img'] = './img/dai.png';
                return object;
            break;
            case "18":
                object['message'] = '准备精品';
                object['img'] = './img/jing.png';
                return object;
            break;
            case "19":
                object['message'] = '加装精品';
                object['img'] = './img/jing.png';
                return object;
            break;
            case "20":
                object['message'] = '验车上牌';
                object['img'] = './img/zhi.png';
                return object;
            break;

            // 1--购车下单,2--订单确认，3--二手车，4--金融分期，5--车辆置换，6--车险 7--验车上牌,8--精品
        }
        
    }
}(jQuery));