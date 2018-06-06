$(function(){
    var session = window.sessionStorage;
    var url = window.location.href; //获取当前页url
    
    var tokenQuery = url.queryURLParameter();
    // var se = {};
    //  se={code: '0',
    //  message: '登录成功',
    //  data: { employeeid: '10060179',
    //  phonenumber: '13120370722',
    //  name: '余志诚',
    //  wxname: '余志诚',
    //  token: '08a0e52aaab6c049f5e7c5479278c0b8' ,
    //  accounttype:'2'}};
    //  session.setItem('data',JSON.stringify(se))
    $ajax(tokenQuery['customerid']);
    function $ajax(customerid){
        object = {};
        object['employeeid'] = Number(JSON.parse(session.getItem("data"))['data']['employeeid'] || '');
        object['customerid'] = Number(customerid);
        $.ajax({ 
            type: "POST",
            url: srcConfig.getOrderListEmployee,
            // url:"http:localhost:8888",
            dataType: 'json',
            contentType: "application/json",   //这个放开
            data: JSON.stringify(object),
            success: function (data) {
                switch(data['code']){
                    case "0":
                        alert("查询成功");
                        blings(data['data'])
                    break;
                    case "101":
                        alert("查询失败");
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
   function blings(data){
     var str = '';
     $.each(data,function(index,val){
        str+=`<div class="list_main">
            <div class="number">
                <span class="img"></span>
                <span class="numBB">订单编号: <span class="numBBNumber">${val['orderid'] || ''}</span></span>
            </div>
            <div class="numList">
                <p class="car"> <span class="label">车型:</span> <span>${val['cartype'] || ''}</span></p>
                <div class="out">
                    <p> <span class="label">外观:</span> <span>${val['contractflag'] || ''}</span></p>
                    <p > <span class="label odd">内饰:</span> <span>${val['carid'] || ''}</span></p>
                </div>
                <p class="car"> <span class="label">车架号:</span> <span>${val['realvinno'] || ''}</span></p>
            </div>
         </div>`
     })
     $(".box_main").html(str);
     $(".list_main").on("click",function(){
        var numBBNumber =$(this).find(".numBBNumber").html() || '';
        session.setItem("numBBNumber",numBBNumber);
        window.location.href = "./bdlSell_add.html?customerid="+tokenQuery['customerid']+"";
     })
   }
}(jQuery))