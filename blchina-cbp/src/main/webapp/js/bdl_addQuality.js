$(function(){
    var url = window.location.href; //获取当前页urls
    var tokenQuery = url.queryURLParameter();
    var session = window.sessionStorage;
    var sessionOrderid = session.getItem("orderid") || '';
    var orderid = '';
    //隐藏图片
    console.log(sessionOrderid)
    tokenQuery['orderid']? orderid=tokenQuery['orderid']:orderid = sessionOrderid;

    //加装精品  获取
    public();
    function public(){
        $.ajax({ 
                type: "POST",
                // url: srcConfig.getFileExt,  //这个放开
                // url:"http://192.168.206.220:8081/cbp/getFileExt",
                url:"http://localhost:8000",
                // url:"http://localhost:8000",
                dataType: 'json',
                // contentType: "application/json",   
                data: JSON.stringify({
                    'orderid':orderid,
                    // 'type':num,
                    'filetypeext':"7"
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
        var str = '';
        $.each(data,function(index,val){
            if(val['filetype'] == '10010'){
                str+=`
                <div class="listFile">
                    <span>${val['filetypename']||''}</span>
                    <span class="imgEye" url=${val['url']} ></span>
                </div>`;
            }
        })
        $(".textBox").html(str);
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
}(jQuery))