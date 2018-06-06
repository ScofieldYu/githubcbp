$(function(){
    var url = window.location.href; //获取当前页urls
    var tokenQuery = url.queryURLParameter();
    var session = window.sessionStorage;
    var sessionOrderid = session.getItem("orderid") || '';
    var orderid = '';
    tokenQuery['orderid']? orderid=tokenQuery['orderid'].split("#")[0]:orderid = sessionOrderid;

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
                                <p><span class="bdl_textTitleOdd">合格证上传</span></p>
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
                                 <p><span class="bdl_textTitleOdd">手册上传</span></p>
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
                                 <p><span class="bdl_textTitleOdd">三包凭证上传</span></p>
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
                    'filetypeext':"9"
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
                    'filetypeext':"9"
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
        var str = '',dian='',jidong='',zhan='';
        $.each(data['fileList'],function(index,val){
            if(val['filetype'] == '5'){
                str+=`
                <div class="listFile">
                    <span>${val['filetypename']||''}</span>
                    <span class="imgEye" url=${val['url']} ></span>
                </div>`;
            }
            if(val['filetype']== "10050"){
                dian+=`<div class="bdl_listZhaoAdd bdl_listZhaoAdd1" listUrl="http">
                    <div class="uploadListBig">
                        <div class="upload on">
                            <input capture="camera" accept="image/*"  class="uploadBtn" type="file">
                            <div class="boxP">
                            <div class="upBg">
                            </div>
                                <p><span class="bdl_textTitleOdd">合格证上传</span></p>
                            </div>
                            <b class="close clo"></b>
                            <img src=${val['url']} class=${"img"+$(".photo1").attr("num")}
                            uuid=${val['fileuuid']}>
                        </div>
                    </div> 
                </div>`;
            }
            if(val['filetype']== "10052"){
                jidong+=`<div class="bdl_listZhaoAdd bdl_listZhaoAdd2" listUrl="http">
                        <div class="uploadListBig">
                            <div class="upload on">
                                <input capture="camera" accept="image/*"  class="uploadBtn" type="file">
                                <div class="boxP">
                                <div class="upBg">
                                    
                                </div>
                                 <p><span class="bdl_textTitleOdd">手册上传</span></p>
                                </div>
                                <b class="close clo"></b>
                                <img src=${val['url']} class=${"img"+$(".photo2").attr("num")}
                                uuid=${val['fileuuid']}>
                            </div>
                        </div> 
                </div>`;
            }
            if(val['filetype']=="10053"){
                zhan+=`<div class="bdl_listZhaoAdd bdl_listZhaoAdd3" listUrl="http">
                        <div class="uploadListBig">
                            <div class="upload on">
                                <input capture="camera" accept="image/*"  class="uploadBtn" type="file">
                                <div class="boxP">
                                <div class="upBg">
                                    
                                </div>
                                 <p><span class="bdl_textTitleOdd">三包凭证上传</span></p>
                                </div>
                                <b class="close clo"></b>
                                <img src=${val['url']} class=${"img"+$(".photo3").attr("num")}
                                uuid=${val['fileuuid']}>
                            </div>
                        </div> 
                    </div>`;
            }
        })
        $(".textBox1").html(str);
        dian==""?'':$(".photo1").html(dian);
        jidong==""?'':$(".photo2").html(jidong);
        zhan==""?'':$(".photo3").html(zhan);
        remove();
        //查看
        $(".imgEye").on("click",function(){
            var url = $(this).attr("url");
             $(".imgFile").attr("src",url);
             $(".addPhoto").show();
        })
        $(".addPhoto").off("click").on("click",function(){
            $(".addPhoto").fadeOut();
        })
    }
    //点击上传
    $(".pushFile").off("click").on("click",function(){
        var txtBoo = $(this).parents(".txtBoo");
        var $length = txtBoo.find(".img"+txtBoo.find(".photo").attr("num")+"").length;
        if($length == 0){
            alert("您没有选取上传文件");
            return false;
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
            sendDate['type']=$(this).attr("typeList");
            sendDate['filetypeext'] = "9";
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