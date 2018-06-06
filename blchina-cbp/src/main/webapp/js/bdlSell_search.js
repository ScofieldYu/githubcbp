$(function(){
    var session = window.sessionStorage;
    var stroge = window.localStorage;

    // var url = window.location.href; //获取当前页url
    // var tokenQuery = url.queryURLParameter();
    // var employeeId = tokenQuery['employeeId'] || '';

    var employeeId = JSON.parse(session.getItem("data"))['data']['employeeid'] || '';

    start();
    //搜索框 根据本地存储渲染
    function start(){
        var str = '';
        for(var i in stroge){
            if(i.split("*")[0] == "history"){
                str+= `<span>${stroge[i]}</span>`
            }
        }
        $(".hisTList").html(str);
        $(".garbageBin").on("click",function(){
            $(".hisTList").html('');
            for(var i in stroge){                        
                if(i.split("*")[0] == "history"){
                    stroge.removeItem(i);
                }
            } 
        })

        //把历史记录 响应input
        $(".hisTList span").off("click").on("click",function(){
            var txt = $(this).text();
            $(".inputSearch").val(txt);
        })
        //点击取消 回退页面
        $(".leave").on("click",function(){
            //返回上一页
            history.go(-1);   
            // if(session.getItem("task")){
            //      window.location.href = session.getItem("task");
            //      session.removeItem("task");
            // }else{
            //      $(".bdl_push").hide().attr("display","false");
            //      $(".bdl_mainCard").css("display","block");
            //      $(".bdlSerch_main").css("display","none");
            // }
        })
        $(".inputSearch").on('keypress',function(e) {  
        var keycode = e.keyCode;  
        var searchName = $(this).val();  
        if(keycode == '13') {  
            e.preventDefault(); 
            //请求搜索接口  
            stroge.setItem("history*"+searchName+"",searchName);
            session.setItem("name",searchName);
            
            window.location.href = "./bdlSell_card.html";
            //添加 state 方便区分 全部模块
            // stroge.setItem("state","searchState"); 
         }
      });       
    }
}(jQuery))