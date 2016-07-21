<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.io.File"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>index</title>
</head>
<body>
<%
    String path=application.getRealPath("/");
    File file=new File(path+File.separator+"WEB-INF"+File.separator+"classes"+File.separator+"log4j.properties");
    String updateTime=(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date((file).lastModified()));
    out.println("war打包时间:"+updateTime);
%>
</body>
</html>