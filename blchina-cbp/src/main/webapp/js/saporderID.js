$(function () {

   
    $("#orderidBtn").click(function () {

 
  
        var senddata={"Records":{

            saporderid: $("#orderid").val()
        }}
   
        $.ajax({
     
            type: "POST",
          url: 'http://bdl-cbp.blchina.com/cbp/wx/pushCarStart',
            dataType: 'json',

            contentType: "application/json",


            data: JSON.stringify(senddata),

            success: function (data) {
                
                console.log(data);
                alert("连接成功!")
            },

            error: function (data) {
                alert("连接失败!");
                console.log(data);
        }

        })
    })

})