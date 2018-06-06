<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src="js/jquery.min.js"></script>

</head>
<body>
	订单号<input name="text" id="text1" style="width:300px;height:200px;display:block">
	<h1><button style="width:500px;height:300px;display:block" onclick="cs()">测试支付1分</button></h1>
	<script type="text/javascript">
	/*{result_code=SUCCESS, sign=C55EDA7FBB08E34174F134312F4692D5,
		mch_id=1494344582, prepay_id=wx201712181724235f95939c040472677351, 
		return_msg=OK, appid=wx05ac254179f84ae1, nonce_str=2VFNBW5KCjwRoiw2,
		return_code=SUCCESS, trade_type=JSAPI} 
	 */
	function cs(){
		var orderId1 = $("#text1").val();
		$.ajax({
            type: "POST",
            url: "http://cbp-qas.xbotech.com/cbp/sap/wXPay/getPayOrder",
            dataType: 'json',
            data:JSON.stringify({
            	orderId:orderId1+""
            }),
            contentType: "application/json",
            success: function (data) {
                if (data.code == 0) {
                    function onBridgeReady(){
                    	   WeixinJSBridge.invoke(
                    	       'getBrandWCPayRequest', {
                    	           "appId":data.data.appid,     //公众号名称，由商户传入     
                    	           "timeStamp":data.data.timeMillis,         //时间戳，自1970年以来的秒数     
                    	           "nonceStr":data.data.nonce_str, //随机串     
                    	           "package":"prepay_id="+data.data.prepay_id,     
                    	           "signType":"MD5",         //微信签名方式：     
                    	           "paySign":data.data.paySign //微信签名 
                    	       },
                    	       function(res){     
                    	           if(res.err_msg == "get_brand_wcpay_request:ok" ) {}     // 使用以上方式判断前端返回,微信团队郑重提示：res.err_msg将在用户支付成功后返回    ok，但并不保证它绝对可靠。 
                    	       }
                    	   ); 
                    	}
                    	if (typeof WeixinJSBridge == "undefined"){
                    	   if( document.addEventListener ){
                    	       document.addEventListener('WeixinJSBridgeReady', onBridgeReady, false);
                    	   }else if (document.attachEvent){
                    	       document.attachEvent('WeixinJSBridgeReady', onBridgeReady); 
                    	       document.attachEvent('onWeixinJSBridgeReady', onBridgeReady);
                    	   }
                    	}else{
                    	   onBridgeReady();
                    	}
                }
            },
            error: function (data) {
            	alert(data.code)
            }
        });
		
	}

</script>
</body>
</html>