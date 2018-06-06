$(function(){
    var url = window.location.href; //获取当前页urls
    var tokenQuery = url.queryURLParameter();
    var session = window.sessionStorage;
    var sessionOrderid = session.getItem("orderid") || '';
    var orderid = '';
    tokenQuery['orderid']? orderid=tokenQuery['orderid'].split("#")[0]:orderid = sessionOrderid;
    publing()
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
    $(".addFile2").on("click",function(){
         if($(".bdl_listZhaoAdd2").length!=$(".img2").length){
             return false;
         }else{
             addPush('bdl_listZhaoAdd2','车辆合格证上传',".photo2");
         }  
    })
    $(".addFile3").on("click",function(){
         if($(".bdl_listZhaoAdd3").length!=$(".img3").length){
             return false;
         }else{
            addPush('bdl_listZhaoAdd3','工作证上传',".photo3");
         }  
    })
    $(".addFile4").on("click",function(){
         if($(".bdl_listZhaoAdd4").length!=$(".img4").length){
             return false;
         }else{
             addPush('bdl_listZhaoAdd4','军官证上传',".photo4");
         }  
    })
    $(".addFile5").on("click",function(){
         if($(".bdl_listZhaoAdd5").length!=$(".img5").length){
             return false;
         }else{
             addPush('bdl_listZhaoAdd3','警官证上传',".photo5");
         }  
    })
    $(".addFile6").on("click",function(){
         if($(".bdl_listZhaoAdd6").length!=$(".img6").length){
             return false;
         }else{
             addPush('bdl_listZhaoAdd6','公务员证上传',".photo6");
         }  
    })
    $(".addFile7").on("click",function(){
         if($(".bdl_listZhaoAdd7").length!=$(".img7").length){
             return false;
         }else{
             addPush('bdl_listZhaoAdd7','房产证上传',".photo7");
         }  
    })
    $(".addFile8").on("click",function(){
         if($(".bdl_listZhaoAdd8").length!=$(".img8").length){
             return false;
         }else{
             addPush('bdl_listZhaoAdd8','行使证上传',".photo8");
         }  
    })
    $(".addFile9").on("click",function(){
         if($(".bdl_listZhaoAdd9").length!=$(".img9").length){
             return false;
         }else{
             addPush('bdl_listZhaoAdd9','车辆登记证上传',".photo9");
         }  
    })
    $(".addFile10").on("click",function(){
         if($(".bdl_listZhaoAdd10").length!=$(".img10").length){
             return false;
         }else{
             addPush('bdl_listZhaoAdd10','咨询纪要上传',".photo10");
         }  
    })
    $(".addFile11").on("click",function(){
         if($(".bdl_listZhaoAdd11").length!=$(".img11").length){
             return false;
         }else{
             addPush('bdl_listZhaoAdd11','批贷函上传',".photo11");
         }  
    })
    $(".addFile12").on("click",function(){
         if($(".bdl_listZhaoAdd12").length!=$(".img12").length){
             return false;
         }else{
             addPush('bdl_listZhaoAdd12','暂住证/居住证上传',".photo12");
         }  
    })
    function remove(){
        //点击删除
        $(".clo").off("click").on("click",function(event){
            event.stopPropagation();
            $(this).parents(".bdl_listZhaoAdd").remove();
        })
    }
    function publing(){
        console.log({
                    'orderid':orderid,
                    'filetypeext':"4"
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
                    'filetypeext':"4",
                    'typeList':["2",'3']
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
        var str = '',result='',dian='',jidong='',zhan='',zhan1="",zhan2='',zhan3='',zhan4='',zhan5='',result1='';
        var banLi1 = '',banLi2 = '',banLi3 = '',banLi4 = '',banLi5 = '';
        var yan1='',yan2='',yan3='',yan4='';
        $.each(data['fileList'],function(index,val){
           
            if(val['filetype'] == "10101"){
                result = addAgain('bdl_listZhaoAdd2','车辆合格证上传','.photo2',result,val);
            }
            if(val['filetype']== "10051"){
                dian = addAgain('bdl_listZhaoAdd3','工作证上传','.photo3',dian,val);
            }
            if(val['filetype']== "10088"){
                jidong = addAgain('bdl_listZhaoAdd4','军官证上传','.photo4',jidong,val);
            }
            if(val['filetype']=="10089"){
                zhan = addAgain('bdl_listZhaoAdd5','警官证上传','.photo5',zhan,val);
            }
            if(val['filetype']=="10090"){
                zhan1 = addAgain('bdl_listZhaoAdd6','公务员证上传','.photo6',zhan1,val);
            }
            if(val['filetype']=="10049"){
                zhan2 = addAgain('bdl_listZhaoAdd8','行使证上传','.photo8',zhan2,val);
            }
            if(val['filetype']=="10091"){
                zhan3 = addAgain('bdl_listZhaoAdd9','车辆登记证上传','.photo9',zhan3,val);
            }
            if(val['filetype']=="10092"){
                zhan4 = addAgain('bdl_listZhaoAdd10','咨询纪要上传','.photo10',zhan4,val);
            }
            if(val['filetype']=="10093"){
                zhan5 = addAgain('bdl_listZhaoAdd11','批贷函上传','.photo11',zhan5,val);
            }
            //指标人身份证
            if(val['filetype'] == '2'){
            result1+=`<div class="bdl_listZhaoAdd" style="margin-bottom:.25rem">
                    <div class="uploadListBig">
                        <div class="upload">
                            <div class="boxP">
                            <div class="upBg">
                                
                            </div>
                                <p><span class="bdl_textTitleOdd">身份证</span></p>
                            </div>
                            <img src=${val['url']} class='img'> 
                        </div>
                    </div></div>`;
            }
             if(val['filetype'] == '3'){
               yan3+=`<div class="bdl_listZhaoAdd">
                    <div class="uploadListBig">
                        <div class="upload">
                            <div class="boxP">
                            <div class="upBg">
                                
                            </div>
                                <p><span class="bdl_textTitleOdd">营业执照</span></p>
                            </div>
                            <img src=${val['url']} class='img'>
                        </div>
                    </div></div>`;
            }
            if(val['filetype'] == '10091'){
                banLi1 = addAgain('bdl_listZhaoAdd12','暂住证/居住证上传','.photo12',banLi1,val);
            }
        })
        result1==''?"":$(".photo1").html(result1);
        yan3 = ''?"":$(".photo13").html(yan3);

        result==''?"":$(".photo2").html(result);
        dian==""?'':$(".photo3").html(dian);
        jidong==""?'':$(".photo4").html(jidong);
        zhan==""?'':$(".photo5").html(zhan);
        zhan1==""?'':$(".photo6").html(zhan1);
        zhan2==""?'':$(".photo8").html(zhan2);
        zhan3==""?'':$(".photo9").html(zhan3);
        zhan4==""?'':$(".photo10").html(zhan4);
        zhan5==""?'':$(".photo11").html(zhan5);
        banLi1==''?"":$(".photo12").html(banLi1);
        yan4 = ''?"":$(".photo7").html(yan4);
        remove();
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
            // sendDate['orderid'] = orderid;  //放开
            sendDate['type']=$(this).attr("typeList");
            sendDate['filetypeext'] = "4";
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
    //获取绑定 封装
    function addAgain(bdl_listZhaoAdd,update,clAss,dom,val){
        
         dom+=`<div class="bdl_listZhaoAdd ${bdl_listZhaoAdd}" listUrl="http">
                    <div class="uploadListBig">
                        <div class="upload on">
                            <input capture="camera" accept="image/*"  class="uploadBtn" type="file">
                            <div class="boxP">
                            <div class="upBg">
                                
                            </div>
                                <p><span class="bdl_textTitleOdd">${update}</span></p>
                            </div>
                            <b class="close clo"></b>
                            <img src=${val['url']} class=${"img"+$(clAss).attr("num")}
                            uuid=${val['fileuuid']}>
                        </div>
                    </div> 
                </div>`;
        return dom;
    }
    //点击增加 封装
    function addPush(dom,text,clAss){
        var str = '';
        str+=`<div class="bdl_listZhaoAdd ${dom}">
                    <div class="uploadListBig">
                        <div class="upload">
                            <input capture="camera" accept="image/*"  class="uploadBtn" type="file">
                            <div class="boxP">
                            <div class="upBg">
                                
                            </div>
                                <p><span class="bdl_textTitleOdd">${text}</span></p>
                            </div>
                            <b class="close clo"></b>
                        </div>
                    </div> 
                </div>`;
        $(clAss).append(str);
        imgChangeEvent();
        remove();
    }
}(jQuery))