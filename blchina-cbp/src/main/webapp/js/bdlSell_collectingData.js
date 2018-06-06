$(function(){
    var url = window.location.href; //获取当前页url
    var tokenQuery = url.queryURLParameter();
    //带给我checkcarnumid 验车上牌ID
    $leave(); 
    $add();
    function $add(){
        $.ajax({ 
            type: "POST",
            url: srcConfig.getAllInfo,
            // url:"http://192.168.206.49:8080/blchina-cbp/checkCarNum/getAllInfo",
            dataType: 'json',
            contentType: "application/json",
            data: JSON.stringify(),
            success: function (data) {
                    switch(data['code']){
                    case "0":
                        alert("成功");
                        console.log(data)
                        bling(data['data']);
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
    function bling(data){
        var result = '';
        $.each(data,function(index,val){
            result+=`
                <div class="list" sourcetype=${val['sourcetype']}>
                    <i class="icon"></i>
                    <span>${val['sourceName']}</span>
                </div>
            `;
            $(".bdl_box").html(result);
        })
        $.ajax({ 
            type: "POST",
            url: srcConfig.getInfoBySelected,
            // url:"http://192.168.206.49:8080/blchina-cbp/checkCarNum/getInfoBySelected",
            dataType: 'json',
            contentType: "application/json",
            data: JSON.stringify({
                'checkcarnumid':"1"
            }),
            success: function (data) {
                    switch(data['code']){
                    case "0":
                        alert("成功");
                        console.log(data)
                        blingBgain(data['data']);
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
    function blingBgain(data){
        $.each(data,function(index,val){
            $(".list").eq(val['sourcetype']-"1").find(".icon").addClass("on");
        })
        $(".list").unbind("click").bind("click",function(){
            var $this = $(this).find(".icon");
            if($this.hasClass("on")){
                $this.removeClass("on");
                var sendData = {};
                // sendData['checkcarnumid'] = 1;
                // sendData['checkcarnumid'] = tokenQuery['checkcarnumid'];
                var sourcename = $(this).find("span").html();
                sendData['sourcetype'] = $(this).attr("sourcetype");
                sendData['ischoiced'] = '0';
                sendData['orderid'] = tokenQuery['orderid'];
                sendData['sourcename'] = sourcename;
                $ajax(srcConfig.collectionInfo,sendData);
                // $ajax('http://192.168.206.49:8080/blchina-cbp/checkCarNum/collectionInfo',sendData);

            }else{
                $this.addClass("on");
                var sendData = {};
                // sendData['checkcarnumid'] = 1;
                // sendData['checkcarnumid'] = tokenQuery['checkcarnumid'];
                var sourcename = $(this).find("span").html();
                sendData['sourcetype'] = $(this).attr("sourcetype");
                sendData['ischoiced'] = '1';
                sendData['orderid'] = tokenQuery['orderid'];
                sendData['sourcename'] = sourcename;
               	
                $ajax(srcConfig.collectionInfo,sendData);
                // $ajax('http://192.168.206.49:8080/blchina-cbp/checkCarNum/collectionInfo',sendData);
            }
        })
    }
    //点击icon 选中　信息
    
    //后退
    function $leave(){
        $(".leave").on("click",function(){
            history.go(-1);            
        })
    }
    function $ajax(url,sendData){
        $.ajax({ 
            type: "POST",
            url: url,
            dataType: 'json',
            contentType: "application/json",
            data: JSON.stringify(sendData),
            success: function (data) {
                    switch(data['code']){
                    case "0":
                        alert("成功");
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
}())