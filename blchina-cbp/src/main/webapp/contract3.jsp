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
	<h1><button style="width:500px;height:300px;display:block" onclick="cs()">授权按钮</button></h1>
	<script type="text/javascript">
	
	function cs(){
            location.href = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx05ac254179f84ae1&redirect_uri=http://cbp-dev.blchina.com/cbp/openId/setOpenId&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect";
	}

</script>
</body>
</html>