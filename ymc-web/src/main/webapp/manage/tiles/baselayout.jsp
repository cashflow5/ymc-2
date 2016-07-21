<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title><tiles:insertAttribute name="title" /></title>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/base.css?${style_v}"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/mold.css?${style_v}"/>
</head>
<body>
<script type="text/javascript" src="${BasePath}/yougou/js/bootstrap.js"></script>
		<div id="newhead" class="header">
			<tiles:insertAttribute name="head" />
		</div>
		<div id="newcontent" class="wrap clearfix mt10">
			<div id="newleft" class="main_menu fl jsMainMenu">
				<tiles:insertAttribute name="left" />
			</div>
			<div id="newmain" class="main_bd fr">
				<tiles:insertAttribute name="main" />
			</div>
		</div>
		<div id="newfooter" class="wrap footer">
			<tiles:insertAttribute name="footer" />
		</div>
</body>
</html>