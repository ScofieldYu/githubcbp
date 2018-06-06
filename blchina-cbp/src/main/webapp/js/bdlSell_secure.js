$(function(){
    var url = window.location.href; //获取当前页urls
    var tokenQuery = url.queryURLParameter();
    var session = window.sessionStorage;
    //判断地址栏带来的 字段 
    var sessionOrderid = session.getItem("orderid") || '';
    var orderid = '';
    //隐藏图片
    console.log(sessionOrderid)
    tokenQuery['orderid']? orderid=tokenQuery['orderid']:orderid = sessionOrderid;

    if(tokenQuery["leixing"] =="1"){
        $(".txtBoo1").show();
        $(".txtBoo2").hide();
    }else{
        $(".txtBoo2").show();
        $(".txtBoo1").hide();
    }
    publing();
    imgChangeEvent();
    //图片上传
     function publing(){
        $.ajax({ 
                type: "POST",
                // url: srcConfig.getFileExt,  //这个放开
                url:"http://192.168.206.220:8081/cbp/getFileExt",
                // url:"http://localhost:8000",
                dataType: 'json',
                contentType: "application/json",   
                data: JSON.stringify({
                    'orderid':orderid,
                    'filetypeext':"6",
                    'typeList':["1",'2','3']
                }),
                success: function (data) {
                    switch(data['code']){
                        case "0":
                            alert("成功");
                            addList(data['data']);
                            console.log(data);
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
        var str = '',result='',odd='',innish='',innish1='',innish2='';
        $.each(data['fileList'],function(index,val){
            if(val['filetype'] == '5'){
                str+=`
                <div class="listFile">
                    <span>${val['filetypename']||''}</span>
                    <span class="imgEye" url=${val['url']}></span>
                </div>`;
            }
            //指标人身份证
            if(val['filetype'] == '2'){
                result+=`<div class="bdl_listZhaoAdd">
                        <div class="uploadListBig">
                            <div class="upload">
                                <!--<input capture="camera" accept="image/*"  class="uploadBtn" type="file">-->
                                <div class="boxP">
                                <div class="upBg">
                                    
                                </div>
                                 <p><span class="bdl_textTitleOdd">身份证</span></p>
                                </div>
                                <img src=${val['url']} class='img'>
                            </div>
                        </div>`;
            }
            //被保险人身份证身份证
            if(val['filetype'] == '1'){
                  odd+=` <div class="bdl_listZhaoAdd">
                        <div class="uploadListBig">
                            <div class="upload">
                                <!--<input capture="camera" accept="image/*"  class="uploadBtn" type="file">-->
                                <div class="boxP">
                                <div class="upBg">
                                    
                                </div>
                                 <p><span class="bdl_textTitleOdd">身份证</span></p>
                                </div>
                                <img src=${val['url']} class='img'>
                            </div>
                        </div> 
                    </div>`
            }
            //合格执照
            if(val['filetype'] == '3'){
                innish+=`<div class="bdl_listZhaoAdd">
                        <div class="uploadListBig">
                            <div class="upload">
                                <div class="boxP">
                                <div class="upBg">
                                    
                                </div>
                                 <p><span class="bdl_textTitleOdd">营业执照</span></p>
                                </div>
                                <img src=${val['url']} class='img'>
                            </div>
                        </div> 
                    </div>`
            }
            //机动车合格证
             if(val['filetype'] == '10086'){
                innish1+=`<div class="bdl_listZhaoAdd bdl_listZhaoAdd1">
                        <div class="uploadListBig">
                            <div class="upload on">
                                <input capture="camera" accept="image/*"  class="uploadBtn" type="file">
                                <div class="boxP">
                                <div class="upBg">
                                    
                                </div>
                                 <p><span class="bdl_textTitleOdd">合格证上传</span></p>
                                </div>
                                <b class="close clo"></b>
                                <img src=${val['url']} class=${"img"+$(".photo4").attr("num")}
                                uuid=${val['fileuuid']}>
                            </div>
                        </div> 
                    </div>`;
            }
            //进口车关单原件
             if(val['filetype'] == '10051'){
                innish2+=`<div class="bdl_listZhaoAdd bdl_listZhaoAdd2">
                        <div class="uploadListBig">
                            <div class="upload on">
                                <input capture="camera" accept="image/*"  class="uploadBtn" type="file">
                                <div class="boxP">
                                <div class="upBg">
                                    
                                </div>
                                 <p><span class="bdl_textTitleOdd">关单上传</span></p>
                                </div>
                                <b class="close clo"></b>
                                <img src=${val['url']} class=${"img"+$(".photo5").attr("num")}
                                uuid=${val['fileuuid']}>
                            </div>
                        </div> 
                    </div>`;
            }
        })
        $(".textBox").html(str);
        result?$(".photo1").html(result):'';
        odd?$(".photo2").html(odd):'';
        innish?$(".photo3").html(innish):'';
        innish1?$(".photo4").html(innish1):'';
        innish2?$(".photo5").html(innish2):'';
        remove();
        //查看
        $(".imgEye").on("click",function(){
            var url = $(this).attr("url");
            $(".imgFile").attr("src",url);
            $(".addPhoto").show();
            $(".addPhoto").off("click").on("click",function(){
                $(".addPhoto").fadeOut();
            })
            // $.ajax({ 
            //     type: "POST",
            //     url: srcConfig.getDocumentQueryBase,  //这个放开
            //     // url:"http://192.168.206.220:8081/cbp/documentQuery/getDocumentQueryBase",
            //     dataType: 'json',
            //     contentType: "application/json",   
            //     data: JSON.stringify(object),
            //     success: function (data) {
            //         switch(data['code']){
            //             case "0":
            //                 alert("查询成功");
            //                 console.log(data['data'])
                         
            //             break;
            //             case "101":
            //                 alert("上传失败");
            //             break;
            //             case "102":
            //                 alert("参数不正确");
            //             break;
            //         }
            //     },
            //     error: function (data) {
            //         alert('请求失败');
            //     }
            // });
        })
    }
    function remove(){
        //点击删除
        $(".clo").off("click").on("click",function(event){
            event.stopPropagation();
            $(this).parents(".bdl_listZhaoAdd").remove();
        })
    }

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
            $(".photo4").append(str);
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
                                <p><span class="bdl_textTitleOdd">关单上传</span></p>
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
            sendDate['filetypeext'] = "6";
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