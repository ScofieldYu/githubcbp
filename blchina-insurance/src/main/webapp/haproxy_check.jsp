<%@ page language="java" contentType="text/html; charset=utf-8"  import="java.net.InetAddress"  
    pageEncoding="utf-8"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">  
<html>  
<head>  
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">  
<title>Server status ok</title>  
</head>  
<body>  
 <p>Server is ok.</p>	
<%  
	InetAddress addr = InetAddress.getLocalHost();  
	out.println("Host Name："+addr.getHostName()); 	 
%>  
<%	  
	String port = System.getProperty("reyo.localPort");
	String path = request.getContextPath();
%>	  
 <p>Tomcat Port:<%=port%></p>
 <p>Project name:<%=path%></p>  
</body>  
</html>