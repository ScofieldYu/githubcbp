$(function () {

    var url = window.location.href; //获取当前页url



    var queryUrl = url.queryURLParameter(); //转query

    var transaction_id = queryUrl.transaction_id;
    var result_code = queryUrl.result_code;
  
    if (result_code==3000) {
        var result ="签署成功"
    } else if (result_code==3001) {
        var result ="签署失败，请重新操作"
    }
    debugger;
    var viewpdf_url = decodeURIComponent(queryUrl.viewpdf_url);

    var timestamp = queryUrl.timestamp;

    var pattern = /(\d{4})(\d{2})(\d{2})(\d{2})(\d{2})(\d{2})/;
    var formatedDate = timestamp.replace(pattern, '$1/$2/$3 $4:$5:$6');
    var ddate = new Date(formatedDate);
    var date = new Date(ddate);
    date_value = date.getFullYear() + '年' + (date.getMonth() + 1) + '月' + date.getDate() + '日' + date.getHours() + ':' + date.getMinutes() + ':' + date.getSeconds();
    //yyyy-MM-dd hh:mm:ss  
    $("#number").html(transaction_id); 
    $("#result").html(result);
   
    $("#lookurl a").attr('href', viewpdf_url);

    $("#queryTime").html(date_value);
})



