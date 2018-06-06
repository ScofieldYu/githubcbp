$(function(){
    var url = window.location.href; //获取当前页urls
    var tokenQuery = url.queryURLParameter();
    var orderid = tokenQuery['orderid'].split("#")[0] || tokenQuery['orderid'];
    var session = window.sessionStorage;
    session.setItem("orderid",orderid);

    $('.tabtitle li').eq(0).hasClass("tabhover")?$(".left").addClass("leftDown"):$(".left").removeClass("leftDown");

        $margin();
    $('.tabtitle li').off("click").on("click",function () {
        var index = $(this).index();
        var lastIndex = $('.tabtitle li').eq(-1).index();
        index==0?$(".left").addClass("leftDown"):$(".left").removeClass("leftDown")
        // console.log(index,lastIndex)
        index==lastIndex?$(".right").addClass("rightDown"):$(".right").removeClass("rightDown");
        $move(index);
        if(index>5){
            $(".tabtitle").stop().animate({"margin-left":-125+"%"},300);
            return false;
        }else{
            $(".tabtitle").stop().animate({"margin-left":-index*25+"%"},300);
        }
        //删除 高
        $margin();
    })
    //自写　封装点击切换　
    //左
    $(".ppL").off("click").on("click",function(){
       var count = $(".tabtitle").find(".tabhover").index();
       count --;
       count<1 ? $(".left").addClass("leftDown"):$(".left").removeClass("leftDown");
       count != $(".tabtitle li").length-1 ? $(".right").removeClass("rightDown"):'';
       if(count*25<=-25){
            return false;
        }else if(count>=6){
            $move(count);
        }else{
            $move(count);
            $(".tabtitle").stop().animate({"margin-left":-count*25+"%"},300);
        }
        //删除 高
        $margin();
    })
    //右
    $(".ppR").off("click").on("click",function(){
        var count = $(".tabtitle").find(".tabhover").index();
        var length = $(".tabtitle li").length-1;
        count ++;
        console.log(count,length);
        count == length ? $(".right").addClass("rightDown"):'';
        count<=1 ? $(".left").removeClass("leftDown") :'';
        if(-count*25<-125){
            $move(count);
            return false;
        }else{
            $move(count);
            $(".tabtitle").stop().animate({"margin-left":-count*25+"%"},300);
        }
        $margin();
    })
    function $move(count){
        $(".tabtitle").find("li").eq(count).attr('class',"tabhover").siblings('li').attr('class','taba');
        $('.tabcontent').eq(count).show(200).siblings('.tabcontent').hide();
    }
    //删除 css 样式 和 height
    function $margin(){
        $('.tabtitle li').eq(0).hasClass("tabhover")?
        $(".maintab").css('height',''):
        $(".maintab").css("height","100%");
        $('.tabtitle li').eq(0).hasClass("tabhover")?
        $(".maintab").css('margin-top',''):
        $(".maintab").css('margin-top','.1rem');
    }
    

    publing();  
    imgChangeEvent();
    //图片上传
    function imgChangeEvent() {
        function imgChagen() {
            var $this = $(this);
            lrz(this.files[0])
                .then(function (rst) {
                    // 处理成功会执行
                    var img = new Image();
                    img.src = rst.base64;
                    $this.parent().find('img').remove();
                    $this.parent().addClass('on').append(img); //呈现图像(拍照結果);
                    $this.parent().find('img').addClass("img"+$this.parents(".photo").attr("num")+"").attr('src',img.src)
                })
        }
        $('.uploadBtn').map(function (p1, p2) {
            p2.removeEventListener('change', imgChagen, false); //先移除再绑定,防止事件多次触发
            p2.addEventListener('change', imgChagen, false);
        });
    }
     $(".addFile1").on("click",function(){
         if($(".bdl_listZhaoAdd1").length!=$(".img1").length){
             return false;
         }else{
            var str = '';
            str+=`<div class="bdl_listZhaoAdd bdl_listZhaoAdd1">
                    <div class="uploadListBig">
                        <div class="upload">
                            <input capture="camera" accept="image/*"  class="uploadBtn" type="file">
                            <div class="boxP">
                            <div class="upBg">
                                
                            </div>
                                <p><span class="bdl_textTitleOdd">垫付证明上传</span></p>
                            </div>
                            <b class="close clo"></b>
                        </div>
                    </div> 
                </div>`;
            $(".photo1").append(str);
            imgChangeEvent();
            remove();
         }  
    })
    $(".addFile2").on("click",function(){
         if($(".bdl_listZhaoAdd2").length!=$(".img2").length){
             return false;
         }else{
            var str = '';
            str+=`<div class="bdl_listZhaoAdd bdl_listZhaoAdd2">
                        <div class="uploadListBig">
                            <div class="upload">
                                <input capture="camera" accept="image/*"  class="uploadBtn" type="file">
                                <div class="boxP">
                                <div class="upBg">
                                    
                                </div>
                                 <p><span class="bdl_textTitleOdd">销售发票上传</span></p>
                                </div>
                                <b class="close clo"></b>
                            </div>
                        </div> 
                    </div>`;
            $(".photo2").append(str);
            imgChangeEvent();
            remove();
         }  
    })
    $(".addFile3").on("click",function(){
         if($(".bdl_listZhaoAdd3").length!=$(".img3").length){
             return false;
         }else{
            var str = '';
            str+=`<div class="bdl_listZhaoAdd bdl_listZhaoAdd3">
                        <div class="uploadListBig">
                            <div class="upload">
                                <input capture="camera" accept="image/*"  class="uploadBtn" type="file">
                                <div class="boxP">
                                <div class="upBg">
                                    
                                </div>
                                 <p><span class="bdl_textTitleOdd">暂住证/居住证上传</span></p>
                                </div>
                                <b class="close clo"></b>
                            </div>
                        </div> 
                    </div>`;
            $(".photo3").append(str);
            imgChangeEvent();
            remove();
         }  
    })
    $(".addFile4").on("click",function(){
         if($(".bdl_listZhaoAdd4").length!=$(".img4").length){
             return false;
         }else{
            var str = '';
            str+=`<div class="bdl_listZhaoAdd bdl_listZhaoAdd4">
                        <div class="uploadListBig">
                            <div class="upload">
                                <input capture="camera" accept="image/*"  class="uploadBtn" type="file">
                                <div class="boxP">
                                <div class="upBg">
                                    
                                </div>
                                 <p><span class="bdl_textTitleOdd">暂住证/居住证上传</span></p>
                                </div>
                                <b class="close clo"></b>
                            </div>
                        </div> 
                    </div>`;
            $(".photo4").append(str);
            imgChangeEvent();
            remove();
         }  
    })
    $(".addFile5").on("click",function(){
         if($(".bdl_listZhaoAdd5").length!=$(".img5").length){
             return false;
         }else{
            var str = '';
            str+=`<div class="bdl_listZhaoAdd bdl_listZhaoAdd5">
                        <div class="uploadListBig">
                            <div class="upload">
                                <input capture="camera" accept="image/*"  class="uploadBtn" type="file">
                                <div class="boxP">
                                <div class="upBg">
                                    
                                </div>
                                 <p><span class="bdl_textTitleOdd">暂住证/居住证上传</span></p>
                                </div>
                                <b class="close clo"></b>
                            </div>
                        </div> 
                    </div>`;
            $(".photo5").append(str);
            imgChangeEvent();
            remove();
         }  
    })
    function remove(){
        //点击删除
        $(".clo").off("click").on("click",function(event){
            event.stopPropagation();
            $(this).parents(".bdl_listZhaoAdd").remove();
        })
    }
    //开始先获取
    function publing(){
        console.log({
                    'orderid':orderid,
                    // 'type':num,
                    'filetypeext':"1"
                })
        $.ajax({ 
                type: "POST",
                // url: srcConfig.getFileExt,  //这个放开
                url:"http://192.168.206.220:8081/cbp/getFileExt",
                // url:"http://localhost:8000",
                dataType: 'json',
                contentType: "application/json",   
                data: JSON.stringify({
                    'orderid':orderid,
                    // 'type':num,
                    'filetypeext':"1"
                }),
                success: function (data) {
                    switch(data['code']){
                        case "0":
                            alert("成功");
                            addList(data['data']);
                            console.log(data['data']);
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
    function addList(data){
        // orderextfileid	扩展文件id
        // orderid	订单id
        // filetype	文件类型
        // fileuuid	文件uuid
        // status	文件状态
        // createtime	创建时间
        // filetypename	文件名称
        // filetypeext	扩展类型
        var str = '',result='',dian='',jidong='',zhan='';
        $.each(data['fileList'],function(index,val){
            if(val['filetype']== "2"){
                dian+=`<div class="bdl_listZhaoAdd bdl_listZhaoAdd1" listUrl="http">
                    <div class="uploadListBig">
                        <div class="upload on">
                            <input capture="camera" accept="image/*"  class="uploadBtn" type="file">
                            <div class="boxP">
                            <div class="upBg">
                            </div>
                                <p><span class="bdl_textTitleOdd">垫付证明上传</span></p>
                            </div>
                            <b class="close clo"></b>
                            <img src=${val['url']} class=${"img"+$(".photo").eq(0).attr("num")}
                            uuid=${val['fileuuid']}>
                        </div>
                    </div> 
                </div>`;
            }
            if(val['filetype']== "1"){
                jidong+=`<div class="bdl_listZhaoAdd bdl_listZhaoAdd2" listUrl="http">
                        <div class="uploadListBig">
                            <div class="upload on">
                                <input capture="camera" accept="image/*"  class="uploadBtn" type="file">
                                <div class="boxP">
                                <div class="upBg">
                                    
                                </div>
                                 <p><span class="bdl_textTitleOdd">销售发票上传</span></p>
                                </div>
                                <b class="close clo"></b>
                                <img src=${val['url']} class=${"img"+$(".photo").eq(1).attr("num")}
                                uuid=${val['fileuuid']}>
                            </div>
                        </div> 
                </div>`;
            }
            if(val['filetype']=="3"){
                zhan+=`<div class="bdl_listZhaoAdd bdl_listZhaoAdd3" listUrl="http">
                        <div class="uploadListBig">
                            <div class="upload on">
                                <input capture="camera" accept="image/*"  class="uploadBtn" type="file">
                                <div class="boxP">
                                <div class="upBg">
                                    
                                </div>
                                 <p><span class="bdl_textTitleOdd">暂住证/居住证上传</span></p>
                                </div>
                                <b class="close clo"></b>
                                <img src=${val['url']} class=${"img"+$(".photo").eq(2).attr("num")}
                                uuid=${val['fileuuid']}>
                            </div>
                        </div> 
                    </div>`;
            }
            if(val['filetype']=="9"){
                str+=`<div class="bdl_listZhaoAdd bdl_listZhaoAdd4" listUrl="http">
                        <div class="uploadListBig">
                            <div class="upload on">
                                <input capture="camera" accept="image/*"  class="uploadBtn" type="file">
                                <div class="boxP">
                                <div class="upBg">
                                    
                                </div>
                                 <p><span class="bdl_textTitleOdd">暂住证/居住证上传</span></p>
                                </div>
                                <b class="close clo"></b>
                                <img src=${val['url']} class=${"img"+$(".photo").eq(3).attr("num")}
                                uuid=${val['fileuuid']}>
                            </div>
                        </div> 
                    </div>`;
            }
            if(val['filetype']=="4"){
                result+=`<div class="bdl_listZhaoAdd bdl_listZhaoAdd5" listUrl="http">
                        <div class="uploadListBig">
                            <div class="upload on">
                                <input capture="camera" accept="image/*"  class="uploadBtn" type="file">
                                <div class="boxP">
                                <div class="upBg">
                                    
                                </div>
                                 <p><span class="bdl_textTitleOdd">暂住证/居住证上传</span></p>
                                </div>
                                <b class="close clo"></b>
                                <img src=${val['url']} class=${"img"+$(".photo").eq(4).attr("num")}
                                uuid=${val['fileuuid']}>
                            </div>
                        </div> 
                    </div>`;
            }
        })
        dian==""?'':$(".photo1").html(dian);
        jidong==""?'':$(".photo2").html(jidong);
        zhan==""?'':$(".photo3").html(zhan);
        str==''?'':$(".photo4").html(str);
        result==''?'':$(".photo5").html(result);
        remove();
    }
    //点击上传
    $(".pushFile").off("click").on("click",function(){
        var txtBoo = $(this).parents(".txtBoo");
        var $length = txtBoo.find(".img"+txtBoo.find(".photo").attr("num")+"").length;
        if($length == 0){
            alert("您没有选取上传文件");
            return false;
            // file	文件	String base64数据	必选	
            // orderid	订单id	int		
            // employeeid	员工	int		
            // idcardnum	联系人身份证	String	必选	
            // buyeridcardnum	购买人身份证	int		
            // contractid	合同id	int		
            // organizecode	机构代码	String		
            // uuidList	uuid集合	String数组		
            // customerid	"客户id 
        }else{
            var sendDate = {};
            var length = $(".img"+txtBoo.find(".photo").attr("num")+"").length;
            var sendD = [],uuidList = [];
            for(var i =0 ;i<length;i++){
                if($($(".img"+txtBoo.find(".photo").attr("num")+"")[i]).attr("uuid")){
                    uuidList.push($($(".img"+txtBoo.find(".photo").attr("num")+"")[i]).attr("uuid"));
                }else{
                    sendD.push($($(".img"+txtBoo.find(".photo").attr("num")+"")[i]).attr("src").split(",")[1]);
                }
            }
            sendD!=''?sendDate['file'] = sendD:'';
            sendDate['orderid'] = orderid;
            // sendDate['orderid'] = orderid;  //放开
            sendDate['type']=$(this).attr("typeList");
            sendDate['filetypeext'] = "1";
            uuidList!=''?sendDate['uuidList']=uuidList:'';
            console.log(sendDate)
            $.ajax({ 
                type: "POST",
                // url: srcConfig.upload,  //这个放开
                url:"http://192.168.206.220:8081/cbp/upload",
                dataType: 'json',
                contentType: "application/json",   
                data: JSON.stringify(sendDate),
                success: function (data) {
                    switch(data['code']){
                        case "0":
                            alert("上传成功");
                        break;
                        case "101":
                            alert("上传失败");
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
}(jQuery))